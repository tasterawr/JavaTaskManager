package org.business_layer.service;

import org.exceptions.DAOException;
import org.exceptions.DomainException;
import org.dao_layer.model.Task;
import org.dao_layer.repository.ListOfTasksRepository;

import java.util.List;

public class ListOfTasksService {
    private final ListOfTasksRepository listOfTasksRepository = new ListOfTasksRepository();

    public void addToListById(int listId, int taskId){
        try{
            listOfTasksRepository.addTask(listId, taskId);
        }
        catch (DAOException e){
            throw new DomainException(e.getMessage(), e);
        }
    }

    public List<Task> getListOfTasks(int listId){
        try{
            return listOfTasksRepository.getListOfTasks(listId);
        }
        catch (DAOException e){
            throw new DomainException(e.getMessage(), e);
        }
    }

    public void changeTaskList(int taskId, int newListId){
        try{
            listOfTasksRepository.changeTaskList(taskId, newListId);
        }
        catch (DAOException e){
            throw new DomainException(e.getMessage(), e);
        }
    }

    public void deleteListOfTasks(int listId){
        try{
            listOfTasksRepository.delete(listId);
        }
        catch (DAOException e){
            throw new DomainException(e.getMessage(), e);
        }
    }
}
