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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class PasswordCreator extends AppCompatActivity {

    EditText password_title, password_input, re_password_input;
    Button encrypt, back;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    private PasswordEncryption passwordEncryption;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_creator);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        //Back to home page
        back = findViewById(R.id.back);
        back.setOnClickListener(view -> {
            startActivity(new Intent(PasswordCreator.this, HomePage.class));
        });


        //Create Password
        password_title = findViewById(R.id.password_title);
        password_input = findViewById(R.id.password_input);
        re_password_input = findViewById(R.id.re_password_input);

        encrypt = findViewById(R.id.encrypt);

        encrypt.setOnClickListener(view -> {

            String title = password_title.getText().toString().trim();
            String pass = password_input.getText().toString().trim();
            String re_pass = re_password_input.getText().toString().trim();

            DatabaseReference dataRef = reference.child(userID).child("total");
            dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    int total = dataSnapshot.getValue(Integer.class);

                    if (validate_input(title,pass,re_pass)) {

                        total += 1;

                        try {
                            passwordEncryption = new PasswordEncryption();
                            String key = passwordEncryption.generateKey();
                            String iv = passwordEncryption.generateIV();

                            String encryptedPassword = passwordEncryption.encrypt(pass, key, iv);

                            reference.child(userID).child("data").child(Integer.toString(total)).child("title").setValue(title);
                            reference.child(userID).child("data").child(Integer.toString(total)).child("pass").setValue(encryptedPassword);
                            reference.child(userID).child("data").child(Integer.toString(total)).child("key").setValue(key);
                            reference.child(userID).child("data").child(Integer.toString(total)).child("iv").setValue(iv);

                            reference.child(userID).child("total").setValue(total);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(getApplicationContext(), "Successfully Encrypted!", Toast.LENGTH_SHORT).show();
                    }


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(PasswordCreator.this, "An error has occurred!", Toast.LENGTH_SHORT).show();
                }
            });

        });





    }

    //Input Validation
    public boolean validate_input(String title, String pass, String re_pass) {
        Pattern upperChar = Pattern.compile("[a-z0-9]*");
        Pattern numberChar = Pattern.compile("[a-zA-Z]*");
        Pattern specialChar = Pattern.compile("[a-zA-Z0-9]*");

        if (title.isEmpty()) {
            password_title.setError("Title is required!");
            password_title.requestFocus();
            return false;
        }
        if (pass.isEmpty()) {
            password_input.setError("Password is required!");
            password_input.requestFocus();
            return false;
        }
        if (pass.length() < 6) {
            password_input.setError("Password must be at least 6 characters!");
            password_input.requestFocus();
            return false;
        }
        if ((upperChar.matcher(pass)).matches()) {
            password_input.setError("Password must include an uppercase character!");
            password_input.requestFocus();
            return false;
        }
        if ((numberChar.matcher(pass)).matches()) {
            password_input.setError("Password must include a digit!");
            password_input.requestFocus();
            return false;
        }
        if ((specialChar.matcher(pass)).matches()) {
            password_input.setError("Password must include a special character!");
            password_input.requestFocus();
            return false;
        }
        if (!re_pass.equals(pass)) {
            re_password_input.setError("Password must match!");
            re_password_input.requestFocus();
            return false;
        } else {
            return true;
        }
    }
}