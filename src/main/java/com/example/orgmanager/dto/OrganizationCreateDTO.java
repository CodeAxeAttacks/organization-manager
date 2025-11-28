package com.example.orgmanager.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizationCreateDTO {
    private String name;
    private CoordinatesDTO coordinates;
    private Double annualTurnover;
    private String fullName;
    private Integer employeesCount;
    private String type;
    private AddressDTO officialAddress;
}