package com.example.sendy.service;

public interface TermsCallback {
    void onSuccess(String terms);
    void onError(String errorMessage);
}
