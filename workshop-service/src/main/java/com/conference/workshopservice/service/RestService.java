package com.conference.workshopservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestService {
    @Autowired
    private RestTemplate restTemplate;

    // generic GET
    public <T> T getForObject(String uri, ParameterizedTypeReference<T> responseType, Object... params) {
        return restTemplate.exchange(uri, HttpMethod.GET, null, responseType, params).getBody();
    }

    // generic POST
    public <T> T postForObject(String uri, ParameterizedTypeReference<T> responseType, Object requestBody, Object... uriParams) {
        return restTemplate.exchange(uri, HttpMethod.POST, RequestEntity.post(uri).contentType(MediaType.APPLICATION_JSON).body(requestBody), responseType, uriParams).getBody();
    }
}
