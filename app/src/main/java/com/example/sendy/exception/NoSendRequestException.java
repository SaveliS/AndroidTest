package com.example.sendy.exception;

import com.example.sendy.MainActivity;

public class NoSendRequestException extends RuntimeException{
    public NoSendRequestException(String message){
        super(message);
    }
}
