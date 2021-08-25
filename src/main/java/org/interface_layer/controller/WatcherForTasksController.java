package org.interface_layer.controller;

import org.business_layer.service.WatcherForTasksService;
import org.dao_layer.model.Task;
import org.exceptions.DomainException;
import org.exceptions.InterfaceException;

import java.util.List;

public class WatcherForTasksController {
    private final WatcherForTasksService watcherForTasksService = new WatcherForTasksService();

    public List<Task> getOverdueTasks(int userId){
        try{
            return watcherForTasksService.getOverdueTasks(userId);
        }
        catch (DomainException e){
            throw new InterfaceException(e.getMessage(), e);
        }
    }
}
