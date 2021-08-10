package org.repository;

import org.db_connection.DatabaseConnector;
import org.model.ListOfTasks;
import org.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ListOfTasksRepository implements IRepository<ListOfTasks> {

    @Override
    public ListOfTasks create(ListOfTasks entity) {
        return null;
    }

    public void addTask(int userId, Task task) throws ClassNotFoundException, SQLException {
        Connection connection = DatabaseConnector.connect();
        String sql = "INSERT INTO list_of_tasks(user_id, task_id, name) VALUES(?, ?, ?)";
        PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pst.setInt(1, userId);
        pst.setInt(2, task.getId());
        pst.setString(3, task.getName());
        pst.executeUpdate();
    }

    @Override
    public ListOfTasks getEntity(int id) {
        return null;
    }

    @Override
    public List<ListOfTasks> getAll() {
        return null;
    }

    public List<Task> getUserTasks(int userId) throws ClassNotFoundException, SQLException{
        Connection connection = DatabaseConnector.connect();
        String sql = "SELECT T.id, T.name, T.description, T.alert_time, T.alert_received " +
                "FROM list_of_tasks INNER JOIN task T " +
                "ON task_id = T.id " +
                "WHERE user_id = ?";
        PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pst.setInt(1, userId);
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

    @Override
    public ListOfTasks update(ListOfTasks entity) {
        return null;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
