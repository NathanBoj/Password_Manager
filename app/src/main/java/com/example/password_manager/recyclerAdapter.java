package com.example.password_manager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.MyViewHolder> {

    private ArrayList<item> itemsList;

    public recyclerAdapter(ArrayList<item> itemsList){
        this.itemsList = itemsList;
    }

    //For each item, display the following elements
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView titleTxt;
        private TextView passTxt;
        ImageButton lock_button, unlock_button;

        public MyViewHolder(final View view){
            super(view);
            titleTxt = view.findViewById(R.id.textview_password_title);
            passTxt = view.findViewById(R.id.textview_password);

            lock_button = view.findViewById(R.id.lock_button);
            unlock_button = view.findViewById(R.id.unlock_button);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        //Get position of specified item
        String title = itemsList.get(position).get_title();
        holder.titleTxt.setText(title);

        String pass = itemsList.get(position).get_password();
        String truncatedPassword = pass;
        if (pass.length() >= 12){
            truncatedPassword = pass.substring(0, 12) + "...";
        }
        holder.passTxt.setText(truncatedPassword);

        String key = itemsList.get(position).get_key();
        String iv = itemsList.get(position).get_iv();

        //Password Object
        PasswordEncryption passwordEncryption = new PasswordEncryption();

        //Show password (decrypt)
        holder.lock_button.setOnClickListener(view -> {
            holder.lock_button.setVisibility(View.GONE);
            holder.unlock_button.setVisibility(View.VISIBLE);

            try {
                String decryptedPassword = passwordEncryption.decrypt(pass, key, iv);
                holder.passTxt.setText(decryptedPassword);
                Toast.makeText(holder.itemView.getContext(), "Successfully decrypted password", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(holder.itemView.getContext(), "Failed to decrypt password", Toast.LENGTH_SHORT).show();
            }

        });

        //Hide password (encrypt)
        String finalTruncatedPassword = truncatedPassword;
        holder.unlock_button.setOnClickListener(view -> {
            holder.unlock_button.setVisibility(View.GONE);
            holder.lock_button.setVisibility(View.VISIBLE);
            holder.passTxt.setText(finalTruncatedPassword);
            Toast.makeText(holder.itemView.getContext(), "Successfully encrypted password", Toast.LENGTH_SHORT).show();
        });


    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
}
