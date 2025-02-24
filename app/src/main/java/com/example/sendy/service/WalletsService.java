package com.example.sendy.service;

import static land.sendy.pfe_sdk.api.API.api;

import com.example.sendy.MainActivity;
import com.example.sendy.exception.NoResponseData;
import com.example.sendy.exception.NoSendRequestException;
import com.example.sendy.exception.ResponseReturnErrorCode;
import com.example.sendy.exception.ShowErrorUser;

import java.security.NoSuchAlgorithmException;

import land.sendy.pfe_sdk.model.pfe.response.AuthActivateRs;
import land.sendy.pfe_sdk.model.pfe.response.AuthLoginRs;
import land.sendy.pfe_sdk.model.pfe.response.BResponse;
import land.sendy.pfe_sdk.model.pfe.response.TermsOfUseRs;
import land.sendy.pfe_sdk.model.types.ApiCallback;
import land.sendy.pfe_sdk.model.types.LoaderError;

public class WalletsService {

    public void getTermsOfUser(MainActivity activity, TermsCallback callback){
        if(callback == null)
            return;

        LoaderError runTermsOfUser = api.getTermsOfUse(activity,new ApiCallback(){
            @Override
            public void onCompleted(boolean res) {
                if (!res || getErrNo() != 0) {
                    ShowErrorUser.showErrorToUser("Сервер вернул ответ с ошибкой",activity);
                    callback.onError("Сервер вернул ответ с ошибкой: " + this.toString());
                }
                else {
                    String agreement = ((TermsOfUseRs)this.oResponse).TextTermsOfUse;
                    callback.onSuccess(agreement);
                }
            }
        });

        if (runTermsOfUser != null && runTermsOfUser.hasError()){
            callback.onError("Ошибка");
        }
    }
    public void sendMessage(String phoneNumber, MainActivity activity, MessageCallback callback) {
        String formatedNumber = phoneNumber.replaceAll("[^\\d]", "");

        LoaderError runLogin = api.loginAtAuth(activity, formatedNumber, new ApiCallback() {
            @Override
            public void onCompleted(boolean res) {
                if (!res || getErrNo() != 0) {
                    callback.onSuccess(false);
                    ShowErrorUser.showErrorToUser("Сервер вернул ответ с ошибкой: " + this.toString() ,activity);
                } else {
                    callback.onSuccess(true);
                }
            }
        });

        if (runLogin != null && runLogin.hasError()) {
            try {
                throw new NoSendRequestException("Запрос не был выполнен");
            } catch (NoSendRequestException ex){
                ShowErrorUser.showErrorToUser(ex.getMessage(),activity);
                callback.onSuccess(false);
            }
        }
    }

    public void checkCode(String token, MainActivity activity, MessageCallback callback) {
        try {
            LoaderError runSendCode = api.activateWllet(activity, token, "sms", new ApiCallback() {
                @Override
                public void onSuccess(BResponse data) {
                    try{
                        if (data != null) {
                            if (this.getErrNo() == 0) {
                                AuthActivateRs response = (AuthActivateRs) this.oResponse;
                                callback.onSuccess(true);
                                System.out.println(response.toString());
                            } else {
                                throw new ResponseReturnErrorCode("Сервер вернул ответ с ошибкой: " + this.toString());
                            }
                        } else {
                            throw new NoResponseData("Сервер не вернул данные");
                        }
                    } catch (RuntimeException ex){
                        ShowErrorUser.showErrorToUser("Ошибка: " + ex.getMessage(),activity);
                        System.out.println("Ошибка: " + ex.getMessage());
                    }
                }

                @Override
                public void onFail(LoaderError error) {
                    throw new RuntimeException("Фатальная ошибка: " + error.toString());
                }
            });

            if (runSendCode != null && runSendCode.hasError()) {
                throw new NoSendRequestException("Запрос не был выполнен");
            }
        } catch (NoSuchAlgorithmException ex) {
            ShowErrorUser.showErrorToUser("Ошибка: " + ex.getMessage(),activity);
            System.out.println("Ошибка: " + ex.getMessage());
        }
    }
}
