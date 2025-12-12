// src/main/java/com/example/orgmanager/dto/AddressDTO.java
package com.example.orgmanager.dto;

public class AddressDTO {
    private String street;

    public AddressDTO() {}

    public AddressDTO(String street) {
        this.street = street;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getStreet() { return street; }
    public void setStreet(String street) { this.street = street; }

    public static class Builder {
        private String street;

        public Builder street(String street) { this.street = street; return this; }

        public AddressDTO build() {
            return new AddressDTO(street);
        }
    }
}