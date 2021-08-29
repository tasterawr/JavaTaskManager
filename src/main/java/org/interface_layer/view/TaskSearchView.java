package org.interface_layer.view;

import org.dao_layer.model.Task;
import org.exceptions.InterfaceException;
import org.interface_layer.controller.TaskController;

import java.sql.Date;
import java.util.Scanner;
import java.util.Set;

public class TaskSearchView {
    private final Scanner input = new Scanner(System.in);
    private final OverdueTasksView overdueTasksView = new OverdueTasksView();
    private final TaskController taskController = new TaskController();

    public void displaySearchMenu(){
        boolean show = true;
        while(show){
            overdueTasksView.displayOverdueTasks();

            System.out.println();
            System.out.println("------SEARCH FOR TASKS------");
            System.out.println("1. Search for tasks by name");
            System.out.println("2. Search for tasks by description");
            System.out.println("3. Search for tasks by alert time (date)");
            System.out.println("4. Search for tasks by alert time range");
            System.out.println("5. Search for tasks by deadline range");
            System.out.println("6. Back to main menu");

            int option = Integer.parseInt(input.nextLine());
            if  (option == 1){
                searchByNamePage();
            }
            else if (option == 2){
                searchByDescriptionPage();
            }

            else if (option == 3){
                searchByAlertTimePage();
            }
            else if (option == 4){
                searchByAlertTimeRangePage();
            }
            else if (option == 5){
                searchByDeadlinePage();
            }
            else if (option == 6){
                show = false;
            }
        }
    }

    private void searchByNamePage() {
        System.out.println("Enter name words to search tasks by: ");
        String inputWords = input.nextLine();

        try{
            Set<Task> tasks = taskController.searchForTasks("name", inputWords);
            System.out.println("------SEARCH RESULTS------");
            for (Task t : tasks)
                System.out.println(t);
        }
        catch (InterfaceException e){
            System.out.println(e.getMessage());
        }
    }

    private void searchByDescriptionPage() {
        System.out.println("Enter description words to search tasks by: ");
        String inputWords = input.nextLine();

        try{
            Set<Task> tasks = taskController.searchForTasks("description", inputWords);
            System.out.println("------SEARCH RESULTS------");
            for (Task t : tasks)
                System.out.println(t);
        }
        catch (InterfaceException e){
            System.out.println(e.getMessage());
        }
    }

    private void searchByAlertTimePage() {
        boolean isNotValid = true;
        String dateString = "";
        while (isNotValid){
            try{
                System.out.println("Enter alert time (date) to search tasks by: ");
                dateString = input.nextLine();
                Date.valueOf(dateString);
                isNotValid = false;
            }
            catch (IllegalArgumentException e){
                System.out.println("Error: Input alert time is not valid.");
            }
        }

        try{
            Set<Task> tasks = taskController.searchForTasks("alert_time", dateString);
            System.out.println("------SEARCH RESULTS------");
            for (Task t : tasks)
                System.out.println(t);
        }
        catch (InterfaceException e){
            System.out.println(e.getMessage());
        }
    }

    private void searchByAlertTimeRangePage() {
        boolean isNotValid = true;
        String dateString1 = "";
        String dateString2 = "";
        while (isNotValid){
            try{
                System.out.println("Enter first alert time (date) of the search range: ");
                dateString1 = input.nextLine();
                System.out.println("Enter second alert time (date) of the search range: ");
                dateString2 = input.nextLine();
                Date.valueOf(dateString1);
                Date.valueOf(dateString2);
                isNotValid = false;
            }
            catch (IllegalArgumentException e){
                System.out.println("Error: Input alert time is not valid.");
            }
        }

        try{
            Set<Task> tasks = taskController.searchForTasks("alert_time_range", dateString1 + " " + dateString2);
            System.out.println("------SEARCH RESULTS------");
            for (Task t : tasks)
                System.out.println(t);
        }
        catch (InterfaceException e){
            System.out.println(e.getMessage());
        }
    }

    private void searchByDeadlinePage() {
        boolean isNotValid = true;
        String numOfDays = "";
        while (isNotValid){
            try {
                System.out.println("Enter number of days of overdue period: ");
                numOfDays = input.nextLine();
                Integer.parseInt(numOfDays);
                isNotValid = false;
            }
            catch (NumberFormatException e){
                System.out.println("Error: Input is not a number.");
            }
        }

        try{
            Set<Task> tasks = taskController.searchForTasks("deadline", numOfDays);
            System.out.println("------TASKS WHICH WILL OVERDUE IN LESS THAN " + numOfDays + " DAYS------");
            for (Task t : tasks)
                System.out.println(t);
        }
        catch (InterfaceException e){
            System.out.println(e.getMessage());
        }
    }
}
