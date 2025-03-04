package com.example.sendy.exception;

import android.content.Context;
import android.widget.Toast;

import com.example.sendy.MainActivity;

public class ShowErrorUser {
    public static void showErrorToUser(String message, Context activity){
        Toast.makeText(activity,message,Toast.LENGTH_LONG).show();
    }
}
