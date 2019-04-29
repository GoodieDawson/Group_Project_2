package com.example.group_project_2;

import java.util.Random;

public class User {

    private String username;
    private String password;
    private String type;
    private int balance;

    public User (String username, String password, String type) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.balance = 0;

    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
