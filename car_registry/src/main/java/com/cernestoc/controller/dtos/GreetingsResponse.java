package com.cernestoc.controller.dtos;

import lombok.Data;

@Data
public class GreetingsResponse {

    private Integer id;

    private String greeting;

    private String personName;
}
