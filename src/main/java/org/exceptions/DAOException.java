package org.exceptions;

public class DAOException extends RuntimeException{
    public DAOException(String message, Exception cause){
        super(message, cause);
    }

    public DAOException(String message) {
        super(message);
    }
}
