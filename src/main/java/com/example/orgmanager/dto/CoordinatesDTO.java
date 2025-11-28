package com.example.orgmanager.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CoordinatesDTO {
    private Double x;
    private Integer y;
}