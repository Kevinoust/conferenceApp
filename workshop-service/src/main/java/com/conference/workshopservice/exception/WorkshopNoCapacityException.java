package com.conference.workshopservice.exception;

public class WorkshopNoCapacityException extends RuntimeException {

    public WorkshopNoCapacityException(Long workshopId) {
        super("Workshop (" + workshopId + ") has no capacity!");
    }
}
