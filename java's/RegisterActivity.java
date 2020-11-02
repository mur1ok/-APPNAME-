package com.example.testapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText username, email, password, repeatPassword;
    Button register, cancel;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        repeatPassword = findViewById(R.id.repeatPassword);
        register = findViewById(R.id.register);
        cancel = findViewById(R.id.cancel);

        dbHelper = new DBHelper(this);

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usernameValue = username.getText().toString();
                String emailValue = email.getText().toString();
                String passwordValue = password.getText().toString();
                String repeatPasswordValue = repeatPassword.getText().toString();

                if (!emailValue.contains("@")) {
                    Toast.makeText(RegisterActivity.this, "Your email is not correct", Toast.LENGTH_SHORT).show();
                    email.setText(null);
                    password.setText(null);
                    repeatPassword.setText(null);
                    return;
                }

                if (!dbHelper.isLoginUnique(emailValue)) {
                    Toast.makeText(RegisterActivity.this, "This e-mail already exists", Toast.LENGTH_SHORT).show();
                    email.setText(null);
                    password.setText(null);
                    repeatPassword.setText(null);
                    return;
                }

                if (!passwordValue.equals(repeatPasswordValue)) {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    password.setText(null);
                    repeatPassword.setText(null);
                    return;
                }

                ContentValues contentValues = new ContentValues();
                contentValues.put("username", usernameValue);
                contentValues.put("email", emailValue);
                contentValues.put("password", passwordValue);
                contentValues.put("strength", 1.0); //TODO: подхватить силу ума из тестирования или поставить средний
                dbHelper.insertUser(contentValues);

                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}