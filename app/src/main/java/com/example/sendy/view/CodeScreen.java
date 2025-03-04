package com.example.sendy.view;

import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sendy.MainActivity;
import com.example.sendy.R;
import com.example.sendy.service.MessageCallback;
import com.example.sendy.service.WalletsService;
import com.example.sendy.validator.CodeValidator;

public class CodeScreen extends AppCompatActivity {
    private WalletsService wallets = new WalletsService();
    private CodeValidator codeValidator = new CodeValidator();

    @Override
    protected void onCreate(Bundle savedInstanceState){
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.agreement_screen);

            runCodeScreen();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }

    private void runCodeScreen(){
        new Handler().postDelayed(() -> {
            setContentView(R.layout.code_screen);

            Button checkCode = findViewById(R.id.checkButton);
            EditText code = findViewById(R.id.inputCode);

            checkCode.setOnClickListener(view -> {
                wallets.checkCode(code.getText().toString(), CodeScreen.this, new MessageCallback(){
                    @Override
                    public void onSuccess(boolean isSend) {
                        if(isSend){
                            Toast.makeText(CodeScreen.this, "Успех :)", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(CodeScreen.this, "Что-то пошло не так :(", Toast.LENGTH_LONG).show();
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
                        checkCode.setBackgroundResource(R.color.buttonIsActive);
                    } else {
                        checkCode.setEnabled(false);
                        checkCode.setBackgroundResource(R.color.buttonIsNoActive);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        },1000);
    }
}
