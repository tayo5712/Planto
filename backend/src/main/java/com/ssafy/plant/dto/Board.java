package com.ssafy.plant.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Board implements Serializable {
    private Long id;
    private String title;
    private String contents;
}