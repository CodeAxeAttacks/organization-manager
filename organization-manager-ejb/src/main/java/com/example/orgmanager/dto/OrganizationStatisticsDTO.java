package com.example.orgmanager.dto;

import jakarta.xml.bind.annotation.*;
import java.io.Serializable;

@XmlRootElement(name = "statistics", namespace = "http://soap.orgmanager.example.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(
        name = "OrganizationStatisticsDTO",
        namespace = "http://soap.orgmanager.example.com/",
        propOrder = {"totalOrganizations","totalEmployees","averageEmployeesPerOrganization","totalAnnualTurnover","processingTimeMs"}
)
public class OrganizationStatisticsDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @XmlElement(namespace = "http://soap.orgmanager.example.com/")
    private int totalOrganizations;
    @XmlElement(namespace = "http://soap.orgmanager.example.com/")
    private long totalEmployees;
    @XmlElement(namespace = "http://soap.orgmanager.example.com/")
    private double averageEmployeesPerOrganization;
    @XmlElement(namespace = "http://soap.orgmanager.example.com/")
    private Double totalAnnualTurnover;
    @XmlElement(namespace = "http://soap.orgmanager.example.com/")
    private long processingTimeMs;

    public OrganizationStatisticsDTO() {}

    public OrganizationStatisticsDTO(int totalOrganizations, long totalEmployees,
                                     double averageEmployeesPerOrganization,
                                     Double totalAnnualTurnover, long processingTimeMs) {
        this.totalOrganizations = totalOrganizations;
        this.totalEmployees = totalEmployees;
        this.averageEmployeesPerOrganization = averageEmployeesPerOrganization;
        this.totalAnnualTurnover = totalAnnualTurnover;
        this.processingTimeMs = processingTimeMs;
    }

    public int getTotalOrganizations() { return totalOrganizations; }
    public void setTotalOrganizations(int v) { this.totalOrganizations = v; }
    public long getTotalEmployees() { return totalEmployees; }
    public void setTotalEmployees(long v) { this.totalEmployees = v; }
    public double getAverageEmployeesPerOrganization() { return averageEmployeesPerOrganization; }
    public void setAverageEmployeesPerOrganization(double v) { this.averageEmployeesPerOrganization = v; }
    public Double getTotalAnnualTurnover() { return totalAnnualTurnover; }
    public void setTotalAnnualTurnover(Double v) { this.totalAnnualTurnover = v; }
    public long getProcessingTimeMs() { return processingTimeMs; }
    public void setProcessingTimeMs(long v) { this.processingTimeMs = v; }
}