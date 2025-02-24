package com.example.sendy.validator;

import android.text.TextWatcher;
import android.widget.EditText;

public class PhoneNumberValidator {
    private final static String PHONE_PATTERN = "^\\+7 \\(\\d{3}\\) \\d{3}-\\d{2}-\\d{2}$";

    /**
     * Проверка корректности номера телефона.
     *
     * @param phone номер телефона для проверки
     * @return true, если номер телефона корректный иначе false.
     */
    public boolean isValid(String phone){
        if (phone == null) return  false;

        return  phone.matches(PHONE_PATTERN);
    }

    /**
     * Форматирует номер телефона
     *
     * @param phone номер телефона
     *
     */
    public void formatPhone(String phone, EditText editText){

        String digitsOnly = phone.replaceAll("[^\\d]","");

        if(digitsOnly.charAt(0) == '7'){
            digitsOnly  = digitsOnly.substring(1);
        }

        StringBuilder formatted = new StringBuilder();

        for (int i = 0; i < digitsOnly.length(); i++) {
            char symbol = digitsOnly.charAt(i);

            switch (i){
                case 0:
                    formatted.append("+7 (");
                    break;
                case 3:
                    formatted.append(") ");
                    break;
                case 6:
                case 8:
                    formatted.append("-");
                    break;
            }

            formatted.append(symbol);
        }

        editText.setText(formatted.toString());
        editText.setSelection(formatted.length());
    }
}
