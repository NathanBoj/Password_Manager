package com.example.password_manager;

public class item {

    private String title, password, key, iv;

    //Password Item has following features
    public item(String title, String password, String key, String iv){
        this.title = title;
        this.password = password;
        this.key = key;
        this.iv = iv;
    }

    public String get_title(){
        return title;
    }

    public String get_password(){
        return password;
    }

    public String get_key(){
        return key;
    }

    public String get_iv(){
        return iv;
    }

}
