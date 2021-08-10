package org.service;

import org.model.User;
import org.utils.CurrentUser;
import org.utils.MessageGenerator;
import org.utils.PasswordEncryptor;
import org.repository.UserRepository;

import java.sql.SQLException;

public class UserService {
    UserRepository userRepository = new UserRepository();

    public User getUserByLoginPass(String login, String password){
        User user = null;
        String encryptedPassword = PasswordEncryptor.encrypt(password);
        try{
            user = userRepository.getUserByLoginPass(login, encryptedPassword);
            if (user != null){
                MessageGenerator.setMessage("Logged in successfully!");
                CurrentUser.setUser(user);
            }

            else
                MessageGenerator.setMessage("Wrong login or password.");
            return user;
        }
        catch (ClassNotFoundException | SQLException e){
            MessageGenerator.setMessage("Error: Log in unsuccessful.");
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
            if (result != null){
                MessageGenerator.setMessage("New user was registered successfully.");
                CurrentUser.setUser(result);
                return true;
            }
            else{
                MessageGenerator.setMessage("Error: Username already exists.");
                return false;
            }
        }
        catch (ClassNotFoundException | SQLException e){
            MessageGenerator.setMessage("Error: New user was not registered.");
            return false;
        }

    }

    public boolean deleteUser(String username, String password){
        try{
            User user = userRepository.getUserByLoginPass(username, PasswordEncryptor.encrypt(password));
            if (user == null){
                MessageGenerator.setMessage("Error: User not found.");
                return false;
            }

            userRepository.delete(user.getId());
            //also delete tasks for user
            MessageGenerator.setMessage("User was deleted successfully.");

        }
        catch (ClassNotFoundException | SQLException e){
            MessageGenerator.setMessage("Error: User was not deleted.");
            return false;
        }

        return true;
    }
}
