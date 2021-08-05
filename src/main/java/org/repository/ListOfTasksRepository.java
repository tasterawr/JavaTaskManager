package org.repository;

import org.model.ListOfTasks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class ListOfTasksRepository implements IRepository<ListOfTasks> {

    @Override
    public ListOfTasks create(ListOfTasks entity) {
        return null;
    }

    @Override
    public ListOfTasks getEntity(int id) {
        return null;
    }

    @Override
    public List<ListOfTasks> getAll() {
        return null;
    }

    @Override
    public ListOfTasks update(ListOfTasks entity) {
        return null;
    }

    @Override
    public void delete(int it) {

    }
}
