package org.dao_layer.repository;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.dao_layer.db_connection.DatabaseConnector;
import org.exceptions.DAOException;
import org.dao_layer.model.TaskList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskListRepository implements IRepository<TaskList> {
    static final Logger LOGGER = Logger.getLogger(UserRepository.class);
    static final String PATH = "src/main/resources/log4j.properties";

    @Override
    public void create(TaskList entity)  {
        PropertyConfigurator.configure(PATH);
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DatabaseConnector.connect();
            String sql = "INSERT INTO task_list(user_id, name) VALUES(?, ?)";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, entity.getUserId());
            statement.setString(2, entity.getName());
            int result = statement.executeUpdate();
            if (result != 1){
                LOGGER.error(String.format("Task list with name \"%s\" already exists.", entity.getName()));
                throw new DAOException(String.format("Error: Task list with name \"%s\" already exists.", entity.getName()));
            }

            LOGGER.info(String.format("New task list (name: %s) was created successfully.", entity.getName()));
        }
        catch (ClassNotFoundException | SQLException e){
            LOGGER.error("Could not create new task list.", e);
            throw new DAOException("Error: Could not create new task list.");
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
    public TaskList getEntity(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DatabaseConnector.connect();
            String sql = "SELECT * FROM task_list WHERE id = ?";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            ResultSet resultSet= statement.executeQuery();
            if (!resultSet.next()){
                LOGGER.error(String.format("No task list with id %s.", id));
                throw new DAOException(String.format("Error: No task list with id %s.", id));
            }


            TaskList taskList = new TaskList();
            taskList.setId(resultSet.getInt("id"));
            taskList.setUserId(resultSet.getInt("user_id"));
            taskList.setName(resultSet.getString("name"));
            LOGGER.info(String.format("Task list with id %s received successfully.", id));

            return taskList;
        }
        catch (ClassNotFoundException | SQLException e){
            LOGGER.error(String.format("Could not get task list with id %s", id), e);
            throw new DAOException(String.format("Error: Could not get task list with id %s", id), e);
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

    public TaskList getListByName(String name){
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = DatabaseConnector.connect();
            String sql = "SELECT * FROM task_list WHERE name = ?";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            ResultSet resultSet= statement.executeQuery();
            if (!resultSet.next()){
                LOGGER.error(String.format("No task list with name \"%s\".", name));
                throw new DAOException(String.format("Error: No task list with name \"%s\".", name));
            }


            TaskList taskList = new TaskList();
            taskList.setId(resultSet.getInt("id"));
            taskList.setUserId(resultSet.getInt("user_id"));
            taskList.setName(resultSet.getString("name"));
            LOGGER.info(String.format("Task list with name \"%s\" received successfully.", name));

            return taskList;
        }
        catch (ClassNotFoundException | SQLException e){
            LOGGER.error(String.format("Could not get task list with name \"%s\".", name), e);
            throw new DAOException(String.format("Error: Could not get task list with name \"%s\".", name), e);
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
    public List<TaskList> getAll() {
        return null;
    }

    public List<TaskList> getListsForUser(int userId){
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DatabaseConnector.connect();
            String sql = "SELECT * FROM task_list WHERE user_id = ?";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, userId);
            ResultSet resultSet= statement.executeQuery();

            List<TaskList> taskLists = new ArrayList<>();
            while (resultSet.next()){
                TaskList taskList = new TaskList();
                taskList.setId(resultSet.getInt("id"));
                taskList.setUserId(resultSet.getInt("user_id"));
                taskList.setName(resultSet.getString("name"));
                taskLists.add(taskList);
            }
            LOGGER.info(String.format("Task lists for user with id %s received successfully.", userId));

            return taskLists;
        }
        catch (ClassNotFoundException | SQLException e){
            LOGGER.error(String.format("Could not get task lists for user with id %s.", userId));
            throw new DAOException(String.format("Error: Could not get task lists for user with id %s.", userId), e);
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
    public void update(TaskList entity) {
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = DatabaseConnector.connect();
            String sql = "UPDATE task_list SET name = ? WHERE id = ?";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, entity.getName());
            statement.setInt(2, entity.getId());
            statement.executeUpdate();

            LOGGER.info(String.format("Task list (id: %s) updated successfully.", entity.getId()));
        }
        catch (ClassNotFoundException | SQLException e){
            LOGGER.error(String.format("Could not update task list (id: %s).", entity.getId()), e);
            throw new DAOException(String.format("Error: Could not update task list (id: %s).", entity.getId()), e);
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
            String sql = "DELETE FROM task_list WHERE id = ?";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            int result = statement.executeUpdate();
            if (result != 1)
                throw new DAOException("Error: No task list with such id.");
        }
        catch (ClassNotFoundException | SQLException e){
            throw new DAOException("Error: Could not delete task list.");
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
