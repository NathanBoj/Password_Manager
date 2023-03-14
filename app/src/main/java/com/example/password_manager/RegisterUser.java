package com.example.password_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterUser extends AppCompatActivity {

    private EditText name_reg, email_reg, password_reg, rePassword;
    private TextView login;

    private FirebaseAuth mAuth;
    public FirebaseAuth auth = FirebaseAuth.getInstance();
    public FirebaseUser userVerify = auth.getCurrentUser();

    @SuppressLint("MissingInflatedId")

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();

        //Initialize views
        Button but_button_reg = findViewById(R.id.registerUser);
        name_reg = findViewById(R.id.fullName);
        email_reg = findViewById(R.id.emailRegister);
        password_reg = findViewById(R.id.passwordRegister);
        rePassword = findViewById(R.id.rePassword);
        login = findViewById(R.id.loginLink);

        //Send user to login page
        login.setOnClickListener(view -> {
            startActivity(new Intent(RegisterUser.this, MainActivity.class));
        });

        //Register the user
        but_button_reg.setOnClickListener(view -> registerUser());
    }


    public void registerUser() {
        Pattern upperChar = Pattern.compile("[a-z0-9]*");
        Pattern numberChar = Pattern.compile("[a-zA-Z]*");
        Pattern specialChar = Pattern.compile("[a-zA-Z0-9]*");

        String name = name_reg.getText().toString().trim();
        String email = email_reg.getText().toString().trim();
        String password = password_reg.getText().toString().trim();
        String Repassword = rePassword.getText().toString().trim();

        //Input Validation
        if (name.isEmpty()) {
            name_reg.setError("Name is required!");
            name_reg.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            email_reg.setError("Email is required!");
            email_reg.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            password_reg.setError("Password is required!");
            password_reg.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_reg.setError("Provide valid Email!");
            email_reg.requestFocus();
            return;
        }
        if (password.length() < 6) {
            password_reg.setError("Password must be at least 6 characters!");
            password_reg.requestFocus();
            return;
        }
        if ((upperChar.matcher(password)).matches()) {
            password_reg.setError("Password must include an uppercase character!");
            password_reg.requestFocus();
            return;
        }
        if ((numberChar.matcher(password)).matches()) {
            password_reg.setError("Password must include a digit!");
            password_reg.requestFocus();
            return;
        }
        if ((specialChar.matcher(password)).matches()) {
            password_reg.setError("Password must include a special character!");
            password_reg.requestFocus();
            return;
        }
        if (!Repassword.equals(password)) {
            rePassword.setError("Password must match!");
            rePassword.requestFocus();
            return;
        }

        //Create user in FireBase
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterUser.this, task -> {
                    if (task.isSuccessful()) {

                        //Add user attributes to realtime database
                        User user = new User(name, email);

                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user);

                        Toast.makeText(RegisterUser.this, "Registration Success!", Toast.LENGTH_SHORT).show();

                        //Verify email
                        verifyuser();


                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(RegisterUser.this, "Error in Registration!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    //Verify email
    public void verifyuser() {
        //Verify email
        userVerify.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task2) {
                        if (task2.isSuccessful()) {
                            //Send user back to login page
                            startActivity(new Intent(RegisterUser.this, MainActivity.class));
                        } else {
                            Toast.makeText(RegisterUser.this, "verify failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


    }





}