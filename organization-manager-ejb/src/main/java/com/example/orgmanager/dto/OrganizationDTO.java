package com.example.orgmanager.dto;

import jakarta.xml.bind.annotation.*;
import java.io.Serializable;

@XmlRootElement(name = "organization", namespace = "http://soap.orgmanager.example.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "OrganizationDTO",
        namespace = "http://soap.orgmanager.example.com/",
        propOrder = {"id","name","coordinates","creationDate","annualTurnover","fullName","employeesCount","type","officialAddress"}
)
public class OrganizationDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlElement(namespace = "http://soap.orgmanager.example.com/")
    private Long id;
    @XmlElement(namespace = "http://soap.orgmanager.example.com/")
    private String name;
    @XmlElement(namespace = "http://soap.orgmanager.example.com/")
    private CoordinatesDTO coordinates;
    @XmlElement(namespace = "http://soap.orgmanager.example.com/")
    private String creationDate;
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

    public OrganizationDTO() {}

    public OrganizationDTO(Long id, String name, CoordinatesDTO coordinates, String creationDate,
                           Double annualTurnover, String fullName, Integer employeesCount,
                           String type, AddressDTO officialAddress) {
        this.id = id; this.name = name; this.coordinates = coordinates;
        this.creationDate = creationDate; this.annualTurnover = annualTurnover;
        this.fullName = fullName; this.employeesCount = employeesCount;
        this.type = type; this.officialAddress = officialAddress;
    }

    public static Builder builder() { return new Builder(); }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public CoordinatesDTO getCoordinates() { return coordinates; }
    public void setCoordinates(CoordinatesDTO coordinates) { this.coordinates = coordinates; }
    public String getCreationDate() { return creationDate; }
    public void setCreationDate(String creationDate) { this.creationDate = creationDate; }
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
        private Long id; private String name; private CoordinatesDTO coordinates;
        private String creationDate; private Double annualTurnover; private String fullName;
        private Integer employeesCount; private String type; private AddressDTO officialAddress;

        public Builder id(Long id) { this.id = id; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder coordinates(CoordinatesDTO c) { this.coordinates = c; return this; }
        public Builder creationDate(String d) { this.creationDate = d; return this; }
        public Builder annualTurnover(Double t) { this.annualTurnover = t; return this; }
        public Builder fullName(String fn) { this.fullName = fn; return this; }
        public Builder employeesCount(Integer ec) { this.employeesCount = ec; return this; }
        public Builder type(String t) { this.type = t; return this; }
        public Builder officialAddress(AddressDTO a) { this.officialAddress = a; return this; }

        public OrganizationDTO build() {
            return new OrganizationDTO(id, name, coordinates, creationDate,
                    annualTurnover, fullName, employeesCount, type, officialAddress);
        }
    }
}