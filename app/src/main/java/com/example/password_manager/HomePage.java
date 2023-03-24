package com.example.password_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class HomePage extends AppCompatActivity {

    private TextView Greeting, Greeting2;

    Button logout, add_password;

    private ArrayList<item> itemsList;
    private RecyclerView recyclerView;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //Initialize List View
        recyclerView = findViewById(R.id.recyclerView);
        itemsList = new ArrayList<>();

        Greeting = findViewById(R.id.greeting);
        Greeting2 = findViewById(R.id.greeting2);
        add_password = findViewById(R.id.add_password);

        //Logout button
        logout = findViewById(R.id.logout);
        logout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(HomePage.this, MainActivity.class));
        });


        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        //Personal Greeting
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    String fullName = userProfile.Name;

                    //Friendly greeting
                    SimpleDateFormat sdf = new SimpleDateFormat("HH");
                    String currentTimeString = sdf.format(new Date());
                    int currentTime = Integer.parseInt(currentTimeString);
                    if (currentTime < 12){
                        Greeting.setText("Good Morning,");
                    } else {
                        Greeting.setText("Good Afternoon,");
                    }
                    Greeting2.setText(fullName);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomePage.this, "An error has occurred!", Toast.LENGTH_SHORT).show();
            }
        });

        //Display Items
        // Get a reference to the "data" node
        DatabaseReference dataRef = reference.child(userID).child("data");
        //Count the number of children
        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int numChildren = (int) dataSnapshot.getChildrenCount();

                if (numChildren != 0) {
                    //For every stored password item
                    for (int i = 1; i <= numChildren; i++) {
                        // Get the title and text values from the current child node
                        String title = dataSnapshot.child(String.valueOf(i)).child("title").getValue(String.class);
                        String pass = dataSnapshot.child(String.valueOf(i)).child("pass").getValue(String.class);
                        String key = dataSnapshot.child(String.valueOf(i)).child("key").getValue(String.class);
                        String iv = dataSnapshot.child(String.valueOf(i)).child("iv").getValue(String.class);

                        // Add the title and text values to the list
                        itemsList.add(new item(title, pass, key, iv));
                    }
                }
                setAdapter();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomePage.this, "An error has occurred!", Toast.LENGTH_SHORT).show();
            }
        });

        //Add items
        add_password.setOnClickListener(view -> {
            startActivity(new Intent(HomePage.this,PasswordCreator.class));
        });

    }

    //Set the item display
    private void setAdapter() {
        recyclerAdapter adapter = new recyclerAdapter(itemsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
}