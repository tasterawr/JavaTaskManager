package org.repository;

import org.db_connection.DatabaseConnector;
import org.exceptions.DAOException;
import org.model.User;

import java.sql.*;
import java.util.List;

public class UserRepository implements IRepository<User>{
    @Override
    public void create(User entity) {
        try{
            Connection connection = DatabaseConnector.connect();
            String sql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, entity.getUsername());
            ResultSet result = pst.executeQuery();
            if (result.next())
                throw new DAOException("Error: User already exists."); // user already exists

            sql = "INSERT INTO users(username, password, firstname, lastname, phone)" +
                    " VALUES(?, ?, ?, ?, ?)" +
                    " RETURNING id";

            pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, entity.getUsername());
            pst.setString(2, entity.getPassword());
            pst.setString(3, entity.getFirstName());
            pst.setString(4, entity.getLastName());
            pst.setString(5, entity.getPhone());
            ResultSet resultSet = pst.executeQuery();

            if (resultSet.next()){
                entity.setId(resultSet.getInt(1));
            }
        }
        catch (ClassNotFoundException | SQLException e){
            throw new DAOException("Error: New user was not added.", e);
        }
    }

    @Override
    public User getEntity(int id) {
        return null;
    }

    public User getUserByLoginPass(String login, String password){
        try {
            Connection connection = DatabaseConnector.connect();
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, login);
            pst.setString(2, password);
            ResultSet result = pst.executeQuery();
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
    }

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public void update(User entity) {
        return null;
    }

    @Override
    public void delete(int id) {
        try{
            Connection connection = DatabaseConnector.connect();
            String sql = "DELETE FROM users WHERE id = ?";
            PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, id);
            int result = pst.executeUpdate();
            if (result != 0)
                throw new DAOException("Error: No user with such id.");
        }
        catch(ClassNotFoundException | SQLException e){
            throw new DAOException("Error: Couldn't delete user.", e);
        }

    }
}
