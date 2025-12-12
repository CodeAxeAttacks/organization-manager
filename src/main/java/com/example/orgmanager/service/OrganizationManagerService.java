// src/main/java/com/example/orgmanager/service/OrganizationManagerService.java
package com.example.orgmanager.service;

import com.example.orgmanager.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationManagerService {

    private static final Logger log = LoggerFactory.getLogger(OrganizationManagerService.class);

    private final OrganizationClient organizationClient;

    @Autowired
    public OrganizationManagerService(OrganizationClient organizationClient) {
        this.organizationClient = organizationClient;
    }

    public OrganizationDTO mergeOrganizations(Long id1, Long id2, String newName, String newAddress) {
        if (id1.equals(id2)) {
            throw new IllegalArgumentException("Cannot merge organization with itself: id1 and id2 must be different");
        }
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("newName cannot be empty");
        }
        if (newAddress == null || newAddress.trim().isEmpty()) {
            throw new IllegalArgumentException("newAddress cannot be empty");
        }

        log.info("Merging organizations {} and {} into '{}'", id1, id2, newName);

        OrganizationDTO org1 = organizationClient.getOrganization(id1);
        OrganizationDTO org2 = organizationClient.getOrganization(id2);

        OrganizationCreateDTO mergedDto = OrganizationCreateDTO.builder()
                .name(newName)
                .coordinates(org1.getCoordinates())
                .annualTurnover(calculateMergedTurnover(org1.getAnnualTurnover(), org2.getAnnualTurnover()))
                .fullName(newName + " International")
                .employeesCount(org1.getEmployeesCount() + org2.getEmployeesCount())
                .type(org1.getType())
                .officialAddress(AddressDTO.builder().street(newAddress).build())
                .build();

        OrganizationDTO merged = organizationClient.createOrganization(mergedDto);

        organizationClient.deleteOrganization(id1);
        organizationClient.deleteOrganization(id2);

        log.info("Successfully merged organizations {} and {} into new organization {}", id1, id2, merged.getId());

        return merged;
    }

    public OrganizationDTO hireEmployee(Long id) {
        log.info("Hiring employee for organization {}", id);

        OrganizationDTO organization = organizationClient.getOrganization(id);

        OrganizationCreateDTO updateDto = OrganizationCreateDTO.builder()
                .name(organization.getName())
                .coordinates(organization.getCoordinates())
                .annualTurnover(organization.getAnnualTurnover())
                .fullName(organization.getFullName())
                .employeesCount(organization.getEmployeesCount() + 1)
                .type(organization.getType())
                .officialAddress(organization.getOfficialAddress())
                .build();

        OrganizationDTO updated = organizationClient.updateOrganization(id, updateDto);

        log.info("Successfully hired employee for organization {}. New count: {}", id, updated.getEmployeesCount());

        return updated;
    }

    private Double calculateMergedTurnover(Double turnover1, Double turnover2) {
        if (turnover1 != null && turnover2 != null) {
            return turnover1 + turnover2;
        } else if (turnover1 != null) {
            return turnover1;
        } else if (turnover2 != null) {
            return turnover2;
        }
        return null;
    }
}