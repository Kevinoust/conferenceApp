package com.conference.sessionservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
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
}
