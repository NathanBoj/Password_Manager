package com.example.password_manager;

public class User {

    //User Default Attributes
    public String Name, email, phone;
    //Total Passwords user has stored
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
