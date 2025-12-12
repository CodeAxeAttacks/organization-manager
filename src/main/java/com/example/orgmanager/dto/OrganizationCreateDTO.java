// src/main/java/com/example/orgmanager/dto/OrganizationCreateDTO.java
package com.example.orgmanager.dto;

public class OrganizationCreateDTO {
    private String name;
    private CoordinatesDTO coordinates;
    private Double annualTurnover;
    private String fullName;
    private Integer employeesCount;
    private String type;
    private AddressDTO officialAddress;

    public OrganizationCreateDTO() {}

    public OrganizationCreateDTO(String name, CoordinatesDTO coordinates, Double annualTurnover,
                                 String fullName, Integer employeesCount, String type,
                                 AddressDTO officialAddress) {
        this.name = name;
        this.coordinates = coordinates;
        this.annualTurnover = annualTurnover;
        this.fullName = fullName;
        this.employeesCount = employeesCount;
        this.type = type;
        this.officialAddress = officialAddress;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public CoordinatesDTO getCoordinates() { return coordinates; }
    public void setCoordinates(CoordinatesDTO coordinates) { this.coordinates = coordinates; }
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
        private String name;
        private CoordinatesDTO coordinates;
        private Double annualTurnover;
        private String fullName;
        private Integer employeesCount;
        private String type;
        private AddressDTO officialAddress;

        public Builder name(String name) { this.name = name; return this; }
        public Builder coordinates(CoordinatesDTO coordinates) { this.coordinates = coordinates; return this; }
        public Builder annualTurnover(Double annualTurnover) { this.annualTurnover = annualTurnover; return this; }
        public Builder fullName(String fullName) { this.fullName = fullName; return this; }
        public Builder employeesCount(Integer employeesCount) { this.employeesCount = employeesCount; return this; }
        public Builder type(String type) { this.type = type; return this; }
        public Builder officialAddress(AddressDTO officialAddress) { this.officialAddress = officialAddress; return this; }

        public OrganizationCreateDTO build() {
            return new OrganizationCreateDTO(name, coordinates, annualTurnover, fullName,
                    employeesCount, type, officialAddress);
        }
    }
}