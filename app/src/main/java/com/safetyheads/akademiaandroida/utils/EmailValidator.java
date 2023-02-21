package com.safetyheads.akademiaandroida.utils;

import android.util.Patterns;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class EmailValidator extends AppCompatActivity {

    public void emailValidator(EditText etMail) {
        String emailToText = etMail.getText().toString();
        if (!emailToText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()) {
            Toast.makeText(this, "Email Verified !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Enter valid Email address !", Toast.LENGTH_SHORT).show();
        }
    }

}
//To use this code you have to use in xml file Button (android:id="@+id/validateButton") and EditText (android:id="@+id/emailField")
