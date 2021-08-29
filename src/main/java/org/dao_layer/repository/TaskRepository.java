package org.dao_layer.repository;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.dao_layer.db_connection.DatabaseConnector;
import org.exceptions.DAOException;
import org.dao_layer.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TaskRepository implements IRepository<Task>{
    static final Logger LOGGER = Logger.getLogger(TaskRepository.class);
    static final String PATH = "src/main/resources/log4j.properties";

    @Override
    public void create(Task entity) {
        PropertyConfigurator.configure(PATH);
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
                LOGGER.info(String.format("New task (name: %s) was created successfully.", entity.getName()));
                entity.setId(rsKeys.getInt(1));
            }
        }
        catch(ClassNotFoundException | SQLException e){
            LOGGER.error(String.format("New task (name: %s) wasn't created.", entity.getName()), e);
            throw new DAOException(String.format("Error: New task (name: %s) wasn't created.", entity.getName()), e);
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
    public Task getEntity(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = DatabaseConnector.connect();
            String sql = "SELECT * FROM task WHERE id = ?";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next() == false){
                LOGGER.error(String.format("No task with id %s.", id));
                throw new DAOException(String.format("Error: No task with id %s.", id));
            }

            Task task = new Task();
            task.setId(resultSet.getInt("id"));
            task.setName(resultSet.getString("name"));
            task.setDescription(resultSet.getString("description"));
            task.setAlertTime(resultSet.getDate("alert_time"));
            task.setAlertReceived();
            LOGGER.info(String.format("Task (id: %s) was received successfully.", id));

            return task;
        }
        catch(ClassNotFoundException | SQLException e){
            LOGGER.error(String.format("Couldn't get requested task (id: %s).", id), e);
            throw new DAOException(String.format("Error: Couldn't get requested task (id: %s).", id), e);
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
    public List<Task> getAll() {
        return null;
    }

    public Set<Task> getUserTasks(int userId, String condition){
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = DatabaseConnector.connect();
            String sql =
                    " SELECT task.id, task.name, task.description, task.alert_time" +
                    " FROM (SELECT L.task_id" +
                    " FROM task_list TL INNER JOIN list_of_tasks L" +
                    " ON TL.id = L.list_id" +
                    " WHERE user_id = ?) TAB" +
                    "   INNER JOIN task" +
                    "   ON TAB.task_id = task.id " + condition; //stands for condition, might be empty string

            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            Set<Task> tasks = new HashSet<>();
            while(resultSet.next()){
                Task task = new Task();
                task.setId(resultSet.getInt("id"));
                task.setName(resultSet.getString("name"));
                task.setDescription(resultSet.getString("description"));
                task.setAlertTime(resultSet.getDate("alert_time"));
                task.setAlertReceived();
                tasks.add(task);
            }
            LOGGER.info(String.format("Tasks for user (id: %s) were received successfully.", userId));

            return tasks;
        }
        catch(ClassNotFoundException | SQLException e){
            LOGGER.error(String.format("Couldn't get requested tasks for user (id: %s).", userId), e);
            throw new DAOException(String.format("Error: Couldn't get requested tasks for user (id: %s).", userId), e);
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
    public void update(Task entity) {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = DatabaseConnector.connect();
            String sql = "UPDATE task SET name = ?, description = ?, alert_time = ? " +
                    " WHERE id = ?";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getName());
            statement.setString(2, entity.getDescription());
            statement.setDate(3, entity.getAlertTime());
            statement.setInt(4, entity.getId());
            int result = statement.executeUpdate();
            LOGGER.info(String.format("Task (id: %s) was updated successfully.", entity.getId()));
        }
        catch(ClassNotFoundException | SQLException e){
            LOGGER.error(String.format("Couldn't update requested task (id: %s).", entity.getId()), e);
            throw new DAOException(String.format("Error: Couldn't update requested task (id: %s).", entity.getId()), e);
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
            String sql = "DELETE FROM task WHERE id = ?";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            int result = statement.executeUpdate();
            if (result != 1){
                LOGGER.error(String.format("No task with id %s.", id));
                throw new DAOException(String.format("Error: No task with id %s.", id));
            }

            LOGGER.info(String.format("Task (id: %s) was deleted successfully.", id));
        }
        catch(ClassNotFoundException | SQLException e){
            LOGGER.error(String.format("Couldn't delete requested task (id: %s).", id), e);
            throw new DAOException(String.format("Error: Couldn't delete requested task (id: %s).", id), e);
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
