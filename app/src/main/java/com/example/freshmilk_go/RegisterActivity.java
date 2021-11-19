package com.example.freshmilk_go;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

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

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
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
        System.out.println("Button pressed");
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

        if(!Patterns.EMAIL_ADDRESS.matcher(emailV).matches()) {
            email.setError("That's not a real email you silly goose!");
            email.requestFocus();
            return;
        }

        if(passV.isEmpty()) {
            password.setError("Password is needed!");
            password.requestFocus();
            return;
        }

        if(passV.length()<6){
            password.setError("Password must be over 6 characters long");
            password.requestFocus();
            return;
        }

        if(confPassV.equals(passV)) {
            //do nothing
        }
             else{
            confirmpassword.setError("Must match password!");
            confirmpassword.requestFocus();
            return;
        }
            progressBar.setVisibility(View.VISIBLE);
             mAuth.createUserWithEmailAndPassword(emailV, passV)
                     .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {

                             if (task.isSuccessful()){
                                 User user = new User(usernameV, passV);
                                 FirebaseDatabase.getInstance().getReference("Users")
                                         .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                         .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                     @Override
                                     public void onComplete(@NonNull Task<Void> task) {
                                         if (task.isSuccessful()){
                                             Toast.makeText(RegisterActivity.this, "New user registered!", Toast.LENGTH_LONG).show();
                                             progressBar.setVisibility(View.GONE);
                                         }
                                         else{
                                             Toast.makeText(RegisterActivity.this, "You have failed, try again.", Toast.LENGTH_LONG).show();
                                             progressBar.setVisibility(View.GONE);
                                         }
                                     }
                                 });

                             }
                             else{
                                 Toast.makeText(RegisterActivity.this, "Failed to Register", Toast.LENGTH_LONG).show();
                                 progressBar.setVisibility(View.VISIBLE);
                             }
                         }
                     });
        }



    }