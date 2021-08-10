package org.controller;

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

    public Task addNewTask(String taskName, String description, String alertTime){
        taskService.addNewTask(taskName, description, alertTime);
        return null;
    }

    public boolean deleteTaskById(int id){
        return false;
    }
}
