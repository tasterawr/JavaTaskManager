package org.repository;

import org.db_connection.DatabaseConnector;
import org.exceptions.DAOException;
import org.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskRepository implements IRepository<Task>{

    @Override
    public void create(Task entity) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DatabaseConnector.connect();
            String sql = "INSERT INTO task(name, description, alert_time) VALUES(?, ?, ?)";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDescription());
            statement.setDate(3, entity.getAlertTime());
            statement.executeUpdate();
            ResultSet rsKeys = statement.getGeneratedKeys();
            if (rsKeys.next()) {
                entity.setId(rsKeys.getInt(1));
            }
        }
        catch(ClassNotFoundException | SQLException e){
            throw new DAOException("Error: New task wasn't created.", e);
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
    public Task getEntity(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = DatabaseConnector.connect();
            String sql = "SELECT * 1FROM task WHERE id = ?";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
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
    public List<Task> getAll() {
        return null;
    }

    public List<Task> getAllUserTasks(int userId){
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = DatabaseConnector.connect();
            String sql =
                    " SELECT T.id, T.name, T.description, T.alert_time" +
                    " FROM (SELECT L.task_id" +
                    " FROM task_list TL INNER JOIN list_of_tasks L" +
                    " ON TL.id = L.list_id" +
                    " WHERE user_id = ?) TAB" +
                    "   INNER JOIN task T" +
                    "   ON TAB.task_id = T.id";

            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            List<Task> tasks = new ArrayList<>();
            while(resultSet.next()){
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setAlertTime(resultSet.getDate("alert_time"));
                task.setAlertReceived();
                tasks.add(task);
            }

            return tasks;
        }
        catch(ClassNotFoundException | SQLException e){
            throw new DAOException("Error: Couldn't get requested tasks.", e);
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
    public void update(Task entity) {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = DatabaseConnector.connect();
            String sql = "UPDATE tasks SET name = ?, description = ?, alert_time = ? " +
                    " WHERE id = ?";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDescription());
            statement.setDate(3, entity.getAlertTime());
            statement.setInt(4, entity.getId());
            int result = statement.executeUpdate();
        }
        catch(ClassNotFoundException | SQLException e){
            throw new DAOException("Error: Couldn't update requested task.", e);
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
    public void delete(int id) {

    }
}
