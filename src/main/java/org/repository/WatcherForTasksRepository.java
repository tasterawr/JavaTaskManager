package org.repository;

import org.model.WatcherForTasks;

import java.util.List;

public class WatcherForTasksRepository implements IRepository<WatcherForTasks> {
    @Override
    public void create(WatcherForTasks entity) {

    }

    @Override
    public WatcherForTasks getEntity(int id) {
        return null;
    }

    @Override
    public List<WatcherForTasks> getAll() {
        return null;
    }

    @Override
    public void update(WatcherForTasks entity) {
        return null;
    }

    @Override
    public void delete(int id) {
        return true;
    }
}
