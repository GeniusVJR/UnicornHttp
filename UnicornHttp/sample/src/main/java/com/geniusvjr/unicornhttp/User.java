package com.geniusvjr.unicornhttp;

/**
 * Created by dream on 16/6/3.
 */
public class User {

    public String id;
    public String account;
    public String email;
    public String username;
    public String token;

    @Override
    public String toString() {
        return username + " : " + email;
    }
}
