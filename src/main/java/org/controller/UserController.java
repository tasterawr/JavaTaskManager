package org.controller;

import org.model.User;
import org.service.UserService;

public class UserController {
    UserService userService = new UserService();

    public User getUserByLoginPass(String login, String password){
        return userService.getUserByLoginPass(login, password);
    }

    public boolean addNewUser(String username, String password, String firstname, String lastname, String phone){
        return userService.addNewUser(username, password, firstname, lastname, phone);
    }

    public boolean deleteUser(String username, String password){
        return userService.deleteUser(username, password);
    }
}
