package org.repository;

import org.db_connection.DatabaseConnector;
import org.exceptions.DAOException;
import org.model.User;

import java.sql.*;
import java.util.List;

public class UserRepository implements IRepository<User>{
    @Override
    public void create(User entity) {
        Connection connection = null;
        PreparedStatement statement = null;
        User requestedUser;
        try{
            requestedUser = getUserByLogin(entity.getUsername());
            if (requestedUser.getId() != -1){
                throw new DAOException("Error: User with such username already exists.");
            }

            String sql = "INSERT INTO users(username, password, firstname, lastname, phone)" +
                    " VALUES(?,?,?,?,?)";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getUsername());
            statement.setString(2, entity.getPassword());
            statement.setString(3, entity.getFirstName());
            statement.setString(4, entity.getLastName());
            statement.setString(5, entity.getPhone());
            statement.executeUpdate();

            requestedUser = getUserByLogin(entity.getUsername());
            entity.setId(requestedUser.getId());
        }
        catch (SQLException e){
            throw new DAOException("Error: New user was not added.", e);
        }
        finally {
            try{
                connection.close();
            }
            catch (SQLException e){
                //log Could not close connection
            }
            try {
                statement.close();
            }
            catch (SQLException e){
                //log Could not close statement
            }
        }
    }

    @Override
    public User getEntity(int id) {
        return null;
    }

    public User getUserByLogin(String username){
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = DatabaseConnector.connect();
            String sql = "SELECT * FROM users WHERE username = ?";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, username);
            ResultSet result = statement.executeQuery();
            if (result.next() == false) {
                User user = new User();
                user.setId(-1);
                return user;
            }

            User user = new User();
            user.setId(Integer.parseInt(result.getString("id")));
            user.setUsername(result.getString("username"));
            user.setPassword(result.getString("password"));
            user.setFirstName(result.getString("firstname"));
            user.setLastName(result.getString("lastname"));
            user.setPhone(result.getString("phone"));
            return user;
        }
        catch (ClassNotFoundException | SQLException e){
            throw new DAOException("Error: New user was not added.", e);
        }
        finally {
            try{
                connection.close();
            }
            catch (SQLException e){
                //log Could not close connection
            }
            try {
                statement.close();
            }
            catch (SQLException e){
                //log Could not close statement
            }
        }
    }

    public User getUserByLoginPass(String login, String password){
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DatabaseConnector.connect();
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, login);
            statement.setString(2, password);
            ResultSet result = statement.executeQuery();
            if (result.next() == false)
                throw new DAOException("Error: Wrong login or password.");

            User user = new User();
            user.setId(Integer.parseInt(result.getString("id")));
            user.setUsername(result.getString("username"));
            user.setPassword(result.getString("password"));
            user.setFirstName(result.getString("firstname"));
            user.setLastName(result.getString("lastname"));
            user.setPhone(result.getString("phone"));
            return user;
        }
        catch(ClassNotFoundException | SQLException e){
            throw new DAOException("Error: Couldn't get user.", e);
        }
        finally {
            try{
                connection.close();
            }
            catch (SQLException e){
                //log Could not close connection
            }
            try {
                statement.close();
            }
            catch (SQLException e){
                //log Could not close statement
            }
        }
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public void update(User entity) {

    }

    @Override
    public void delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = DatabaseConnector.connect();
            String sql = "DELETE FROM users WHERE id = ?";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            int result = statement.executeUpdate();
            if (result != 0)
                throw new DAOException("Error: No user with such id.");
        }
        catch(ClassNotFoundException | SQLException e){
            throw new DAOException("Error: Couldn't delete user.", e);
        }
        finally {
            try{
                connection.close();
            }
            catch (SQLException e){
                //log Could not close connection
            }
            try {
                statement.close();
            }
            catch (SQLException e){
                //log Could not close statement
            }
        }
    }
}
