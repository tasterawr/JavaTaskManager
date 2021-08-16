package org.exceptions;

public class InterfaceException extends RuntimeException{
    public InterfaceException(String message, Exception cause){
        super(message, cause);
    }

    public InterfaceException(String message) {
        super(message);
    }
}
