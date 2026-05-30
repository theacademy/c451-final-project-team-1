package com.jc451.team1.dao;

import com.jc451.team1.dto.User;

public interface UserDao {

    public User createUser(User user);

    public User getUser(int id);

    public User updateUser(int id);
}
