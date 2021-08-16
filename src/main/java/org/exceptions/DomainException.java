package org.exceptions;

public class DomainException extends RuntimeException {
    public DomainException(String message, Exception cause){
        super(message, cause);
    }

    public DomainException(String message) {
        super(message);
    }
}
