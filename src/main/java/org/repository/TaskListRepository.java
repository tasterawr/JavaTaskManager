package org.repository;

import org.db_connection.DatabaseConnector;
import org.exceptions.DAOException;
import org.model.TaskList;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskListRepository implements IRepository<TaskList> {

    @Override
    public void create(TaskList entity)  {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DatabaseConnector.connect();
            String sql = "INSERT INTO task_list(id, user_id, name) VALUES(?, ?, ?)";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, entity.getId());
            statement.setInt(2, entity.getUserId());
            statement.setString(3, entity.getName());
            int result = statement.executeUpdate();
            if (result != 1)
                throw new DAOException("Error: Task list with such name already exists.");
        }
        catch (ClassNotFoundException | SQLException e){
            throw new DAOException("Error: Could not create new task list.");
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
    public TaskList getEntity(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DatabaseConnector.connect();
            String sql = "SELECT * FROM task_list WHERE id = ?";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, id);
            ResultSet resultSet= statement.executeQuery();
            if (!resultSet.next())
                throw new DAOException("Error: No task list with such id.");

            TaskList taskList = new TaskList();
            taskList.setId(resultSet.getInt("id"));
            taskList.setUserId(resultSet.getInt("user_id"));
            taskList.setName(resultSet.getString("name"));

            return taskList;
        }
        catch (ClassNotFoundException | SQLException e){
            throw new DAOException("Error: Could not get task list.");
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

    public TaskList getListByName(String name){
        Connection connection = null;
        PreparedStatement statement = null;
        try{
            connection = DatabaseConnector.connect();
            String sql = "SELECT * FROM task_list WHERE name = ?";
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            ResultSet resultSet= statement.executeQuery();
            if (!resultSet.next())
                throw new DAOException(String.format("Error: No task list with name \"%s\".", name));

            TaskList taskList = new TaskList();
            taskList.setId(resultSet.getInt("id"));
            taskList.setUserId(resultSet.getInt("user_id"));
            taskList.setName(resultSet.getString("name"));

            return taskList;
        }
        catch (ClassNotFoundException | SQLException e){
            throw new DAOException(String.format("Error: Could not get task list with name \"%s\".", name));
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

            return taskLists;
        }
        catch (ClassNotFoundException | SQLException e){
            throw new DAOException("Error: Could not get task list.");
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
        }
        catch (ClassNotFoundException | SQLException e){
            throw new DAOException("Error: Could not get task list.");
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
