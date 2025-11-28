package com.example.orgmanager.service;

import com.example.orgmanager.dto.OrganizationCreateDTO;
import com.example.orgmanager.dto.OrganizationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrganizationClient {

    private final WebClient webClient;

    /**
     * Получить организацию по ID из Service 1
     */
    public OrganizationDTO getOrganization(Long id) {
        try {
            return webClient.get()
                    .uri("/{id}", id)
                    .retrieve()
                    .bodyToMono(OrganizationDTO.class)
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Failed to get organization {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to get organization from Service 1: " + e.getStatusCode());
        }
    }

    /**
     * Создать организацию в Service 1
     */
    public OrganizationDTO createOrganization(OrganizationCreateDTO dto) {
        try {
            return webClient.post()
                    .bodyValue(dto)
                    .retrieve()
                    .bodyToMono(OrganizationDTO.class)
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Failed to create organization: {}", e.getMessage());
            throw new RuntimeException("Failed to create organization in Service 1: " + e.getStatusCode());
        }
    }

    /**
     * Обновить организацию в Service 1
     */
    public OrganizationDTO updateOrganization(Long id, OrganizationCreateDTO dto) {
        try {
            return webClient.put()
                    .uri("/{id}", id)
                    .bodyValue(dto)
                    .retrieve()
                    .bodyToMono(OrganizationDTO.class)
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Failed to update organization {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to update organization in Service 1: " + e.getStatusCode());
        }
    }

    /**
     * Удалить организацию из Service 1
     */
    public void deleteOrganization(Long id) {
        try {
            webClient.delete()
                    .uri("/{id}", id)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Failed to delete organization {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to delete organization from Service 1: " + e.getStatusCode());
        }
    }
}