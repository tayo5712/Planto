package com.ssafy.plant.service;

import com.ssafy.plant.components.XmlPlantDetailResponse;
import com.ssafy.plant.components.XmlPlantResponse;
import com.ssafy.plant.domain.DictEntity;
import com.ssafy.plant.repository.DictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

@Service
public class DictApi {
    @Value("${java.plant.secretKey}")
    private String secretKey;

    @Autowired
    private DictRepository dictRepository;
    public String callApi(String page, String cntntsNo, String api, String type) {
        try {
            StringBuilder urlBuilder = new StringBuilder("http://api.nongsaro.go.kr/service/garden/" + api); /*URL*/
            urlBuilder.append("?" + URLEncoder.encode("apiKey", "UTF-8") + secretKey); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + page); /*Service Key*/
            urlBuilder.append("&" + URLEncoder.encode("cntntsNo", "UTF-8") + "=" + cntntsNo); /*Service Key*/

            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");

            BufferedReader rd;
            if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            rd.close();
            conn.disconnect();

            // String 형식의 xml
            String xml = sb.toString();

            if (type.equals("page")) {
                return getPage(xml);
            } else if (type.equals("list")) {
                plantList(xml);
            } else if (type.equals("detail")){
                return xml;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "1";
    }

    public String getPage(String xml) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(XmlPlantResponse.class); // JAXB Context 생성
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();  // Unmarshaller Object 생성
            XmlPlantResponse apiResponse = (XmlPlantResponse) unmarshaller.unmarshal(new StringReader(xml)); // unmarshall 메서드 호출

            return apiResponse.getBody().getItems().getNumOfRows();

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return "1";
    }

    public void plantList(String xml) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(XmlPlantResponse.class); // JAXB Context 생성
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();  // Unmarshaller Object 생성
            XmlPlantResponse apiResponse = (XmlPlantResponse) unmarshaller.unmarshal(new StringReader(xml)); // unmarshall 메서드 호출

            for (XmlPlantResponse.Body.Items.Item item : apiResponse.getBody().getItems().getItem()) {
                savePlant(item.getCntntsNo(), item);
            }

        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void savePlant(String cntntsNo, XmlPlantResponse.Body.Items.Item plant) {
        try {
            String detailXml = callApi("1", cntntsNo, "gardenDtl", "detail");


//            plant detail
            JAXBContext jaxbContext = JAXBContext.newInstance(XmlPlantDetailResponse.class); // JAXB Context 생성
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();  // Unmarshaller Object 생성
            XmlPlantDetailResponse apiResponse = (XmlPlantDetailResponse) unmarshaller.unmarshal(new StringReader(detailXml)); // unmarshall 메서드 호출
            XmlPlantDetailResponse.Body.Item detail = apiResponse.getBody().getItem();

            String [] temperature = detail.getGrwhTpCodeNm().split("~");

            int temperatureMin = Integer.parseInt(temperature[0]);
            int temperatureMax = Integer.parseInt(temperature[1].substring(0, 2));

            String lights = detail.getLighttdemanddoCodeNm();
            int lightNum = 0;

            if (lights.contains("낮은 광도")) {
                lightNum += 1;
            }
            if(lights.contains("중간 광도")){
                lightNum += 2;
            }
            if(lights.contains("높은 광도")) {
                lightNum += 4;
            }

            int lightMin = 300;
            int lightMax = 800;

            if (lightNum == 2){
                lightMin = 800;
                lightMax = 1500;
            } else if (lightNum == 3){
                lightMin = 300;
                lightMax = 1500;
            } else if (lightNum == 4){
                lightMin = 1500;
                lightMax = 10000;
            } else if (lightNum == 5){
                lightMax = 10000;
            } else if (lightNum == 6){
                lightMin = 800;
                lightMax = 10000;
            } else if (lightNum == 3){
                lightMax = 10000;
            }

            int humidityMin = 0;
            int humidityMax = 100;

            String [] humidity = detail.getHdCodeNm().split(" ~ ");
            if (detail.getHdCodeNm().contains("이상")) {
                humidityMin = Integer.parseInt(humidity[0].substring(0, 2));
            } else if (detail.getHdCodeNm().contains("미만")){
                humidityMax = Integer.parseInt(humidity[0].substring(0, 2));
            } else if(!detail.getHdCodeNm().equals("")){
                humidityMin = Integer.parseInt(humidity[0]);
                humidityMax = Integer.parseInt(humidity[1].substring(0, 2));
            }
            String [] images = plant.getRtnStreFileNm().split("\\|");

            DictEntity dictEntity = DictEntity.builder()
                    .plantDictId(Long.parseLong(cntntsNo))
                    .name(plant.getCntntsSj())
                    .manageLevel(detail.getManagelevelCodeNm())
                    .functionInfo(detail.getFncltyInfo())
                    .temperatureMax(temperatureMax)
                    .temperatureMin(temperatureMin)
                    .lightMax(lightMax)
                    .lightMin(lightMin)
                    .humidityMax(humidityMax)
                    .humidityMin(humidityMin)
                    .manageInfo(detail.getSpeclmanageInfo())
                    .adviceInfo(detail.getAdviseInfo())
                    .imagePath(images[0])
                    .plantEng(detail.getPlntbneNm())
                    .build();
            dictRepository.save(dictEntity);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }
}
