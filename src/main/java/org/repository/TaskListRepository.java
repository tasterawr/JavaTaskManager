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
        try {
            Connection connection = DatabaseConnector.connect();
            String sql = "INSERT INTO task_list(id, user_id, name) VALUES(?, ?, ?)";
            PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, entity.getId());
            pst.setInt(2, entity.getUserId());
            pst.setString(3, entity.getName());
            int result = pst.executeUpdate();
            if (result != 1)
                throw new DAOException("Error: Task list with such name already exists.");
        }
        catch (ClassNotFoundException | SQLException e){
            throw new DAOException("Error: Could not create new task list.");
        }
    }

    @Override
    public TaskList getEntity(int id) {
        try {
            Connection connection = DatabaseConnector.connect();
            String sql = "SELECT * FROM task_list WHERE id = ?";
            PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, id);
            ResultSet resultSet= pst.executeQuery();
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
    }

    public TaskList getListByName(String name){
        try{
            Connection connection = DatabaseConnector.connect();
            String sql = "SELECT * FROM task_list WHERE name = ?";
            PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, name);
            ResultSet resultSet= pst.executeQuery();
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
    }

    @Override
    public List<TaskList> getAll() {
        return null;
    }

    public List<TaskList> getListsForUser(int userId){
        try {
            Connection connection = DatabaseConnector.connect();
            String sql = "SELECT * FROM task_list WHERE user_id = ?";
            PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, userId);
            ResultSet resultSet= pst.executeQuery();

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
    }

    @Override
    public void update(TaskList entity) {
        try{
            Connection connection = DatabaseConnector.connect();
            String sql = "UPDATE task_list SET name = ? WHERE id = ?";
            PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setString(1, entity.getName());
            pst.setInt(2, entity.getId());
            pst.executeUpdate();
        }
        catch (ClassNotFoundException | SQLException e){
            throw new DAOException("Error: Could not get task list.");
        }
    }

    @Override
    public void delete(int id) {
        try{
            Connection connection = DatabaseConnector.connect();
            String sql = "DELETE FROM task_list WHERE id = ?";
            PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, id);
            int result = pst.executeUpdate();
            if (result != 1)
                throw new DAOException("Error: No task list with such id.");
        }
        catch (ClassNotFoundException | SQLException e){
            throw new DAOException("Error: Could not delete task list.");
        }
    }
}
