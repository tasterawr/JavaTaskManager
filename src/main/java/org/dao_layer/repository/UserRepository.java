package org.dao_layer.repository;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.dao_layer.db_connection.DatabaseConnector;
import org.exceptions.DAOException;
import org.dao_layer.model.User;

import java.sql.*;
import java.util.List;

public class UserRepository implements IRepository<User>{
    static final Logger LOGGER = Logger.getLogger(UserRepository.class);
    static final String PATH = "src/main/resources/log4j.properties";

    @Override
    public void create(User entity) {
        PropertyConfigurator.configure(PATH);
        Connection connection = null;
        PreparedStatement statement = null;
        User requestedUser;
        try{
            requestedUser = getUserByLogin(entity.getUsername());
            if (requestedUser.getId() != -1){
                DAOException daoException = new DAOException("Error: User with such username already exists.");
                LOGGER.error("User was not created.", daoException);
                throw daoException;
            }

            connection = DatabaseConnector.connect();
            String sql = "INSERT INTO users(username, password, firstname, lastname, phone)" +
                    " VALUES(?,?,?,?,?)";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getUsername());
            statement.setString(2, entity.getPassword());
            statement.setString(3, entity.getFirstName());
            statement.setString(4, entity.getLastName());
            statement.setString(5, entity.getPhone());
            statement.executeUpdate();
            LOGGER.info(String.format("New user (username: %s) was created successfully.", entity.getUsername()));

            requestedUser = getUserByLogin(entity.getUsername());
            entity.setId(requestedUser.getId());
        }
        catch (SQLException e){
            LOGGER.error("User was not created.", e);
            throw new DAOException("Error: New user was not added.", e);
        }
        catch (ClassNotFoundException e){
            LOGGER.error(e.getMessage());
        }
        finally {
            try{
                connection.close();
            }
            catch (SQLException e){
                LOGGER.error("Could not close the connection.", e);
            }
            try {
                statement.close();
            }
            catch (SQLException e){
                LOGGER.error("Could not close the statement.", e);
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
                LOGGER.info(String.format("No user with username \"%s\" was found.", username));
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
            LOGGER.info(String.format("User with username \"%s\" found successfully.", username));
            return user;
        }
        catch (ClassNotFoundException | SQLException e){
            LOGGER.error(String.format("Error: Could not get user with username %s.", username), e);
            throw new DAOException("Error: Could not get user.", e);
        }
        finally {
            try{
                connection.close();
            }
            catch (SQLException e){
                LOGGER.error("Could not close the connection.", e);
            }
            try {
                statement.close();
            }
            catch (SQLException e){
                LOGGER.error("Could not close the statement.", e);
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
            if (result.next() == false){
                LOGGER.error("Could not log in. Wrong login or password.");
                throw new DAOException("Error: Wrong login or password.");
            }


            User user = new User();
            user.setId(Integer.parseInt(result.getString("id")));
            user.setUsername(result.getString("username"));
            user.setPassword(result.getString("password"));
            user.setFirstName(result.getString("firstname"));
            user.setLastName(result.getString("lastname"));
            user.setPhone(result.getString("phone"));
            LOGGER.info(String.format("User (username: %s) logged in successfully.", login));
            return user;
        }
        catch(ClassNotFoundException | SQLException e){
            LOGGER.error("Error: Couldn't get user.", e);
            throw new DAOException("Error: Couldn't get user.", e);
        }
        finally {
            try{
                connection.close();
            }
            catch (SQLException e){
                LOGGER.error("Could not close the connection.", e);
            }
            try {
                statement.close();
            }
            catch (SQLException e){
                LOGGER.error("Could not close the statement.", e);
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
            if (result != 0){
                LOGGER.error(String.format("No user with id %s.", id));
                throw new DAOException("Error: No user with such id.");
            }
            LOGGER.info(String.format("User with id %s deleted successfully.", id));
        }
        catch(ClassNotFoundException | SQLException e){
            LOGGER.error("Error: Couldn't delete user.", e);
            throw new DAOException("Error: Couldn't delete user.", e);
        }
        finally {
            try{
                connection.close();
            }
            catch (SQLException e){
                LOGGER.error("Could not close the connection.", e);
            }
            try {
                statement.close();
            }
            catch (SQLException e){
                LOGGER.error("Could not close the statement.", e);
            }
        }
    }
}
