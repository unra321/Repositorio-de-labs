package com.cernestoc.controller.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GreetingsRequest {

    private String greeting;

    private String personName;

}
