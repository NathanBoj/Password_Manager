package com.example.password_manager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private TextView register, forgotPass;
    private EditText email_log, password_log;
    private Button Login, debug;

    private FirebaseAuth mAuth;
    public FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize views
        register = findViewById(R.id.registerLink);
        email_log = findViewById(R.id.emailLogin);
        password_log = findViewById(R.id.passwordLogin);
        Login = findViewById(R.id.loginUser);
        forgotPass = findViewById(R.id.forgotPass);

        //Debug Feature
        debug = findViewById(R.id.debug);
        debug.setOnClickListener(view -> {
            mAuth.signInWithEmailAndPassword("bojczuk.nathan@gmail.com","Abcdefg123!").addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    FirebaseUser userAuthorized = auth.getCurrentUser();
                    //If user verified their email
                    assert userAuthorized != null;
                    if (userAuthorized.isEmailVerified()){
                        startActivity(new Intent(MainActivity.this,SMS.class));
                    } else {
                        Toast.makeText(MainActivity.this, "Please verify your email!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Login Failed! Please try again.", Toast.LENGTH_SHORT).show();
                }
            });
        });

        mAuth = FirebaseAuth.getInstance();

        //Send user to register page
        register.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, RegisterUser.class));
        });

        //Send user to forgot password page
        forgotPass.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, ForgotPassword.class));
        });

        //Log user in
        Login.setOnClickListener(view1 -> {
            userLogin();
        });

    }


    //User Login
    private void userLogin() {
        String email = email_log.getText().toString().trim();
        String password = password_log.getText().toString().trim();

        //Input Validation
        if (email.isEmpty()){
            email_log.setError("Email is required!");
            email_log.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            email_log.setError("Enter a valid Email!");
            email_log.requestFocus();
            return;
        }
        if (password.isEmpty()){
            password_log.setError("Password is required!");
            password_log.requestFocus();
            return;
        }
        if (password.length() < 6){
            password_log.setError("Password must be at least 6 characters!");
            password_log.requestFocus();
            return;
        }

        //Retrieve user from FireBase
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                //If user verified their email
                FirebaseUser userAuthorized = auth.getCurrentUser();
                if (userAuthorized.isEmailVerified()){
                    startActivity(new Intent(MainActivity.this,SMS.class));
                } else {
                    Toast.makeText(MainActivity.this, "Please verify your email!", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(MainActivity.this, "Login Failed! Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}