package org.business_layer.service;

import org.exceptions.DAOException;
import org.exceptions.DomainException;
import org.dao_layer.model.User;
import org.utils.CurrentUser;
import org.utils.PasswordEncryptor;
import org.dao_layer.repository.UserRepository;

public class UserService {
    UserRepository userRepository = new UserRepository();

    public User getUserByLoginPass(String login, String password){
        String encryptedPassword = PasswordEncryptor.encrypt(password);
        try{
            User user = userRepository.getUserByLoginPass(login, encryptedPassword);
            CurrentUser.setUser(user);
            return user;
        }
        catch (DAOException e){
            throw new DomainException(e.getMessage(), e);
        }
    }

    public void addNewUser(String username, String password, String firstname, String lastname, String phone){
        User user = new User();
        user.setUsername(username);
        user.setPassword(PasswordEncryptor.encrypt(password));
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setPhone(phone);

        try{
            userRepository.create(user);
            CurrentUser.setUser(user);
        }
        catch (DAOException e){
            throw new DomainException(e.getMessage(), e);
        }

    }

    public void deleteUser(String username, String password){
        try{
            User user = userRepository.getUserByLoginPass(username, PasswordEncryptor.encrypt(password));

            userRepository.delete(user.getId());
        }
        catch (DAOException e){
            throw new DomainException(e.getMessage(), e);
        }
    }
}
