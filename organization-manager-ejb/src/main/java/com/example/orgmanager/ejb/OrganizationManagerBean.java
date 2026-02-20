package com.example.orgmanager.ejb;

import com.example.orgmanager.client.OrganizationClient;
import com.example.orgmanager.dto.*;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.jboss.ejb3.annotation.Pool;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Stateless Session Bean implementing organization management business logic
 * Configured with pool settings for scalability
 */
@Stateless
public class OrganizationManagerBean implements OrganizationManagerRemote {

    private static final Logger log = LoggerFactory.getLogger(OrganizationManagerBean.class);
    private static final AtomicInteger instanceCounter = new AtomicInteger(0);
    private static final AtomicInteger activeInstances = new AtomicInteger(0);

    private int instanceId;

    @EJB
    private OrganizationClient organizationClient;

    @PostConstruct
    public void onCreate() {
        instanceId = instanceCounter.incrementAndGet();
        int active = activeInstances.incrementAndGet();
        log.info("EJB POOL: Created instance #{} | Active instances: {}", instanceId, active);
    }

    @PreDestroy
    public void onDestroy() {
        int active = activeInstances.decrementAndGet();
        log.info("EJB POOL: Destroyed instance #{} | Active instances: {}", instanceId, active);
    }

    @Override
    public OrganizationDTO mergeOrganizations(Long id1, Long id2, String newName, String newAddress) {
        log.info("EJB POOL: Instance #{} acquired for mergeOrganizations", instanceId);

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
        log.info("EJB POOL: Instance #{} released from mergeOrganizations", instanceId);

        return merged;
    }

    @Override
    public OrganizationDTO hireEmployee(Long id) {
        long startTime = System.currentTimeMillis();
        log.info("EJB POOL: Instance #{} acquired for hireEmployee", instanceId);
        log.info("Hiring employee for organization {}", id);

        // Step 1: Get the target organization
        OrganizationDTO organization = organizationClient.getOrganization(id);
        log.debug("Retrieved organization: {}", organization.getName());

        // Step 2: Validate organization exists and has required data
        if (organization.getName() == null || organization.getName().trim().isEmpty()) {
            throw new IllegalStateException("Organization has no name");
        }

        // Step 3: Get all organizations to calculate company statistics
        log.debug("Fetching all organizations for validation and statistics...");
        OrganizationDTO[] allOrganizations = organizationClient.getAllOrganizations();

        // Step 4: Calculate total employees across all organizations
        long totalEmployeesBeforeHire = 0;
        int totalOrganizations = 0;
        if (allOrganizations != null) {
            totalOrganizations = allOrganizations.length;
            for (OrganizationDTO org : allOrganizations) {
                if (org.getEmployeesCount() != null) {
                    totalEmployeesBeforeHire += org.getEmployeesCount();
                }
            }
        }

        log.info("Current statistics - Total organizations: {}, Total employees: {}",
                 totalOrganizations, totalEmployeesBeforeHire);

        // Step 5: Business rule validation - check if organization can hire more employees
        int currentEmployees = organization.getEmployeesCount() != null ? organization.getEmployeesCount() : 0;
        final int MAX_EMPLOYEES_PER_ORG = 10000;

        if (currentEmployees >= MAX_EMPLOYEES_PER_ORG) {
            throw new IllegalStateException(
                String.format("Organization %s has reached maximum employee limit of %d",
                             organization.getName(), MAX_EMPLOYEES_PER_ORG));
        }

        // Step 6: Calculate new employee count and percentage increase
        int newEmployeeCount = currentEmployees + 1;
        double percentageIncrease = currentEmployees > 0
            ? ((double)(newEmployeeCount - currentEmployees) / currentEmployees) * 100
            : 100.0;

        log.debug("Hiring will increase employee count by {:.2f}%", percentageIncrease);

        // Step 7: Simulate processing time for complex business logic
        // This helps demonstrate EJB pool scaling under load
        try {
            Thread.sleep(100); // Simulate 100ms of processing
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Processing interrupted", e);
        }

        // Step 8: Prepare update DTO
        OrganizationCreateDTO updateDto = OrganizationCreateDTO.builder()
                .name(organization.getName())
                .coordinates(organization.getCoordinates())
                .annualTurnover(organization.getAnnualTurnover())
                .fullName(organization.getFullName())
                .employeesCount(newEmployeeCount)
                .type(organization.getType())
                .officialAddress(organization.getOfficialAddress())
                .build();

        // Step 9: Update the organization
        OrganizationDTO updated = organizationClient.updateOrganization(id, updateDto);

        long duration = System.currentTimeMillis() - startTime;
        log.info("Successfully hired employee for organization {}. New count: {}. " +
                 "Total processing time: {}ms. New global employee count: {}",
                 id, updated.getEmployeesCount(), duration, totalEmployeesBeforeHire + 1);
        log.info("EJB POOL: Instance #{} released from hireEmployee", instanceId);

        return updated;
    }

    @Override
    public OrganizationStatisticsDTO getOrganizationStatistics() {
        long startTime = System.currentTimeMillis();
        log.info("EJB POOL: Instance #{} acquired for getOrganizationStatistics", instanceId);
        log.info("Calculating organization statistics");

        // Step 1: Fetch all organizations from the service
        OrganizationDTO[] allOrganizations = organizationClient.getAllOrganizations();

        // Step 2: Calculate statistics
        int totalOrganizations = 0;
        long totalEmployees = 0;
        double totalAnnualTurnover = 0.0;
        int organizationsWithTurnover = 0;

        if (allOrganizations != null) {
            totalOrganizations = allOrganizations.length;

            for (OrganizationDTO org : allOrganizations) {
                // Count employees
                if (org.getEmployeesCount() != null) {
                    totalEmployees += org.getEmployeesCount();
                }

                // Sum turnover
                if (org.getAnnualTurnover() != null) {
                    totalAnnualTurnover += org.getAnnualTurnover();
                    organizationsWithTurnover++;
                }
            }
        }

        // Step 3: Calculate average
        double averageEmployees = totalOrganizations > 0
            ? (double) totalEmployees / totalOrganizations
            : 0.0;

        // Step 4: Simulate complex processing
        try {
            Thread.sleep(50); // Simulate 50ms of processing
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Statistics processing interrupted", e);
        }

        long processingTime = System.currentTimeMillis() - startTime;

        OrganizationStatisticsDTO statistics = new OrganizationStatisticsDTO(
            totalOrganizations,
            totalEmployees,
            averageEmployees,
            totalAnnualTurnover,
            processingTime
        );

        log.info("Statistics calculated: {} organizations, {} total employees, {:.2f} avg employees/org. " +
                 "Processing time: {}ms",
                 totalOrganizations, totalEmployees, averageEmployees, processingTime);
        log.info("EJB POOL: Instance #{} released from getOrganizationStatistics", instanceId);

        return statistics;
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
