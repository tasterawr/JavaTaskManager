package org.controller;

import org.exceptions.DomainException;
import org.exceptions.InterfaceException;
import org.model.ListOfTasks;
import org.model.Task;
import org.repository.IRepository;
import org.repository.ListOfTasksRepository;
import org.service.ListOfTasksService;
import org.utils.CurrentUser;

import java.util.List;

public class ListOfTasksController {
    private ListOfTasksService listOfTasksService = new ListOfTasksService();

    public List<Task> getTaskList(int listId){
        try {
            return listOfTasksService.getTaskList(listId);
        }
        catch (DomainException e){
            throw new InterfaceException(e.getMessage(), e);
        }
    }
}
