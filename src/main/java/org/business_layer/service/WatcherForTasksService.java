package org.business_layer.service;

import org.dao_layer.model.Task;
import org.dao_layer.repository.TaskRepository;
import org.exceptions.DAOException;
import org.exceptions.DomainException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WatcherForTasksService {
    private final TaskRepository taskRepository = new TaskRepository();
    private static List<Integer> shownTasksIds = new ArrayList<>();

    public Set<Task> getUserTasks(int userId){
        try {
            return taskRepository.getUserTasks(userId, "");
        }
        catch (DomainException | DAOException e){
            throw e;
        }
    }

    public List<Task> getOverdueTasks(int userId){
        try{
            Set<Task> userTasks = getUserTasks(userId);
            List<Task> overdueTasks = new ArrayList<>();

            for(Task task : userTasks){
                boolean isInTheList = false;

                for (Integer id : shownTasksIds){
                    if (id == task.getId()) {
                        isInTheList = true;
                    }
                }

                if (task.isAlertReceived() && !isInTheList) {
                    overdueTasks.add(task);
                    shownTasksIds.add(task.getId());
                }
            }

            return overdueTasks;
        }
        catch (DAOException e){
            throw new DomainException(e.getMessage(), e);
        }
        catch (DomainException e){
            throw e;
        }

    }
}
