package org.controller;

import org.exceptions.DomainException;
import org.exceptions.InterfaceException;
import org.model.User;
import org.service.UserService;

public class UserController {
    UserService userService = new UserService();

    public User getUserByLoginPass(String login, String password){
        try {
            return userService.getUserByLoginPass(login, password);
        }
        catch(DomainException e){
            throw new InterfaceException(e.getMessage(), e);
        }
    }

    public void addNewUser(String username, String password, String firstname, String lastname, String phone){
        try{
            userService.addNewUser(username, password, firstname, lastname, phone);
        }
        catch(DomainException e){
            throw new InterfaceException(e.getMessage(), e);
        }

    }

    public void deleteUser(String username, String password){
        try{
            userService.deleteUser(username, password);
        }
        catch (DomainException e){
            throw new InterfaceException(e.getMessage(), e);
        }
    }
}
