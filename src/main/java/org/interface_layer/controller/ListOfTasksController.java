package org.interface_layer.controller;

import org.exceptions.DAOException;
import org.exceptions.DomainException;
import org.exceptions.InterfaceException;
import org.dao_layer.model.Task;
import org.business_layer.service.ListOfTasksService;

import java.util.List;

public class ListOfTasksController {
    private ListOfTasksService listOfTasksService = new ListOfTasksService();

    public List<Task> getListOfTasks(int listId){
        try {
            return listOfTasksService.getListOfTasks(listId);
        }
        catch (DomainException e){
            throw new InterfaceException(e.getMessage(), e);
        }
    }

    public void changeTaskList(int taskId, int newListId){
        try{
            listOfTasksService.changeTaskList(taskId, newListId);
        }
        catch (DomainException e){
            throw new InterfaceException(e.getMessage(), e);
        }
    }
}
