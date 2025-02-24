package com.example.sendy.exception;

public class NoResponseData extends RuntimeException{
    public NoResponseData (String message){
        super(message);
    }
}
