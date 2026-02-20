package com.example.orgmanager.dto;

import jakarta.xml.bind.annotation.*;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "OrganizationCreateDTO",
        namespace = "http://soap.orgmanager.example.com/",
        propOrder = {"name","coordinates","annualTurnover","fullName","employeesCount","type","officialAddress"}
)
public class OrganizationCreateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlElement(namespace = "http://soap.orgmanager.example.com/")
    private String name;
    @XmlElement(namespace = "http://soap.orgmanager.example.com/")
    private CoordinatesDTO coordinates;
    @XmlElement(namespace = "http://soap.orgmanager.example.com/")
    private Double annualTurnover;
    @XmlElement(namespace = "http://soap.orgmanager.example.com/")
    private String fullName;
    @XmlElement(namespace = "http://soap.orgmanager.example.com/")
    private Integer employeesCount;
    @XmlElement(namespace = "http://soap.orgmanager.example.com/")
    private String type;
    @XmlElement(namespace = "http://soap.orgmanager.example.com/")
    private AddressDTO officialAddress;

    public OrganizationCreateDTO() {}

    public OrganizationCreateDTO(String name, CoordinatesDTO coordinates, Double annualTurnover,
                                 String fullName, Integer employeesCount, String type,
                                 AddressDTO officialAddress) {
        this.name = name; this.coordinates = coordinates; this.annualTurnover = annualTurnover;
        this.fullName = fullName; this.employeesCount = employeesCount;
        this.type = type; this.officialAddress = officialAddress;
    }

    public static Builder builder() { return new Builder(); }

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
        private String name; private CoordinatesDTO coordinates; private Double annualTurnover;
        private String fullName; private Integer employeesCount; private String type;
        private AddressDTO officialAddress;

        public Builder name(String n) { this.name = n; return this; }
        public Builder coordinates(CoordinatesDTO c) { this.coordinates = c; return this; }
        public Builder annualTurnover(Double t) { this.annualTurnover = t; return this; }
        public Builder fullName(String fn) { this.fullName = fn; return this; }
        public Builder employeesCount(Integer ec) { this.employeesCount = ec; return this; }
        public Builder type(String t) { this.type = t; return this; }
        public Builder officialAddress(AddressDTO a) { this.officialAddress = a; return this; }

        public OrganizationCreateDTO build() {
            return new OrganizationCreateDTO(name, coordinates, annualTurnover, fullName,
                    employeesCount, type, officialAddress);
        }
    }
}