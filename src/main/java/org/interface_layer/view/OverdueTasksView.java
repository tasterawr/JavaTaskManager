package org.interface_layer.view;

import org.dao_layer.model.Task;
import org.interface_layer.controller.WatcherForTasksController;
import org.utils.CurrentUser;

import java.util.List;
import java.util.Scanner;

public class OverdueTasksView {
    private final WatcherForTasksController watcherForTasksController = new WatcherForTasksController();
    private final Scanner input = new Scanner(System.in);

    public void displayOverdueTasks(){
        List<Task> overdueTasks = watcherForTasksController.getOverdueTasks(CurrentUser.getUser().getId());

        if (overdueTasks.size() != 0){
            System.out.println("!-----You have overdue tasks!-----!");
            for (Task task : overdueTasks){
                System.out.println(task);
            }

            System.out.println("Enter anything to continue...");
            input.nextLine();
        }
    }
}
