package com.example.sendy.view;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sendy.MainActivity;
import com.example.sendy.R;
import com.example.sendy.service.TermsCallback;
import com.example.sendy.service.WalletsService;

public class AgreementScreen extends AppCompatActivity {

    private WalletsService wallets = new WalletsService();
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.agreement_screen);

            runAgreementScreen();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }

    private void runAgreementScreen(){
        webView = findViewById(R.id.webView);

        wallets.getTermsOfUser(AgreementScreen.this, new TermsCallback() {
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

    @Override
    public void onBackPressed(){
        if(webView.canGoBack()){
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
