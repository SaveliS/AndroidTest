package com.example.sendy.validator;

public class CodeValidator {

    public boolean isValid(String code) {
        if (code.length() != 6){
            return false;
        }

        for (int i = 0; i < code.length(); i++) {
            if(!Character.isDigit(code.charAt(i))){
               return false;
            }
        }

        return true;
    }
}
