package org.service;

import org.exceptions.DAOException;
import org.exceptions.DomainException;
import org.model.Task;
import org.model.TaskList;
import org.repository.IRepository;
import org.repository.ListOfTasksRepository;
import org.repository.TaskRepository;
import org.utils.CurrentUser;
import org.utils.MessageGenerator;

import java.sql.Date;
import java.util.List;

public class TaskService {
    TaskRepository taskRepository = new TaskRepository();
    ListOfTasksRepository listOfTasksRepository = new ListOfTasksRepository();
    TaskListService taskListService = new TaskListService();
    ListOfTasksService listOfTasksService = new ListOfTasksService();

    public List<Task> getAllUserTasks(int userId){
        try {
            return taskRepository.getAllUserTasks(userId);
        }
        catch (DAOException e){
            throw new DomainException(e.getMessage(), e);
        }
    }

    public Task getTaskById(int id){
        try{
            return taskRepository.getEntity(id);
        }
        catch (DAOException e){
            throw new DomainException(e.getMessage(), e);
        }
    }

    public void changeTaskInfo(int id, String mode, String newValue){
        try{
            Task task = taskRepository.getEntity(id);
            switch (mode){
                case "name": task.setName(newValue); break;
                case "description": task.setDescription(newValue); break;
                case "alert_time": task.setAlertTime(Date.valueOf(newValue));
                default: break;
            }

            taskRepository.update(task);
        }
        catch (DAOException e){
            throw new DomainException(e.getMessage(), e);
        }

    }

    public void addNewTask(String taskName, String description, String alertTime){
        Task task = new Task();
        task.setName(taskName);
        task.setDescription(description);
        task.setAlertTime(Date.valueOf(alertTime));
        task.setAlertReceived();
        try{
            taskRepository.create(task);
            TaskList taskList = taskListService.getTaskListByName(String.format("%s default task list", CurrentUser.getUser().getUsername()));
            listOfTasksService.addToListById(taskList.getId(), task.getId());
        }
        catch(DAOException e){
            throw new DomainException(e.getMessage(), e);
        }
        catch (DomainException e){
            throw e;
        }
    }

    public void deleteTaskById(int id){
        try{
            taskRepository.delete(id);
        }
        catch (DAOException e){
            throw new DomainException(e.getMessage(), e);
        }
    }
}
