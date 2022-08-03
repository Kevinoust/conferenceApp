package com.conference.sessionservice.exception;

import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * ResponseErrorHandler for RestTemplate
 */
@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().isError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if(response.getStatusCode().is4xxClientError()) {
            throw HttpClientErrorException.create(response.getStatusCode(), response.getStatusText(), response.getHeaders(), response.getBody().readAllBytes(), UTF_8);
        } else if (response.getStatusCode().is5xxServerError()) {
            throw HttpServerErrorException.create(response.getStatusCode(), response.getStatusText(), response.getHeaders(), response.getBody().readAllBytes(), UTF_8);
        }
    }
}
