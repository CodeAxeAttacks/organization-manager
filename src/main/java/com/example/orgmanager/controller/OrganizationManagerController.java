// src/main/java/com/example/orgmanager/controller/OrganizationManagerController.java
package com.example.orgmanager.controller;

import com.example.orgmanager.dto.OrganizationDTO;
import com.example.orgmanager.service.OrganizationManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class OrganizationManagerController {

    private final OrganizationManagerService managerService;

    @Autowired
    public OrganizationManagerController(OrganizationManagerService managerService) {
        this.managerService = managerService;
    }

    @PostMapping("/merge/{id1}/{id2}/{newName}/{newAddress}")
    public ResponseEntity<OrganizationDTO> mergeOrganizations(
            @PathVariable Long id1,
            @PathVariable Long id2,
            @PathVariable String newName,
            @PathVariable String newAddress) {

        OrganizationDTO merged = managerService.mergeOrganizations(id1, id2, newName, newAddress);
        return ResponseEntity.status(HttpStatus.CREATED).body(merged);
    }

    @PostMapping("/hire/{id}")
    public ResponseEntity<OrganizationDTO> hireEmployee(@PathVariable Long id) {
        OrganizationDTO updated = managerService.hireEmployee(id);
        return ResponseEntity.ok(updated);
    }
}