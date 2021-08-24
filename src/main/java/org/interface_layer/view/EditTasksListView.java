package org.interface_layer.view;

import org.interface_layer.controller.ListOfTasksController;
import org.interface_layer.controller.TaskListController;
import org.exceptions.InterfaceException;
import org.dao_layer.model.Task;
import org.dao_layer.model.TaskList;
import org.utils.CurrentUser;

import java.util.List;
import java.util.Scanner;

public class EditTasksListView {
    private final Scanner input = new Scanner(System.in);
    private final TaskListController taskListController = new TaskListController();
    private final ListOfTasksController listOfTasksController = new ListOfTasksController();

    public void displayEditTaskListsPage(){
        boolean show = true;
        while(show) {
            System.out.println();
            System.out.println("------EDIT TASK LISTS------");
            System.out.println("1. Show all user task lists");
            System.out.println("2. Show task list by id");
            System.out.println("3. Edit task list by id");
            System.out.println("4. Add new task list");
            System.out.println("5. Delete task list by id");
            System.out.println("6. Back to main menu");

            int option = Integer.parseInt(input.nextLine());
            switch (option) {
                case 1:
                    showUserTaskLists();
                    break;
                case 2:
                    showTaskListById();
                    break;
                case 3:
                    editTaskListById();
                    break;
                case 4:
                    addNewTaskList();
                    break;
                case 5:
                    deleteTaskListById();
                    break;
                case 6:
                    show = false;
                default:
                    break;
            }
        }
    }

    private void showUserTaskLists(){
        System.out.println(String.format("------TASK LISTS FOR %s------", CurrentUser.getUser().getUsername()));
        try{
            List<TaskList> taskLists = taskListController.getListsForUser();
            for (TaskList taskList : taskLists){
                System.out.println(taskList);
            }
        }
        catch (InterfaceException e){
            System.out.println(e.getMessage());
        }
    }

    private void showTaskListById(){
        boolean show = true;
        int id = -1;
        while(show){
            try{
                System.out.println("Enter list id: ");
                id = Integer.parseInt(input.nextLine());
                show = false;
            }
            catch(NumberFormatException e){
                System.out.println("Error: input is not an id.");
            }
        }

        try{
            TaskList tasklist = taskListController.getTaskList(id);
            List<Task> tasks = listOfTasksController.getListOfTasks(id);
            System.out.println(String.format("------%s------",tasklist.getName()));
            for (Task task : tasks)
                System.out.println(task);
        }
        catch (InterfaceException e){
            System.out.println(e.getMessage());
        }
    }

    private void editTaskListById(){
        boolean show = true;
        while(show){
            System.out.println("------EDIT TASK LIST------");
            System.out.println("1. Change task list name");
            System.out.println("2. Back to main menu");

            try{
                int option = Integer.parseInt(input.nextLine());
                if (option == 1){
                    changeTaskListName();
                }
                else if (option == 2){
                    show = false;
                }
            }
            catch (NumberFormatException e){
                continue;
            }
        }
    }

    private void changeTaskListName(){
        boolean show = true;
        int id = -1;
        while(show) {
            System.out.println("Enter task list id:");
            try {
                id = Integer.parseInt(input.nextLine());
                show = false;
            } catch (NumberFormatException e) {
                System.out.println("Error: input is not an id.");
            }
        }

        System.out.println("Enter new task list name: ");
        String newTaskListName = input.nextLine();
        try{
            taskListController.updateTaskList(id, "name", newTaskListName);
            System.out.println("Update successful.");
        }
        catch (InterfaceException e){
            System.out.println(e.getMessage());
        }
    }

    private void addNewTaskList(){
        System.out.println("Enter name of a new task list:");
        String taskListName = input.nextLine();
        try{
            taskListController.addTaskList(taskListName);
            System.out.println("New task list was created successfully!");
        }
        catch (InterfaceException e){
            System.out.println(e.getMessage());
        }
    }

    private void deleteTaskListById(){
        System.out.println("Enter id of a task list to delete:");
        int id = -1;
        boolean isNotValid = true;
        while(isNotValid){
            try{
                id = Integer.parseInt(input.nextLine());
                isNotValid = false;
            }
            catch (NumberFormatException e){
                System.out.println("Not a valid id.");
            }
        }

        try{
            taskListController.deleteTaskList(id);
            System.out.println("Task list was deleted successfully!");
        }
        catch (InterfaceException e){
            System.out.println(e.getMessage());
        }
    }
}
