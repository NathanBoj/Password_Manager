package com.example.password_manager;

public class User {

    public String Name, email, phone;
    public int total;

    public User(){

    }

    public User(String Name, String email, String phone, int total){
        this.Name = Name;
        this.email = email;
        this.phone = phone;
        this.total = total;
    }

}
