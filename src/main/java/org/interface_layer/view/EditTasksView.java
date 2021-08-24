package org.interface_layer.view;

import org.exceptions.DomainException;
import org.interface_layer.controller.ListOfTasksController;
import org.interface_layer.controller.TaskController;
import org.exceptions.InterfaceException;
import org.dao_layer.model.Task;
import org.interface_layer.controller.TaskListController;
import org.utils.CurrentUser;
import org.utils.MessageGenerator;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class EditTasksView {
    private final Scanner input = new Scanner(System.in);
    private final TaskController taskController = new TaskController();
    private final ListOfTasksController listOfTasksController = new ListOfTasksController();

    public void displayEditTasksPage(){
        boolean show = true;
        while(show) {
            System.out.println();
            System.out.println("------EDIT TASKS------");
            System.out.println("1. Show all user tasks");
            System.out.println("2. Show task by id");
            System.out.println("3. Edit task by id");
            System.out.println("4. Add new task");
            System.out.println("5. Delete task by id");
            System.out.println("6. Back to main menu");

            int option = Integer.parseInt(input.nextLine());
            if (option == 1){
                showAllTasks();
            }
            else if (option == 2){
                showTaskById();
            }
            else if (option == 3){
                editTaskById();
            }
            else if (option == 4){
                addNewTask();
            }
            else if (option == 5){
                deleteTaskById();
            }
            else if (option == 6){
                show = false;
            }
        }
    }

    private void showAllTasks(){
        System.out.println("------TASKS FOR " + CurrentUser.getUser().getUsername() + "------");
        try{
            List<Task> tasks = taskController.getAllUserTasks();
            if (tasks.size() == 0){
                System.out.println();
            }
            else
                for (Task t : tasks)
                    System.out.println(t);
        }
        catch (InterfaceException e){
            System.out.println(e.getMessage());
        }
    }

//    private void showAllTasks(){
//        System.out.println("------TASKS FOR " + CurrentUser.getUser().getUsername() + "------");
//        System.out.println("Enter task list ID:");
//        int id = Integer.parseInt(input.nextLine());
//        try{
//            List<Task> tasks = listOfTasksController.getTaskList(id);
//            if (tasks.size() == 0){
//                System.out.println("");
//            }
//            else
//                for (Task t : tasks)
//                    System.out.println(t);
//        }
//        catch (InterfaceException e){
//            System.out.println(e.getMessage());
//        }
//    }

    private void showTaskById(){
        boolean show = true;
        while(show){
            System.out.println("Enter id:");
            try{
                int id = Integer.parseInt(input.nextLine());
                System.out.println(taskController.getTaskById(id));
                show = false;
            }
            catch (NumberFormatException e){
                System.out.println("Error: input is not an id.");
            }
        }
    }

    private void editTaskById(){

        boolean show = true;
        while(show){
            System.out.println("    ------EDIT TASK------");
            System.out.println("    1. Change task name");
            System.out.println("    2. Change task description");
            System.out.println("    3. Change task alert time");
            System.out.println("    4. Move task to other list");
            System.out.println("    5. Back to task menu");

            int option = Integer.parseInt(input.nextLine());
            if (option == 1){
                changeTaskNamePage();
            }
            else if (option == 2){
                changeTaskDescrPage();
            }
            else if (option == 3) {
                changeTaskAlertTimePage();
            }
            else if (option == 4) {
                setNewTaskList();
            }
            else if (option == 5){
                show = false;
            }
        }
    }

    private void changeTaskNamePage(){
        boolean show = true;
        int id = -1;
        while(show) {
            System.out.println("Enter id:");
            try {
                id = Integer.parseInt(input.nextLine());
                show = false;
            } catch (NumberFormatException e) {
                System.out.println("Error: input is not an id.");
            }
        }

        System.out.println("Enter new task name: ");
        String newTaskName = input.nextLine();
        try{
            taskController.changeTaskInfo(id, "name", newTaskName);
            System.out.println("Update successful.");
        }
        catch (InterfaceException e){
            System.out.println(e.getMessage());
        }


    }

    private void changeTaskDescrPage(){
        boolean show = true;
        int id = -1;
        while(show) {
            System.out.println("Enter id:");
            try {
                id = Integer.parseInt(input.nextLine());
                show = false;
            } catch (NumberFormatException e) {
                System.out.println("Error: input is not an id.");
            }
        }

        System.out.println("Enter new task description: ");
        String newTaskDescription = input.nextLine();
        try{
            taskController.changeTaskInfo(id, "description", newTaskDescription);
            System.out.println("Update successful.");
        }
        catch (InterfaceException e){
            System.out.println(e.getMessage());
        }
    }

    private void changeTaskAlertTimePage(){
        boolean show = true;
        int id = -1;
        while(show) {
            System.out.println("Enter id:");
            try {
                id = Integer.parseInt(input.nextLine());
                show = false;
            } catch (NumberFormatException e) {
                System.out.println("Error: input is not an id.");
            }
        }

        show = true;
        String newAlertTime = null;
        while(show){
            System.out.println("Enter new task alert time: ");
            newAlertTime = input.nextLine();
            try{
                Date.valueOf(newAlertTime);
                show = false;
            }
            catch (IllegalArgumentException e){
                System.out.println("Error: Invalid date format.");
            }
        }

        try{
            taskController.changeTaskInfo(id, "alert_time", newAlertTime);
            System.out.println("Update successful.");
        }
        catch (InterfaceException e){
            System.out.println(e.getMessage());
        }
    }

    private void setNewTaskList(){
        boolean isNotValid = true;
        int taskId = -1;
        int newListId = -1;
        while(isNotValid) {
            System.out.println("Enter task id:");
            try {
                taskId = Integer.parseInt(input.nextLine());
                isNotValid = false;
            } catch (NumberFormatException e) {
                System.out.println("Error: input is not an id.");
            }
        }

        isNotValid = true;
        while(isNotValid) {
            System.out.println("Enter new task list id:");
            try {
                newListId = Integer.parseInt(input.nextLine());
                isNotValid = false;
            } catch (NumberFormatException e) {
                System.out.println("Error: input is not an id.");
            }
        }

        try{
            listOfTasksController.changeTaskList(taskId, newListId);
            System.out.println("Task was moved successfully!");
        }
        catch (DomainException e){
            System.out.println(e.getMessage());
        }
    }

    private void addNewTask(){
        System.out.println("Enter task name: ");
        String taskName = input.nextLine();
        System.out.println("Enter task description: ");
        String taskDescription = input.nextLine();
        boolean show = true;
        while(show) {
            System.out.println("Enter task alert date (yyyy-mm-dd): ");
            String alertDate = input.nextLine();
            try {
                Date.valueOf(alertDate);
                taskController.addNewTask(taskName, taskDescription, alertDate);
                MessageGenerator.setMessage("New task added successfully.");
                show = false;
            } catch (IllegalArgumentException e) {
                MessageGenerator.setMessage("Error: Invalid date format.");
            }
            catch (InterfaceException e){
                MessageGenerator.setMessage(e.getMessage());
                show = false;
            }
        }

        System.out.println(MessageGenerator.getMessage());

    }

    private void deleteTaskById(){
        boolean show = true;
        while(show){
            System.out.println("Enter task id: ");
            try {
                int id = Integer.parseInt(input.nextLine());
                boolean innerShow = true;
                while(innerShow){
                    System.out.println("Confirm deletion (yes/no): ");
                    String confirm = input.nextLine();
                    if (confirm.equals("yes")){
                        taskController.deleteTaskById(id);
                        innerShow = false;
                    }
                    else if (confirm.equals("no"))
                        return;
                }
                show = false;
            } catch (NumberFormatException e) {
                System.out.println("Error: input is not an id.");
            }
        }
    }
}
