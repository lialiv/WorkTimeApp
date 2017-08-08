package com.example.amirl2.myapplication.Accessories;

/**
 * Created by AmirL2 on 8/8/2017.
 */

public class UserObj

{
    public int id;
    public String name;
    public String username;
    public String password;

    public UserObj(int id, String name, String username, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public UserObj(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public UserObj() {
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

}



