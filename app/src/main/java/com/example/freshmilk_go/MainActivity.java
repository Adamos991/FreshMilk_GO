package com.example.freshmilk_go;


import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.*;

import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView username = (TextView) findViewById(R.id.username);
        TextView password = (TextView) findViewById(R.id.password);

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);


        Button loginbtn = (Button) findViewById(R.id.loginbtn);

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
                    System.out.println("Login Successful");
                }
            }
        });
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.register:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
    }
}