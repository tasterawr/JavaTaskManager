package org.controller;

import org.exceptions.DomainException;
import org.exceptions.InterfaceException;
import org.model.Task;
import org.repository.IRepository;
import org.repository.TaskRepository;
import org.service.TaskService;

import java.util.Date;
import java.util.List;

public class TaskController {
    TaskService taskService = new TaskService();


    public List<Task> showAllTasks(int id){
        //taskRepository.create(null);
        return null;
    }

    public Task getTaskById(int id){
        return null;
    }

    public boolean changeTaskInfo(Task task, String changeType, String newValue){
        //update task
        return false;
    }

    public void addNewTask(String taskName, String description, String alertTime){
        try{
            taskService.addNewTask(taskName, description, alertTime);
        }
        catch (DomainException e){
            throw new InterfaceException(e.getMessage(), e);
        }
    }

    public boolean deleteTaskById(int id){
        return false;
    }
}
