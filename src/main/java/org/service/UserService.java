package org.service;

import org.model.User;
import org.password_utils.PasswordEncryptor;
import org.repository.IRepository;
import org.repository.UserRepository;

import java.sql.SQLException;

public class UserService {
    UserRepository userRepository = new UserRepository();

    public User getUserByLoginPass(String login, String password){
        User user = null;
        String encryptedPassword = PasswordEncryptor.encrypt(password);
        try{
            user = userRepository.getUserByLoginPass(login, encryptedPassword);
            return user;
        }
        catch (ClassNotFoundException | SQLException e){
            return null;
        }
    }

    public boolean addNewUser(String username, String password, String firstname, String lastname, String phone){
        User user = new User();
        user.setUsername(username);
        user.setPassword(PasswordEncryptor.encrypt(password));
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setPhone(phone);

        try{
            User result = userRepository.create(user);
            if (result != null)
                return true;
            else return false;
        }
        catch (ClassNotFoundException | SQLException e){
            return false;
        }

    }

    public boolean deleteUser(String username, String password){
        return false;
    }
}
