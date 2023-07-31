package com.example.it3b_partialapps_grp1;

public class User {
    private String username , fullname, password, contact;

    public User(String username, String fullname, String password, String contact) {
        this.username = username;
        this.fullname = fullname;
        this.password = password;
        this.contact = contact;
    }

    public String getUsername() {
        return username;
    }

    public String getFullname() {
        return fullname;
    }

    public String getPassword() {
        return password;
    }

    public String getContact() {
        return contact;
    }
}
