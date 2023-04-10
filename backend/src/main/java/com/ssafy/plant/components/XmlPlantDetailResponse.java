package com.ssafy.plant.components;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
public class XmlPlantDetailResponse {
    @XmlElement(name = "header")
    private XmlPlantDetailResponse.Header header;

    @XmlElement(name = "body")
    private XmlPlantDetailResponse.Body body;

    @Getter
    @Setter
    @XmlRootElement(name = "header")
    @ToString
    public static class Header {
        private String resultCode;
        private String resultMsg;
    }

    @Getter
    @Setter
    @XmlRootElement(name = "body")
    @ToString
    public static class Body {
        private XmlPlantDetailResponse.Body.Item item;

        @Getter
        @Setter
        @XmlRootElement(name = "item")
        @ToString
        public static class Item {
            private String grwhTpCodeNm;
            private String lighttdemanddoCodeNm;
            private String hdCodeNm;
            private String ignSeasonCodeNm;
            private String managelevelCodeNm;
            private String fncltyInfo;
            private String speclmanageInfo;
            private String adviseInfo;
            private String plntbneNm;

        }
    }
}
