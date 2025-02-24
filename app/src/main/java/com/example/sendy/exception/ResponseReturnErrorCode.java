package com.example.sendy.exception;

public class ResponseReturnErrorCode extends RuntimeException{
    public ResponseReturnErrorCode(String message){
        super(message);
    }
}
