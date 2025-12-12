// src/main/java/com/example/orgmanager/dto/CoordinatesDTO.java
package com.example.orgmanager.dto;

public class CoordinatesDTO {
    private Double x;
    private Integer y;

    public CoordinatesDTO() {}

    public CoordinatesDTO(Double x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public static Builder builder() {
        return new Builder();
    }

    public Double getX() { return x; }
    public void setX(Double x) { this.x = x; }
    public Integer getY() { return y; }
    public void setY(Integer y) { this.y = y; }

    public static class Builder {
        private Double x;
        private Integer y;

        public Builder x(Double x) { this.x = x; return this; }
        public Builder y(Integer y) { this.y = y; return this; }

        public CoordinatesDTO build() {
            return new CoordinatesDTO(x, y);
        }
    }
}