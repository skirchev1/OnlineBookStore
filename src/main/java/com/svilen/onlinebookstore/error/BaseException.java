package com.svilen.onlinebookstore.error;

public class BaseException extends RuntimeException{

    private int statusCode;

       public BaseException(int statusCode, String message) {
        super(message);
        this.setStatusCode(409);
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
