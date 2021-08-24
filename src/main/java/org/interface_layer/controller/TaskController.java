package org.interface_layer.controller;

import org.exceptions.DomainException;
import org.exceptions.InterfaceException;
import org.dao_layer.model.Task;
import org.business_layer.service.TaskService;
import org.utils.CurrentUser;

import java.util.List;

public class TaskController {
    TaskService taskService = new TaskService();

    public List<Task> getAllUserTasks(){
        try{
            return taskService.getAllUserTasks(CurrentUser.getUser().getId());
        }
        catch (DomainException e){
            throw new InterfaceException(e.getMessage(), e);
        }
    }

    public Task getTaskById(int id){
        try{
            return taskService.getTaskById(id);
        }
        catch (DomainException e){
            throw new InterfaceException(e.getMessage(), e);
        }
    }

    public void changeTaskInfo(int id, String mode, String newValue){
        try{
            taskService.changeTaskInfo(id, mode, newValue);
        }
        catch (DomainException e) {
            throw new InterfaceException(e.getMessage(), e);
        }
    }

    public void addNewTask(String taskName, String description, String alertTime){
        try{
            taskService.addNewTask(taskName, description, alertTime);
        }
        catch (DomainException e){
            throw new InterfaceException(e.getMessage(), e);
        }
    }

    public void deleteTaskById(int id){
        try{
            taskService.deleteTaskById(id);
        }
        catch (DomainException e){
            throw new InterfaceException(e.getMessage(), e);
        }
    }
}
