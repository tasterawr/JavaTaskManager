package org.repository;

import org.db_connection.DatabaseConnector;
import org.exceptions.DAOException;
import org.model.ListOfTasks;
import org.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListOfTasksRepository implements IRepository<ListOfTasks> {

    @Override
    public void create(ListOfTasks entity) {
        return;
    }

    public void addTask(int listId, int taskId) {
        try{
            Connection connection = DatabaseConnector.connect();
            String sql = "INSERT INTO list_of_tasks(list_id, task_id) VALUES(?, ?)";
            PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, listId);
            pst.setInt(2, taskId);
            pst.executeUpdate();
        }
        catch (ClassNotFoundException | SQLException e){
            throw new DAOException(String.format("Error: Task ID %s could not be added to the task list ID %s.", taskId, listId), e);
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

    public List<Task> getListTasks(int listId) {
        try{
            Connection connection = DatabaseConnector.connect();
            String sql = "SELECT T.id, T.name, T.description, T.alert_time, T.alert_received " +
                    "FROM list_of_tasks INNER JOIN task T " +
                    "ON task_id = T.id " +
                    "WHERE list_id = ?";
            PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, listId);
            ResultSet resultSet = pst.executeQuery();
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
    }

    @Override
    public void update(ListOfTasks entity) {

    }

    public void changeTaskList(int taskId, int newListId){
        try{
            Connection connection = DatabaseConnector.connect();
            String sql = "UPDATE list_of_tasks SET list_id = ? WHERE task_id = ?";
            PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pst.setInt(1, newListId);
            pst.setInt(2, taskId);
            ResultSet resultSet = pst.executeQuery();
        }
        catch (ClassNotFoundException | SQLException e){
            throw new DAOException(String.format("Error: Could not put task ID %s to list ID %s.", taskId, newListId), e);
        }
    }

    @Override
    public void delete(int id) {

    }
}
