package com.example.carstore1;

public class User {
    private int id;
    private String name;
    private String user_name;
    private String password;

    public User(){
        id = -1;
        name = "";
        user_name = "";
        password = "";
    }
    public User(int id, String name, String user_name, String password) {
        this.id = id;
        this.name = name;
        this.user_name = user_name;
        this.password = password;
    }

    public User(String name, String user_name, String password) {
        this.name = name;
        this.user_name = user_name;
        this.password = password;
    }

    public User(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
