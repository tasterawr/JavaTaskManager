package org.dao_layer.repository;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.dao_layer.db_connection.DatabaseConnector;
import org.exceptions.DAOException;
import org.dao_layer.model.ListOfTasks;
import org.dao_layer.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListOfTasksRepository implements IRepository<ListOfTasks> {
    static final Logger LOGGER = Logger.getLogger(ListOfTasksRepository.class);
    static final String PATH = "src/main/resources/log4j.properties";

    @Override
    public void create(ListOfTasks entity) {
        return;
    }

    public void addTask(int listId, int taskId) {
        PropertyConfigurator.configure(PATH);
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = DatabaseConnector.connect();
            String sql = "INSERT INTO list_of_tasks(list_id, task_id) VALUES(?, ?)";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, listId);
            statement.setInt(2, taskId);
            statement.executeUpdate();
            LOGGER.info(String.format("Task (id: %s) was successfully added to list (id: %s)", taskId, listId));
        }
        catch (ClassNotFoundException | SQLException e){
            LOGGER.error(String.format("Task (id: %s) could not be added to the task list (id: %s).", taskId, listId), e);
            throw new DAOException(String.format("Error: Task (id: %s) could not be added to the task list (id: %s).", taskId, listId), e);
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
            LOGGER.info(String.format("Tasks from list (id: %s) were received successfully.", listId));

            return tasks;
        }
        catch (ClassNotFoundException | SQLException e){
            LOGGER.error(String.format("Could not get tasks for list (id: %s).", listId), e);
            throw new DAOException(String.format("Error: Could not get tasks for list (id: %s).", listId), e);
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
            statement.executeUpdate();
            LOGGER.info(String.format("Task (id: %s) was moved to list (id: %s) successfully.", taskId, newListId));
        }
        catch (ClassNotFoundException | SQLException e){
            LOGGER.error(String.format("Error: Could not move task (id: %s) to list (id: %s).", taskId, newListId), e);
            throw new DAOException(String.format("Error: Could not move task (id: %s) to list (id: %s).", taskId, newListId), e);
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
    public void delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = DatabaseConnector.connect();
            String sql = "DELETE FROM list_of_tasks WHERE list_id = ?";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            statement.executeUpdate();
            LOGGER.info(String.format("Task list (id: %s) was deleted successfully.", id));
        }
        catch (ClassNotFoundException | SQLException e){
            LOGGER.error(String.format("Error: Could not delete task list (id: %s).", id), e);
            throw new DAOException(String.format("Error: Could not delete task list (id: %s).", id), e);
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
