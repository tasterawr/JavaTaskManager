package org.view;

import org.controller.ListOfTasksController;
import org.controller.TaskController;
import org.model.Task;
import org.utils.CurrentUser;
import org.utils.MessageGenerator;

import java.sql.Date;
import java.util.List;
import java.util.Scanner;

public class EditTasksView {
    private Scanner input = new Scanner(System.in);
    private TaskController taskController = new TaskController();
    private ListOfTasksController listOfTasksController = new ListOfTasksController();

    public void displayEditTasksPage(){
        boolean show = true;
        while(show) {
            System.out.println();
            System.out.println("------EDIT TASKS------");
            System.out.println("1. Show all tasks");
            System.out.println("2. Show task by id");
            System.out.println("3. Edit task by id");
            System.out.println("4. Add new task");
            System.out.println("5. Delete task by id");
            System.out.println("6. Back to main menu");

            int option = Integer.parseInt(input.nextLine());
            switch (option) {
                case 1:
                    showAllTasks();
                    break;
                case 2:
                    showTaskById();
                    break;
                case 3:
                    editTaskById();
                    break;
                case 4:
                    addNewTask();
                    break;
                case 5:
                    deleteTaskById();
                    break;
                case 6:
                    show = false;
                default:
                    displayEditTasksPage();
            }
        }
    }

    private void showAllTasks(){
        System.out.println("------TASKS FOR " + CurrentUser.getUser().getUsername() + "------");
        List<Task> tasks = listOfTasksController.getTaskList();
        if (tasks == null)
            System.out.println(MessageGenerator.getMessage());
        else{
            for (Task task : tasks)
                System.out.println(task);
        }
    }

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
        Task task = null;
        while(show) {
            System.out.println("Enter id:");
            try {
                int id = Integer.parseInt(input.nextLine());
                task = taskController.getTaskById(id);
                show = false;
            } catch (NumberFormatException e) {
                System.out.println("Error: input is not an id.");
            }
        }

        show = true;
        while(show){
            System.out.println("    ------EDIT TASK------");
            System.out.println("    1. Change task name");
            System.out.println("    2. Change task description");
            System.out.println("    3. Change task alert time");
            System.out.println("    4. Back to task menu");

            int option = Integer.parseInt(input.nextLine());
            switch (option){
                case 1: changeTaskNamePage(task); break;
                case 2: changeTaskDescrPage(task); break;
                case 3: changeTaskAlertTimePage(task); break;
                case 4: return;
                default: continue;
            }
        }
    }

    private void changeTaskNamePage(Task task){
        System.out.println("Enter new task name: ");
        String newTaskName = input.nextLine();
        taskController.changeTaskInfo(task, "name", newTaskName);
        System.out.println("Update successful.");
    }

    private void changeTaskDescrPage(Task task){
        System.out.println("Enter new task description: ");
        String newTaskDescription = input.nextLine();
        taskController.changeTaskInfo(task, "description", newTaskDescription);
        System.out.println("Update successful.");
    }

    private void changeTaskAlertTimePage(Task task){
        System.out.println("Enter new task alert time: ");
        String newAlertTime = input.nextLine();
        taskController.changeTaskInfo(task, "alertTime", newAlertTime);
        System.out.println("Update successful.");
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
                Date date = Date.valueOf(alertDate);
                taskController.addNewTask(taskName, taskDescription, alertDate);
                show = false;
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Invalid date format.");
            }
        }

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
                    if (confirm == "yes"){
                        taskController.deleteTaskById(id);
                        innerShow = false;
                    }
                    else if (confirm == "no")
                        return;
                    else continue;

                }
                show = false;
            } catch (NumberFormatException e) {
                System.out.println("Error: input is not an id.");
            }
        }
    }
}
