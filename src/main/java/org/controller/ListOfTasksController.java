package org.controller;

import org.model.ListOfTasks;
import org.model.Task;
import org.repository.IRepository;
import org.repository.ListOfTasksRepository;
import org.service.ListOfTasksService;
import org.utils.CurrentUser;

import java.util.List;

public class ListOfTasksController {
    private ListOfTasksService listOfTasksService = new ListOfTasksService();

    public List<Task> getTaskList(){
        return listOfTasksService.getTaskList();
    }
}
