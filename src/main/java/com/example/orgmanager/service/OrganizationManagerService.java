package com.example.orgmanager.service;

import com.example.orgmanager.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrganizationManagerService {

    private final OrganizationClient organizationClient;

    /**
     * Объединить две организации
     */
    public OrganizationDTO mergeOrganizations(Long id1, Long id2, String newName, String newAddress) {
        // Валидация
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

        // 1. Получить обе организации
        OrganizationDTO org1 = organizationClient.getOrganization(id1);
        OrganizationDTO org2 = organizationClient.getOrganization(id2);

        // 2. Создать новую объединенную организацию
        OrganizationCreateDTO mergedDto = OrganizationCreateDTO.builder()
                .name(newName)
                .coordinates(org1.getCoordinates()) // От первой организации
                .annualTurnover(calculateMergedTurnover(org1.getAnnualTurnover(), org2.getAnnualTurnover()))
                .fullName(newName + " International") // Формируем на основе newName
                .employeesCount(org1.getEmployeesCount() + org2.getEmployeesCount()) // Сумма
                .type(org1.getType()) // От первой организации
                .officialAddress(AddressDTO.builder().street(newAddress).build())
                .build();

        OrganizationDTO merged = organizationClient.createOrganization(mergedDto);

        // 3. Удалить исходные организации
        organizationClient.deleteOrganization(id1);
        organizationClient.deleteOrganization(id2);

        log.info("Successfully merged organizations {} and {} into new organization {}", id1, id2, merged.getId());

        return merged;
    }

    /**
     * Нанять сотрудника в организацию
     */
    public OrganizationDTO hireEmployee(Long id) {
        log.info("Hiring employee for organization {}", id);

        // 1. Получить организацию
        OrganizationDTO organization = organizationClient.getOrganization(id);

        // 2. Увеличить количество сотрудников
        OrganizationCreateDTO updateDto = OrganizationCreateDTO.builder()
                .name(organization.getName())
                .coordinates(organization.getCoordinates())
                .annualTurnover(organization.getAnnualTurnover())
                .fullName(organization.getFullName())
                .employeesCount(organization.getEmployeesCount() + 1) // +1 сотрудник
                .type(organization.getType())
                .officialAddress(organization.getOfficialAddress())
                .build();

        // 3. Обновить организацию
        OrganizationDTO updated = organizationClient.updateOrganization(id, updateDto);

        log.info("Successfully hired employee for organization {}. New count: {}", id, updated.getEmployeesCount());

        return updated;
    }

    /**
     * Рассчитать объединенный оборот
     */
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