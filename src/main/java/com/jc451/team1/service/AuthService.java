package com.jc451.team1.service;

import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public boolean login(String username, String password) {

        return username.equals("admin")
                && password.equals("password");
    }
}
