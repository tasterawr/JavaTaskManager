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

    public List<Task> getTaskList(int listId){
        try{
            return listOfTasksRepository.getListTasks(listId);
        }
        catch (DAOException e){
            throw new DomainException(e.getMessage(), e);
        }

    }
}
