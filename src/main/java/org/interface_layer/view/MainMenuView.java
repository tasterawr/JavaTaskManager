package org.interface_layer.view;

import java.util.Scanner;

public class MainMenuView {
    private final Scanner input = new Scanner(System.in);
    private final EditTasksView editTasksView = new EditTasksView();
    private final EditTasksListView editTasksListView = new EditTasksListView();
    private final TaskSearchView taskSearchView = new TaskSearchView();
    private final OverdueTasksView overdueTasksView = new OverdueTasksView();

    public void displayMainMenu(){
        boolean show = true;
        while(show){
            overdueTasksView.displayOverdueTasks();

            System.out.println();
            System.out.println("------MAIN MENU------");
            System.out.println("1. Edit tasks");
            System.out.println("2. Edit task lists");
            System.out.println("3. Search for task");
            System.out.println("4. Exit");

            int option = Integer.parseInt(input.nextLine());
            switch (option){
                case 1: displayEditTasksPage(); break;
                case 2: displayEditTaskListsPage(); break;
                case 3: displaySearchForTaskPage(); break;
                case 4: show = false;
                default: break;
            }
        }
    }

    private void displayEditTasksPage(){
        editTasksView.displayEditTasksPage();
    }

    private void displayEditTaskListsPage(){
        editTasksListView.displayEditTaskListsPage();
    }

    private void displaySearchForTaskPage(){

    }
}
