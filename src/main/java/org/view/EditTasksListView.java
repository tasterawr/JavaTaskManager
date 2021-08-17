package org.view;

import org.controller.ListOfTasksController;
import org.controller.TaskListController;
import org.exceptions.InterfaceException;
import org.model.Task;
import org.model.TaskList;
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

    }

    private void addNewTaskList(){

    }

    private void deleteTaskListById(){

    }
}
