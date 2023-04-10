package com.ssafy.plant.components;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.xml.bind.annotation.*;
import java.util.List;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "response")
@ToString
public class XmlPlantResponse {

    @XmlElement(name = "header")
    private Header header;

    @XmlElement(name = "body")
    private Body body;

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
        private Items items;
        @Getter
        @Setter
        @XmlRootElement(name = "items")
        @ToString
        public static class Items {

            private String numOfRows;
            private String pageNo;
            private String totalCount;
            private List<Item> item;

            @Getter
            @Setter
            @XmlRootElement(name = "item")
            @ToString
            public static class Item {

                private String cntntsNo;
                private String cntntsSj;
                private String rtnFileCours;
                private String rtnFileSeCode;
                private String rtnFileSn;
                private String rtnImageDc;
                private String rtnImgSeCode;
                private String rtnOrginlFileNm;
                private String rtnStreFileNm;
                private String rtnThumbFileNm;
            }
        }
    }
}