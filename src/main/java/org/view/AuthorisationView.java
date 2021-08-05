package org.view;

import org.controller.UserController;
import org.model.User;

import java.util.Scanner;

public class AuthorisationView {
    private Scanner input = new Scanner(System.in);
    MainMenuView mainMenuView = new MainMenuView();
    UserController userController = new UserController();

    public void displayAuthorisationMenu(){
        System.out.println();
        System.out.println("------AUTHORISATION MENU------");
        System.out.println("1. Log In");
        System.out.println("2. Sing In");
        System.out.println("3. Exit");

        int option = Integer.parseInt(input.nextLine());
        switch (option){
            case 1: displayLogInPage(); break;
            case 2: displaySignInPage(); break;
            case 3: return;
            default: displayAuthorisationMenu();
        }
    }

    private void displayLogInPage(){
        System.out.println("------LOG IN------");
        System.out.println("Enter login: ");
        String login = input.nextLine();
        System.out.println("Enter password: ");
        String password = input.nextLine();
        User user = userController.getUserByLoginPass(login, password);
        if (user != null){
            System.out.println("Logged in successfully!");
            mainMenuView.displayMainMenu();
        }
        else{
            System.out.println("Error: Wrong login or password.");
            displayAuthorisationMenu();
        }

        return;
    }

    private void displaySignInPage(){
        System.out.println("------SIGN IN------");
        System.out.println("Enter username: ");
        String username = input.nextLine();
        System.out.println("Enter first name: ");
        String firstname = input.nextLine();
        System.out.println("Enter last name: ");
        String lastname = input.nextLine();
        System.out.println("Enter phone number: ");
        String phone = input.nextLine();

        boolean match = false;
        while(!match){
            System.out.println("Enter password: ");
            String password = input.nextLine();
            System.out.println("Enter password again: ");
            String passwordCheck = input.nextLine();

            if (passwordCheck.equals(password)){
                match = true;
                boolean result = userController.addNewUser(username,password,firstname,lastname,phone);
                if (result){
                    System.out.println("New user has been registered successfully.");
                    mainMenuView.displayMainMenu();
                }
                else{
                    System.out.println("Error: New user has not been registered.");
                    displayAuthorisationMenu();
                }
            }
            else{
                System.out.println("Passwords don't match: ");
            }
        }

    }
}
