package com.conference.workshopservice.exception;

public class ServiceNotAvailableException extends RuntimeException {
    public ServiceNotAvailableException(String serviceName) {
        super("Failed to connect to service [" + serviceName + "]");
    }
}