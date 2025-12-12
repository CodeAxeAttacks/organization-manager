// src/main/java/com/example/orgmanager/dto/OrganizationDTO.java
package com.example.orgmanager.dto;

import java.time.LocalDateTime;

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

    public OrganizationDTO() {}

    public OrganizationDTO(Long id, String name, CoordinatesDTO coordinates, LocalDateTime creationDate,
                           Double annualTurnover, String fullName, Integer employeesCount,
                           String type, AddressDTO officialAddress) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.annualTurnover = annualTurnover;
        this.fullName = fullName;
        this.employeesCount = employeesCount;
        this.type = type;
        this.officialAddress = officialAddress;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public CoordinatesDTO getCoordinates() { return coordinates; }
    public void setCoordinates(CoordinatesDTO coordinates) { this.coordinates = coordinates; }
    public LocalDateTime getCreationDate() { return creationDate; }
    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }
    public Double getAnnualTurnover() { return annualTurnover; }
    public void setAnnualTurnover(Double annualTurnover) { this.annualTurnover = annualTurnover; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public Integer getEmployeesCount() { return employeesCount; }
    public void setEmployeesCount(Integer employeesCount) { this.employeesCount = employeesCount; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public AddressDTO getOfficialAddress() { return officialAddress; }
    public void setOfficialAddress(AddressDTO officialAddress) { this.officialAddress = officialAddress; }

    public static class Builder {
        private Long id;
        private String name;
        private CoordinatesDTO coordinates;
        private LocalDateTime creationDate;
        private Double annualTurnover;
        private String fullName;
        private Integer employeesCount;
        private String type;
        private AddressDTO officialAddress;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder coordinates(CoordinatesDTO coordinates) { this.coordinates = coordinates; return this; }
        public Builder creationDate(LocalDateTime creationDate) { this.creationDate = creationDate; return this; }
        public Builder annualTurnover(Double annualTurnover) { this.annualTurnover = annualTurnover; return this; }
        public Builder fullName(String fullName) { this.fullName = fullName; return this; }
        public Builder employeesCount(Integer employeesCount) { this.employeesCount = employeesCount; return this; }
        public Builder type(String type) { this.type = type; return this; }
        public Builder officialAddress(AddressDTO officialAddress) { this.officialAddress = officialAddress; return this; }

        public OrganizationDTO build() {
            return new OrganizationDTO(id, name, coordinates, creationDate, annualTurnover,
                    fullName, employeesCount, type, officialAddress);
        }
    }
}