package org.dao_layer.repository;

import org.dao_layer.db_connection.DatabaseConnector;
import org.exceptions.DAOException;
import org.dao_layer.model.ListOfTasks;
import org.dao_layer.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListOfTasksRepository implements IRepository<ListOfTasks> {

    @Override
    public void create(ListOfTasks entity) {
        return;
    }

    public void addTask(int listId, int taskId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = DatabaseConnector.connect();
            String sql = "INSERT INTO list_of_tasks(list_id, task_id) VALUES(?, ?)";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, listId);
            statement.setInt(2, taskId);
            statement.executeUpdate();
        }
        catch (ClassNotFoundException | SQLException e){
            throw new DAOException(String.format("Error: Task ID %s could not be added to the task list ID %s.", taskId, listId), e);
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
    public ListOfTasks getEntity(int id) {
        return null;
    }

    @Override
    public List<ListOfTasks> getAll() {
        return null;
    }

    public List<Task> getListOfTasks(int listId) {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = DatabaseConnector.connect();
            String sql = "SELECT T.id, T.name, T.description, T.alert_time, T.alert_received " +
                    "FROM list_of_tasks INNER JOIN task T " +
                    "ON task_id = T.id " +
                    "WHERE list_id = ?";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, listId);
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
        catch (ClassNotFoundException | SQLException e){
            throw new DAOException(String.format("Error: Could not get tasks for list ID %s.", listId), e);
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
    public void update(ListOfTasks entity) {

    }

    public void changeTaskList(int taskId, int newListId){
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = DatabaseConnector.connect();
            String sql = "UPDATE list_of_tasks SET list_id = ? WHERE task_id = ?";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, newListId);
            statement.setInt(2, taskId);
            ResultSet resultSet = statement.executeQuery();
        }
        catch (ClassNotFoundException | SQLException e){
            throw new DAOException(String.format("Error: Could not put task ID %s to list ID %s.", taskId, newListId), e);
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
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = DatabaseConnector.connect();
            String sql = "DELETE FROM list_of_tasks WHERE list_id = ?";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            statement.executeUpdate();
        }
        catch (ClassNotFoundException | SQLException e){
            throw new DAOException(String.format("Error: Could not delete tasks from list ID %s.", id), e);
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
