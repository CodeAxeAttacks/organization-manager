package com.example.orgmanager.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizationDTO {
    private Long id;
    private String name;
    private CoordinatesDTO coordinates;
    private LocalDateTime creationDate;
    private Double annualTurnover;
    private String fullName;
    private Integer employeesCount;
    private String type;
    private AddressDTO officialAddress;
}