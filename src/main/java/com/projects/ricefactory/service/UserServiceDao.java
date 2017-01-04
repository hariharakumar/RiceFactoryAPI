package com.projects.ricefactory.service;

import com.projects.ricefactory.dto.User;

import java.util.List;

/**
 * Created by hearlapati on 12/5/16.
 */
public interface UserServiceDao {

    public User createUser(User user) throws Exception;

    public User updateUser(User existingUser, User updatedUser) throws Exception;

    public User getUserById(Long id);

    public List<User> getAllUsers();

    public void deleteUser(Long id);

    public User getUserByEmail(String email);
}
