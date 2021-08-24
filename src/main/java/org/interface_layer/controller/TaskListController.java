package org.interface_layer.controller;

import org.exceptions.DomainException;
import org.exceptions.InterfaceException;
import org.dao_layer.model.TaskList;
import org.business_layer.service.TaskListService;
import org.utils.CurrentUser;

import java.util.List;

public class TaskListController {
    TaskListService taskListService = new TaskListService();

    public void addTaskList(String name){
        try{
            taskListService.addTaskList(name);
        }
        catch (DomainException e){
            throw new InterfaceException(e.getMessage(), e);
        }
    }

    public TaskList getTaskList(int id){
        try{
            return taskListService.getTaskList(id);
        }
        catch (DomainException e){
            throw new InterfaceException(e.getMessage(), e);
        }
    }

    public List<TaskList> getListsForUser(){
        try {
            return taskListService.getListsForUser(CurrentUser.getUser().getId());
        }
        catch (DomainException e){
            throw new InterfaceException(e.getMessage(), e);
        }
    }

    public void updateTaskList(int id, String mode, String newValue){
        try{
            taskListService.updateTaskList(id, mode, newValue);
        }
        catch (DomainException e){
            throw new InterfaceException(e.getMessage(), e);
        }
    }

    public void deleteTaskList(int id){
        try {
            taskListService.deleteTaskList(id);
        }
        catch (DomainException e){
            throw new InterfaceException(e.getMessage(), e);
        }
    }
}
