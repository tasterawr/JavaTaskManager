package org.service;

import org.exceptions.DAOException;
import org.exceptions.DomainException;
import org.model.Task;
import org.repository.ListOfTasksRepository;
import org.utils.CurrentUser;
import org.utils.MessageGenerator;

import java.sql.SQLException;
import java.util.List;

public class ListOfTasksService {
    private ListOfTasksRepository listOfTasksRepository = new ListOfTasksRepository();

    public List<Task> getListOfTasks(int listId){
        try{
            return listOfTasksRepository.getListOfTasks(listId);
        }
        catch (DAOException e){
            throw new DomainException(e.getMessage(), e);
        }
    }

    public void addToListById(int listId, int taskId){
        try{
            listOfTasksRepository.addTask(listId, taskId);
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
