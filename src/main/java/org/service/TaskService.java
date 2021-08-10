package org.service;

import org.model.Task;
import org.repository.IRepository;
import org.repository.ListOfTasksRepository;
import org.repository.TaskRepository;
import org.utils.CurrentUser;
import org.utils.MessageGenerator;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class TaskService {
    TaskRepository taskRepository = new TaskRepository();
    ListOfTasksRepository listOfTasksRepository = new ListOfTasksRepository();

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
        Task task = new Task();
        task.setName(taskName);
        task.setDescription(description);
        task.setAlertTime(Date.valueOf(alertTime));
        task.setAlertReceived();
        try{
            Task result = taskRepository.create(task);
            listOfTasksRepository.addTask(CurrentUser.getUser().getId(), result);
            MessageGenerator.setMessage("Error: Task was added successfully.");
            return result;
        }
        catch(ClassNotFoundException | SQLException e){
            MessageGenerator.setMessage("Error: Task was not added.");
            return null;
        }

    }

    public boolean deleteTaskById(int id){
        return false;
    }
}
