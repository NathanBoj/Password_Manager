package com.example.password_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.MultiFactorAssertion;
import com.google.firebase.auth.MultiFactorSession;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.PhoneMultiFactorGenerator;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class SMS extends AppCompatActivity {
    private PhoneAuthCredential credential;
    private String verificationId;
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;

    Button enter;
    EditText putcodehere;

    public FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public FirebaseAuth auth = FirebaseAuth.getInstance();
    public FirebaseUser userPhone = auth.getCurrentUser();

    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String userID = user.getUid();
    String phone_Number = "5454";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot snapshot) {
               User userProfile = snapshot.getValue(User.class);

               if (userProfile != null) {
                   String fullName = userProfile.Name;
                   String Number = userProfile.phone;

                   phone_Number = "+1".concat(Number);

                   send_Code();


               }
           }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        enter = findViewById(R.id.enter);

        putcodehere = findViewById(R.id.putcodehere);

//        //MultiFactorAssertion multiFactorAssertion = PhoneMultiFactorGenerator.getAssertion(credential);
//        userPhone.getMultiFactor().getSession()
//                .addOnCompleteListener(
//                        new OnCompleteListener<MultiFactorSession>() {
//                            @Override
//                            public void onComplete(@NonNull Task<MultiFactorSession> task) {
//                                if (task.isSuccessful()) {
//                                    MultiFactorSession multiFactorSession = task.getResult();
//                                    PhoneAuthOptions options =
//                                            PhoneAuthOptions.newBuilder(mAuth)
//                                                    .setPhoneNumber(phone_Number)       // Phone number to verify
//                                                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
//                                                    .setActivity(SMS.this)                 // Activity (for callback binding)
//                                                    .setCallbacks(callbacks)           // OnVerificationStateChangedCallbacks
//                                                    .build();
//                                    // Send SMS verification code.
//                                    PhoneAuthProvider.verifyPhoneNumber(options);
//                                }
//                            }
//                        });

//        send_code.setOnClickListener(view -> {

            //MultiFactorAssertion multiFactorAssertion = PhoneMultiFactorGenerator.getAssertion(credential);

//
//        });

        enter.setOnClickListener(view -> {
            String verificationCode = putcodehere.getText().toString().trim();
            // Ask user for the verification code.
            PhoneAuthCredential credential =
                    PhoneAuthProvider.getCredential(verificationId, verificationCode);

            MultiFactorAssertion multiFactorAssertion = PhoneMultiFactorGenerator.getAssertion(credential);
            // Complete enrollment
            FirebaseAuth.getInstance()
                    .getCurrentUser()
                    .getMultiFactor()
                    .enroll(multiFactorAssertion, "My personal phone number")
                    .addOnCompleteListener(
                            new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(SMS.this, "SMS Code Verified", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(SMS.this, HomePage.class));
                                }
                            });

        });



    }

    public void send_Code() {

        userPhone.getMultiFactor().getSession()
                .addOnCompleteListener(
                        new OnCompleteListener<MultiFactorSession>() {
                            @Override
                            public void onComplete(@NonNull Task<MultiFactorSession> task) {
                                if (task.isSuccessful()) {
                                    MultiFactorSession multiFactorSession = task.getResult();
                                }
                            }
                        });

        PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1) Instant verification. In some cases, the phone number can be
                //    instantly verified without needing to send or enter a verification
                //    code. You can disable this feature by calling
                //    PhoneAuthOptions.builder#requireSmsValidation(true) when building
                //    the options to pass to PhoneAuthProvider#verifyPhoneNumber().
                // 2) Auto-retrieval. On some devices, Google Play services can
                //    automatically detect the incoming verification SMS and perform
                //    verification without user action.
                SMS.this.credential = credential;
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in response to invalid requests for
                // verification, like an incorrect phone number.
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // ...
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // ...
                }
                // Show a message and update the UI
                // ...
            }

            @Override
            public void onCodeSent(
                    String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number.
                // We now need to ask the user to enter the code and then construct a
                // credential by combining the code with a verification ID.
                // Save the verification ID and resending token for later use.
                SMS.this.verificationId = verificationId;
                SMS.this.forceResendingToken = token;
                // ...
            }
        };

        userPhone.getMultiFactor().getSession()
                .addOnCompleteListener(
                        new OnCompleteListener<MultiFactorSession>() {
                            @Override
                            public void onComplete(@NonNull Task<MultiFactorSession> task) {
                                if (task.isSuccessful()) {
                                    MultiFactorSession multiFactorSession = task.getResult();
                                    PhoneAuthOptions options =
                                            PhoneAuthOptions.newBuilder(mAuth)
                                                    .setPhoneNumber(phone_Number)       // Phone number to verify
                                                    .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                                                    .setActivity(SMS.this)                 // Activity (for callback binding)
                                                    .setCallbacks(callbacks)           // OnVerificationStateChangedCallbacks
                                                    .build();
                                    // Send SMS verification code.
                                    PhoneAuthProvider.verifyPhoneNumber(options);
                                }
                            }
                        });
    }

}