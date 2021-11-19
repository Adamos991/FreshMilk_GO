package com.example.freshmilk_go;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private TextView register1, registerbutt, backbtn;
    private EditText fname, password, confirmpassword, email;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        registerbutt = (Button) findViewById(R.id.registerbutt);
        registerbutt.setOnClickListener(this);

        backbtn = (TextView) findViewById(R.id.backbtn);
        backbtn.setOnClickListener(this);

        fname = (EditText) findViewById(R.id.fname);
        password = (EditText) findViewById(R.id.password);
        confirmpassword = (EditText) findViewById(R.id.confirmpassword);
        email = (EditText) findViewById(R.id.email);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.backbtn:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registerbutt:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String emailV = email.getText().toString().trim();
        String passV = password.getText().toString().trim();
        String confPassV = confirmpassword.getText().toString().trim();
        String usernameV = fname.getText().toString().trim();

        if(usernameV.isEmpty()) {
            fname.setError("Username is needed!");
            fname.requestFocus();
            return;
        }

        if(emailV.isEmpty()) {
            email.setError("Email is needed!");
            email.requestFocus();
            return;
        }

        if(passV.isEmpty()) {
            password.setError("Password is needed!");
            password.requestFocus();
            return;
        }

        if(confPassV.equals(passV)) {
            confirmpassword.setError("Must match password!");
            confirmpassword.requestFocus();
            return;
        }
    }
}