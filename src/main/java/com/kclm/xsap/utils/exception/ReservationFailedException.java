package com.kclm.xsap.utils.exception;

public class ReservationFailedException extends RuntimeException{
    public ReservationFailedException() {
    }

    public ReservationFailedException(String message) {
        super(message);
    }

    public ReservationFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
