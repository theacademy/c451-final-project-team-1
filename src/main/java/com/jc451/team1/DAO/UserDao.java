package com.jc451.team1.DAO;

import com.jc451.team1.DTO.User;

public interface UserDao {

    public User createUser(User user);

    public User getUser(int id);

    public User updateUser(int id);
}
