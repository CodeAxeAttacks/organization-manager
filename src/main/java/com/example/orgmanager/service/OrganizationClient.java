// src/main/java/com/example/orgmanager/service/OrganizationClient.java
package com.example.orgmanager.service;

import com.example.orgmanager.dto.OrganizationCreateDTO;
import com.example.orgmanager.dto.OrganizationDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class OrganizationClient {

    private static final Logger log = LoggerFactory.getLogger(OrganizationClient.class);

    private final RestTemplate restTemplate;
    private final String baseUrl;

    @Autowired
    public OrganizationClient(RestTemplate restTemplate, @Qualifier("service1BaseUrl") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public OrganizationDTO getOrganization(Long id) {
        try {
            String url = baseUrl + "/" + id;
            ResponseEntity<OrganizationDTO> response = restTemplate.getForEntity(url, OrganizationDTO.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            log.error("Failed to get organization {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to get organization from Service 1: " + e.getStatusCode());
        }
    }

    public OrganizationDTO createOrganization(OrganizationCreateDTO dto) {
        try {
            ResponseEntity<OrganizationDTO> response = restTemplate.postForEntity(
                    baseUrl,
                    dto,
                    OrganizationDTO.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            log.error("Failed to create organization: {}", e.getMessage());
            throw new RuntimeException("Failed to create organization in Service 1: " + e.getStatusCode());
        }
    }

    public OrganizationDTO updateOrganization(Long id, OrganizationCreateDTO dto) {
        try {
            String url = baseUrl + "/" + id;
            HttpEntity<OrganizationCreateDTO> request = new HttpEntity<>(dto);
            ResponseEntity<OrganizationDTO> response = restTemplate.exchange(
                    url,
                    HttpMethod.PUT,
                    request,
                    OrganizationDTO.class
            );
            return response.getBody();
        } catch (HttpClientErrorException e) {
            log.error("Failed to update organization {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to update organization in Service 1: " + e.getStatusCode());
        }
    }

    public void deleteOrganization(Long id) {
        try {
            String url = baseUrl + "/" + id;
            restTemplate.delete(url);
        } catch (HttpClientErrorException e) {
            log.error("Failed to delete organization {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to delete organization from Service 1: " + e.getStatusCode());
        }
    }
}