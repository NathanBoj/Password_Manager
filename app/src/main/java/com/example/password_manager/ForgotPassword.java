package com.example.password_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Pattern;

public class ForgotPassword extends AppCompatActivity {

    private EditText emailReset;
    private Button resetUser;

    private FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailReset = findViewById(R.id.emailReset);
        resetUser = findViewById(R.id.resetUser);

        mAuth = FirebaseAuth.getInstance();

        //Reset Password
        resetUser.setOnClickListener(view1 -> {
            resetPass();
        });

    }

    private void resetPass(){

        String email = emailReset.getText().toString().trim();

        //Input Validation
        if (email.isEmpty()) {
            emailReset.setError("Email is required!");
            emailReset.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailReset.setError("Enter a valid Email!");
            emailReset.requestFocus();
            return;
        }

        //Send Password reset with email
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(ForgotPassword.this, "Password reset sent to your email", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(ForgotPassword.this, MainActivity.class));
                        } else {
                            Toast.makeText(ForgotPassword.this, "Error in email", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}