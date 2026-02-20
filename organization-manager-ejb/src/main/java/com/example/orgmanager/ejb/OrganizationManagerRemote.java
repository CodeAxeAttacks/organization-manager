package com.example.orgmanager.ejb;

import com.example.orgmanager.dto.OrganizationDTO;
import com.example.orgmanager.dto.OrganizationStatisticsDTO;

import jakarta.ejb.Remote;

/**
 * Remote business interface for Organization Manager EJB
 */
@Remote
public interface OrganizationManagerRemote {

    /**
     * Merge two organizations into a new one
     *
     * @param id1 First organization ID
     * @param id2 Second organization ID
     * @param newName Name for the merged organization
     * @param newAddress Address for the merged organization
     * @return The newly created merged organization
     */
    OrganizationDTO mergeOrganizations(Long id1, Long id2, String newName, String newAddress);

    /**
     * Hire an employee for an organization
     *
     * @param id Organization ID
     * @return Updated organization with incremented employee count
     */
    OrganizationDTO hireEmployee(Long id);

    /**
     * Get statistics for all organizations
     *
     * @return Statistics including total organizations, employees, and turnover
     */
    OrganizationStatisticsDTO getOrganizationStatistics();
}
