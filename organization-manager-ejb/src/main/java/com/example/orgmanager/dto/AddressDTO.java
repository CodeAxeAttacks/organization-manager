package com.example.orgmanager.dto;

import jakarta.xml.bind.annotation.*;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "AddressDTO",
        namespace = "http://soap.orgmanager.example.com/",
        propOrder = {"street"}
)
public class AddressDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlElement(namespace = "http://soap.orgmanager.example.com/")
    private String street;

    public AddressDTO() {}
    public AddressDTO(String street) { this.street = street; }

    public static Builder builder() { return new Builder(); }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public static class Builder {
        private String street;
        public Builder street(String s) { this.street = s; return this; }
        public AddressDTO build() { return new AddressDTO(street); }
    }
}