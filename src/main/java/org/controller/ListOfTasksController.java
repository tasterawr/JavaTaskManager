package org.controller;

import org.model.ListOfTasks;
import org.repository.IRepository;
import org.repository.ListOfTasksRepository;

public class ListOfTasksController {
    private IRepository<ListOfTasks> repository = new ListOfTasksRepository();
}
