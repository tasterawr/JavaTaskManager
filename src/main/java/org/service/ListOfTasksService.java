package org.service;

import org.model.Task;
import org.repository.ListOfTasksRepository;
import org.utils.CurrentUser;
import org.utils.MessageGenerator;

import java.sql.SQLException;
import java.util.List;

public class ListOfTasksService {
    private ListOfTasksRepository listOfTasksRepository = new ListOfTasksRepository();

    public List<Task> getTaskList(){
        try{
            List<Task> tasks = listOfTasksRepository.getUserTasks(CurrentUser.getUser().getId());
            if (tasks.size() == 0){
                MessageGenerator.setMessage("Error: Task list is empty.");
                return null;
            }
            else{
                return tasks;
            }
        }
        catch (ClassNotFoundException | SQLException e){
            e.printStackTrace();
            MessageGenerator.setMessage("Error: Tasks were not received.");
            return null;
        }

    }
}
