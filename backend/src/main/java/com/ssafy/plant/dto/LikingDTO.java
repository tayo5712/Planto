package com.ssafy.plant.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@ToString
public class LikingDTO {
    private long serialNo;
    private int liking;

    @Builder
    public LikingDTO(long serialNo, int liking) {
        this.serialNo = serialNo;
        this.liking = liking;
    }
}
