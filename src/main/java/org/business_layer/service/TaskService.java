package org.business_layer.service;

import org.exceptions.DAOException;
import org.exceptions.DomainException;
import org.dao_layer.model.Task;
import org.dao_layer.model.TaskList;
import org.dao_layer.repository.TaskRepository;
import org.utils.CurrentUser;

import java.sql.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class TaskService {
    TaskRepository taskRepository = new TaskRepository();
    TaskListService taskListService = new TaskListService();
    ListOfTasksService listOfTasksService = new ListOfTasksService();

    public Set<Task> getAllUserTasks(int userId){
        try {
            return taskRepository.getUserTasks(userId, "");
        }
        catch (DAOException e){
            throw new DomainException(e.getMessage(), e);
        }
    }

    public Task getTaskById(int id){
        try{
            return taskRepository.getEntity(id);
        }
        catch (DAOException e){
            throw new DomainException(e.getMessage(), e);
        }
    }

    public Set<Task> searchForTasks(String criteria, String inputData){
        Set<Task> tasks = new HashSet<>();

        try{
            if (criteria.equals("name")){
                String [] nameWords = inputData.split(" ");
                for (String nameWord : nameWords){
                    String condition = "WHERE LOWER(task.name) LIKE \'%" + nameWord.toLowerCase(Locale.ROOT) + "%\'";
                    Set<Task> taskSet = taskRepository.getUserTasks(CurrentUser.getUser().getId(), condition);
                    for (Task t : taskSet)
                        tasks.add(t);
                }
            }
            else if (criteria.equals("description")){
                String [] descriptionWords = inputData.split(" ");
                for (String word : descriptionWords){
                    String condition = "WHERE LOWER(task.description) LIKE \'%" + word.toLowerCase(Locale.ROOT) + "%\'";
                    Set<Task> taskSet = taskRepository.getUserTasks(CurrentUser.getUser().getId(), condition);
                    for (Task t : taskSet)
                        tasks.add(t);
                }
            }
            else if (criteria.equals("alert_time")){
                String condition = "WHERE task.alert_time = \'" + inputData + "\'";
                Set<Task> taskSet = taskRepository.getUserTasks(CurrentUser.getUser().getId(), condition);
                for (Task t : taskSet)
                    tasks.add(t);
            }
            else if (criteria.equals("alert_time_range")){
                String[] dates = inputData.split(" ");
                String condition = "WHERE task.alert_time BETWEEN \'" + dates[0] + "\' AND \'" + dates[1] + "\'";
                Set<Task> taskSet = taskRepository.getUserTasks(CurrentUser.getUser().getId(), condition);
                for (Task t : taskSet)
                    tasks.add(t);
            }
            else if (criteria.equals("deadline")){
                String condition = "WHERE task.alert_time - CURRENT_DATE BETWEEN 0 AND " + inputData;
                Set<Task> taskSet = taskRepository.getUserTasks(CurrentUser.getUser().getId(), condition);
                for (Task t : taskSet)
                    tasks.add(t);
            }

            return tasks;
        }
        catch (DAOException e){
            throw new DomainException(e.getMessage(), e);
        }
    }

    public void changeTaskInfo(int id, String mode, String newValue){
        try{
            Task task = taskRepository.getEntity(id);
            if (mode.equals("name")){
                task.setName(newValue);
            }
            else if (mode.equals("description")){
                task.setDescription(newValue);
            }
            else if (mode.equals("alert_time")){
                task.setAlertTime(Date.valueOf(newValue));
            }

            taskRepository.update(task);
        }
        catch (DAOException e){
            throw new DomainException(e.getMessage(), e);
        }

    }

    public void addNewTask(String taskName, String description, String alertTime){
        Task task = new Task();
        task.setName(taskName);
        task.setDescription(description);
        task.setAlertTime(Date.valueOf(alertTime));
        task.setAlertReceived();
        try{
            taskRepository.create(task);
            TaskList taskList = taskListService.getTaskListByName(String.format("%s default task list", CurrentUser.getUser().getUsername()));
            listOfTasksService.addToListById(taskList.getId(), task.getId());
        }
        catch(DAOException e){
            throw new DomainException(e.getMessage(), e);
        }
        catch (DomainException e){
            throw e;
        }
    }

    public void deleteTaskById(int id){
        try{
            taskRepository.delete(id);
        }
        catch (DAOException e){
            throw new DomainException(e.getMessage(), e);
        }
    }
}
