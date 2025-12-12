// src/main/java/com/example/orgmanager/config/HttpClientConfig.java
package com.example.orgmanager.config;

import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@Configuration
public class HttpClientConfig {

    @Value("${service1.url}")
    private String service1Url;

    @Bean
    public RestTemplate restTemplate() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        SSLContext sslContext = SSLContextBuilder
                .create()
                .loadTrustMaterial(null, (certificate, authType) -> true)
                .build();

        SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(
                sslContext,
                (hostname, session) -> true
        );

        HttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder
                .create()
                .setSSLSocketFactory(sslSocketFactory)
                .build();

        HttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .build();

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        factory.setConnectTimeout(5000);

        RestTemplate restTemplate = new RestTemplate(factory);
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        return restTemplate;
    }

    @Bean
    public String service1BaseUrl() {
        return service1Url;
    }
}