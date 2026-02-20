package com.example.orgmanager.dto;

import jakarta.xml.bind.annotation.*;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "CoordinatesDTO",
        namespace = "http://soap.orgmanager.example.com/",
        propOrder = {"x", "y"}
)
public class CoordinatesDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlElement(namespace = "http://soap.orgmanager.example.com/")
    private Double x;
    @XmlElement(namespace = "http://soap.orgmanager.example.com/")
    private Integer y;

    public CoordinatesDTO() {}
    public CoordinatesDTO(Double x, Integer y) { this.x = x; this.y = y; }

    public static Builder builder() { return new Builder(); }

    public Double getX() { return x; }
    public void setX(Double x) { this.x = x; }
    public Integer getY() { return y; }
    public void setY(Integer y) { this.y = y; }

    public static class Builder {
        private Double x; private Integer y;
        public Builder x(Double x) { this.x = x; return this; }
        public Builder y(Integer y) { this.y = y; return this; }
        public CoordinatesDTO build() { return new CoordinatesDTO(x, y); }
    }
}