package com.conference.attendeeticketservice.exception;

public class ResourceNotFoundException extends RuntimeException {
    private String resourceName;

    public ResourceNotFoundException(String resourceName) {
        super(resourceName + " not found!");
        this.resourceName = resourceName;;
    }

    public String getResourceName() {
        return resourceName;
    }
}
