package com.example.orgmanager.controller;

import com.example.orgmanager.dto.OrganizationDTO;
import com.example.orgmanager.service.OrganizationManagerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrganizationManagerController {

    private final OrganizationManagerService managerService;

    /**
     * POST /orgmanager/merge/{id1}/{id2}/{newName}/{newAddress}
     * Объединить две организации
     */
    @PostMapping("/merge/{id1}/{id2}/{newName}/{newAddress}")
    public ResponseEntity<OrganizationDTO> mergeOrganizations(
            @PathVariable Long id1,
            @PathVariable Long id2,
            @PathVariable String newName,
            @PathVariable String newAddress) {

        OrganizationDTO merged = managerService.mergeOrganizations(id1, id2, newName, newAddress);
        return ResponseEntity.status(HttpStatus.CREATED).body(merged);
    }

    /**
     * POST /orgmanager/hire/{id}
     * Нанять сотрудника
     */
    @PostMapping("/hire/{id}")
    public ResponseEntity<OrganizationDTO> hireEmployee(@PathVariable Long id) {
        OrganizationDTO updated = managerService.hireEmployee(id);
        return ResponseEntity.ok(updated);
    }
}