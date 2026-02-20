package com.example.orgmanager.client;

import com.example.orgmanager.dto.OrganizationCreateDTO;
import com.example.orgmanager.dto.OrganizationDTO;
import com.example.orgmanager.dto.PageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.LocalBean;
import java.util.List;

@Singleton
@Startup
@LocalBean
public class OrganizationClient {

    private static final Logger log = LoggerFactory.getLogger(OrganizationClient.class);

    private RestTemplate restTemplate;
    private String baseUrl;

    @PostConstruct
    public void init() {
        // Прямой URL к organization-service, без Consul
        this.baseUrl = System.getProperty("service1.url",
                System.getenv().getOrDefault("SERVICE1_URL",
                        "http://localhost:18018/api/v1/organizations"));

        this.restTemplate = createRestTemplate();
        log.info("OrganizationClient initialized, service URL: {}", baseUrl);
    }

    private RestTemplate createRestTemplate() {
        org.springframework.http.client.SimpleClientHttpRequestFactory factory =
                new org.springframework.http.client.SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5000);
        factory.setReadTimeout(10000);

        RestTemplate template = new RestTemplate(factory);
        template.getMessageConverters().clear();

        org.springframework.http.converter.json.MappingJackson2HttpMessageConverter jsonConverter =
                new org.springframework.http.converter.json.MappingJackson2HttpMessageConverter();
        jsonConverter.setSupportedMediaTypes(java.util.Arrays.asList(
                org.springframework.http.MediaType.APPLICATION_JSON
        ));
        template.getMessageConverters().add(jsonConverter);

        return template;
    }

    public OrganizationDTO getOrganization(Long id) {
        try {
            String url = baseUrl + "/" + id;
            ResponseEntity<OrganizationDTO> response = restTemplate.getForEntity(url, OrganizationDTO.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            log.error("Failed to get organization {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to get organization: " + e.getStatusCode());
        }
    }

    public OrganizationDTO createOrganization(OrganizationCreateDTO dto) {
        try {
            ResponseEntity<OrganizationDTO> response = restTemplate.postForEntity(
                    baseUrl, dto, OrganizationDTO.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            log.error("Failed to create organization: {}", e.getMessage());
            throw new RuntimeException("Failed to create organization: " + e.getStatusCode());
        }
    }

    public OrganizationDTO updateOrganization(Long id, OrganizationCreateDTO dto) {
        try {
            String url = baseUrl + "/" + id;
            HttpEntity<OrganizationCreateDTO> request = new HttpEntity<>(dto);
            ResponseEntity<OrganizationDTO> response = restTemplate.exchange(
                    url, HttpMethod.PUT, request, OrganizationDTO.class);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            log.error("Failed to update organization {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to update organization: " + e.getStatusCode());
        }
    }

    public void deleteOrganization(Long id) {
        try {
            restTemplate.delete(baseUrl + "/" + id);
        } catch (HttpClientErrorException e) {
            log.error("Failed to delete organization {}: {}", id, e.getMessage());
            throw new RuntimeException("Failed to delete organization: " + e.getStatusCode());
        }
    }

    public OrganizationDTO[] getAllOrganizations() {
        try {
            ParameterizedTypeReference<PageResponse<OrganizationDTO>> typeRef =
                    new ParameterizedTypeReference<PageResponse<OrganizationDTO>>() {};
            ResponseEntity<PageResponse<OrganizationDTO>> response = restTemplate.exchange(
                    baseUrl, HttpMethod.GET, null, typeRef);

            PageResponse<OrganizationDTO> page = response.getBody();
            if (page == null || page.getContent() == null) {
                return new OrganizationDTO[0];
            }
            List<OrganizationDTO> orgs = page.getContent();
            return orgs.toArray(new OrganizationDTO[0]);
        } catch (Exception e) {
            log.error("Failed to get all organizations: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to get organizations: " + e.getMessage());
        }
    }
}