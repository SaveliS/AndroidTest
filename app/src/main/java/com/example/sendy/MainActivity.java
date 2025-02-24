package com.example.sendy;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sendy.service.MessageCallback;
import com.example.sendy.service.TermsCallback;
import com.example.sendy.service.WalletsService;
import com.example.sendy.validator.CodeValidator;
import com.example.sendy.validator.PhoneNumberValidator;

import land.sendy.pfe_sdk.activies.MasterActivity;
import land.sendy.pfe_sdk.api.API;

public class MainActivity extends MasterActivity {

    private final String SERVER_URL = "https://testwallet.sendy.land/";
    private final CodeValidator codeValidator = new CodeValidator();

    private final WalletsService wallets = new WalletsService();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.splash_screen);

            api = API.getInsatce(SERVER_URL);
            lp = Looper.getMainLooper();

            runMainScreen();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }

    private void runMainScreen(){
        PhoneNumberValidator validator = new PhoneNumberValidator();

        new Handler().postDelayed(() -> {
            setContentView(R.layout.main_screen);

            Button submitButton = findViewById(R.id.submitButton);
            EditText phoneNumberInput = findViewById(R.id.phoneNumber);
            CheckBox checkAgreement = findViewById(R.id.agreementCheckBox);
            TextView agreementLink = findViewById(R.id.agreementLink);

            agreementLink.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    runAgreementScreen();
                }
            });

            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    wallets.sendMessage(phoneNumberInput.getText().toString(), MainActivity.this, new MessageCallback() {
                        @Override
                        public void onSuccess(boolean isSend) {
                            if(isSend){
                                runCodeScreen();
                            }
                        }
                    });
                }
            });

            phoneNumberInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    phoneNumberInput.removeTextChangedListener(this);

                    try {
                        validator.formatPhone(s.toString(), phoneNumberInput);
                        if(validator.isValid(s.toString()) && checkAgreement.isChecked()){
                            submitButton.setEnabled(true);
                        } else {
                            submitButton.setEnabled(false);
                        }
                    } finally {
                        phoneNumberInput.addTextChangedListener(this);
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }, 3000);
    }

    private void runCodeScreen(){
        new Handler().postDelayed(() -> {
            setContentView(R.layout.code_screen);

            Button checkCode = findViewById(R.id.checkButton);
            EditText code = findViewById(R.id.inputCode);

            checkCode.setOnClickListener(view -> {
                wallets.checkCode(code.getText().toString(), MainActivity.this, new MessageCallback(){

                    @Override
                    public void onSuccess(boolean isSend) {
                        if(isSend){
                            Toast.makeText(MainActivity.this, "Успех :)", Toast.LENGTH_LONG);
                        } else {
                            Toast.makeText(MainActivity.this, "Что-то пошло не так :(", Toast.LENGTH_LONG);
                        }
                    }
                });
            });

            code.addTextChangedListener(new TextWatcher(){

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if(codeValidator.isValid(s.toString())){
                        checkCode.setEnabled(true);
                    } else {
                        checkCode.setEnabled(false);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        },3000);
    }

    private void runAgreementScreen(){
        setContentView(R.layout.agreement_screen);

        WebView webView = findViewById(R.id.webView);

        wallets.getTermsOfUser(MainActivity.this, new TermsCallback() {
            @Override
            public void onSuccess(String terms) {
                webView.loadData(terms,"text/html","UTF-8");
            }

            @Override
            public void onError(String errorMessage) {
                return;
            }
        });
    }
}


