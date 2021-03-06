package org.business_layer.service;

import org.exceptions.DAOException;
import org.exceptions.DomainException;
import org.dao_layer.model.TaskList;
import org.dao_layer.repository.TaskListRepository;
import org.utils.CurrentUser;

import java.util.List;

public class TaskListService {
    private final TaskListRepository taskListRepository = new TaskListRepository();
    private final ListOfTasksService listOfTasksService = new ListOfTasksService();

    public void addTaskList(String name){
        try{
            TaskList taskList = new TaskList();
            taskList.setUserId(CurrentUser.getUser().getId());
            taskList.setName(name);
            taskListRepository.create(taskList);
        }
        catch (DAOException e){
            throw new DomainException(e.getMessage(), e);
        }
    }

    public TaskList getTaskList(int id){
        try{
            return taskListRepository.getEntity(id);
        }
        catch (DAOException e){
            throw new DomainException(e.getMessage(), e);
        }
    }

    public TaskList getTaskListByName(String name){
        try{
            return taskListRepository.getListByName(name);
        }
        catch (DAOException e){
            throw new DomainException(e.getMessage(), e);
        }
    }

    public List<TaskList> getListsForUser(int id){
        try {
            return taskListRepository.getListsForUser(id);
        }
        catch (DAOException e){
            throw new DomainException(e.getMessage(), e);
        }
    }

    public void updateTaskList(int id, String mode, String newValue){
        try{
            TaskList taskList = getTaskList(id);
            if (mode.equals("user_id"))
                taskList.setUserId(Integer.parseInt(newValue));
            else if (mode.equals("name"))
                taskList.setName(newValue);
            taskListRepository.update(taskList);
        }
        catch (DAOException e){
            throw new DomainException(e.getMessage(), e);
        }
        catch (DomainException e){
            throw e;
        }
    }

    public void deleteTaskList(int id){
        try {
            taskListRepository.delete(id);
            listOfTasksService.deleteListOfTasks(id);
        }
        catch (DAOException e){
            throw new DomainException(e.getMessage(), e);
        }
    }
}
