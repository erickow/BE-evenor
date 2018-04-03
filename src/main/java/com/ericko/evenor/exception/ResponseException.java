package com.ericko.evenor.exception;

public class ResponseException extends RuntimeException {
    public ResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResponseException(String message){
        super(message);
    }

    public ResponseException(Throwable cause){
        super(cause);
    }
    public ResponseException(){
        super();
    }
}
