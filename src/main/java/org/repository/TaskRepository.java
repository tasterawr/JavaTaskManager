package org.repository;

import org.db_connection.DatabaseConnector;
import org.exceptions.DAOException;
import org.model.Task;

import java.sql.*;
import java.util.List;

public class TaskRepository implements IRepository<Task>{

    @Override
    public void create(Task entity) {
        try {
            Connection connection = DatabaseConnector.connect();
            String sql = "INSERT INTO task(name, description, alert_time) VALUES(?, ?, ?)";
            PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, entity.getName());
            pst.setString(2, entity.getDescription());
            pst.setDate(3, entity.getAlertTime());
            pst.executeUpdate();
            ResultSet rsKeys = pst.getGeneratedKeys();
            if (rsKeys.next()) {
                entity.setId(rsKeys.getInt(1));
            }
        }
        catch(ClassNotFoundException | SQLException e){
            throw new DAOException("Error: New task wasn't created.", e);
        }
    }

    @Override
    public Task getEntity(int id) {
        try{
            Connection connection = DatabaseConnector.connect();
            String sql = "SELECT FROM task WHERE id = ?";
            PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, id);
            ResultSet resultSet = pst.executeQuery();
            if (resultSet.next() == false)
                throw new DAOException("Error: No task with such id.");

            Task task = new Task();
            task.setId(resultSet.getInt("id"));
            task.setName(resultSet.getString("name"));
            task.setDescription(resultSet.getString("description"));
            task.setAlertTime(resultSet.getDate("alert_time"));
            task.setAlertReceived();
            return task;
        }
        catch(ClassNotFoundException | SQLException e){
            throw new DAOException("Error: Couldn't get requested task.", e);
        }
    }

    @Override
    public List<Task> getAll() {
        return null;
    }

    @Override
    public void update(Task entity) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
