package org.view;

import java.util.Scanner;

public class MainMenuView {
    private Scanner input = new Scanner(System.in);
    private EditTasksView editTasksView = new EditTasksView();
    private EditTasksListView editTasksListView = new EditTasksListView();
    private TaskSearchView taskSearchView = new TaskSearchView();

    public void displayMainMenu(){
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
            case 4: return;
            default: displayMainMenu();
        }
    }

    private void displayEditTasksPage(){
        editTasksView.displayEditTasksPage();
    }

    private void displayEditTaskListsPage(){

    }

    private void displaySearchForTaskPage(){

    }
}
