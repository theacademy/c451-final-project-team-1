package com.jc451.team1.dao;

import com.jc451.team1.dto.User;

public interface UserDao {

    User createUser(User user);

    User getUser(int id);

    User updateUser(int id);
}
