package org.repository;

import org.db_connection.DatabaseConnector;
import org.model.Task;

import java.sql.*;
import java.util.List;

public class TaskRepository implements IRepository<Task>{

    @Override
    public Task create(Task entity) throws ClassNotFoundException, SQLException{
        Connection connection = DatabaseConnector.connect();
        String sql = "INSERT INTO task(name, description) VALUES(?, ?)";
        PreparedStatement pst = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        pst.setString(1, entity.getName());
        pst.setString(2, entity.getDescription());
        pst.executeUpdate();
        System.out.println("Query Completed");
        connection.close();
        return null;
    }

    @Override
    public Task getEntity(int id) {
        return null;
    }

    @Override
    public List<Task> getAll() {
        return null;
    }

    @Override
    public Task update(Task entity) {
        return null;
    }

    @Override
    public void delete(int it) {

    }
}
