package org.repository;

import org.db_connection.DatabaseConnector;
import org.model.User;

import java.sql.*;
import java.util.List;

public class UserRepository implements IRepository<User>{
    @Override
    public User create(User entity) throws ClassNotFoundException, SQLException {
        Connection connection = DatabaseConnector.connect();
        String sql = "SELECT * FROM users WHERE username = ?";
        PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pst.setString(1, entity.getUsername());
        ResultSet result = pst.executeQuery();
        if (result.next())
            return null;

        sql = "INSERT INTO users(username, password, firstname, lastname, phone)" +
                " VALUES(?, ?, ?, ?, ?)";

        pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pst.setString(1, entity.getUsername());
        pst.setString(2, entity.getPassword());
        pst.setString(3, entity.getFirstName());
        pst.setString(4, entity.getLastName());
        pst.setString(5, entity.getPhone());
        pst.executeUpdate();

        return entity;
    }

    @Override
    public User getEntity(int id) {
        return null;
    }

    public User getUserByLoginPass(String login, String password) throws ClassNotFoundException, SQLException {
        Connection connection = DatabaseConnector.connect();
        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";
        PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pst.setString(1, login);
        pst.setString(2, password);
        ResultSet result = pst.executeQuery();
        try{
            result.next();
        }
        catch(SQLException e){
            return null;
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

    @Override
    public List<User> getAll() {
        return null;
    }

    @Override
    public User update(User entity) {
        return null;
    }

    @Override
    public void delete(int it) {

    }
}
