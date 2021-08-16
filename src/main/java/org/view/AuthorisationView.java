package org.view;

import org.controller.UserController;
import org.exceptions.InterfaceException;
import org.model.User;
import org.utils.MessageGenerator;

import java.util.Scanner;

public class AuthorisationView {
    private Scanner input = new Scanner(System.in);
    MainMenuView mainMenuView = new MainMenuView();
    UserController userController = new UserController();

    public void displayAuthorisationMenu(){
        boolean show = true;
        while(show){
            System.out.println();
            System.out.println("------AUTHORISATION MENU------");
            System.out.println("1. Log In");
            System.out.println("2. Sing In");
            System.out.println("3. Delete User");
            System.out.println("4. Exit");

            int option = Integer.parseInt(input.nextLine());
            switch (option){
                case 1: displayLogInPage(); break;
                case 2: displaySignInPage(); break;
                case 3: displayDeleteUserPage(); break;
                case 4: show = false;
                default: displayAuthorisationMenu();
            }
        }
    }

    private void displayLogInPage(){
        System.out.println("------LOG IN------");
        System.out.println("Enter login: ");
        String login = input.nextLine();
        System.out.println("Enter password: ");
        String password = input.nextLine();
        try{
            User user = userController.getUserByLoginPass(login, password);
            MessageGenerator.setMessage("Logged in successfully!");
            System.out.println(MessageGenerator.getMessage());
            mainMenuView.displayMainMenu();
        }
        catch(InterfaceException e){
            MessageGenerator.setMessage(e.getMessage());
            System.out.println(MessageGenerator.getMessage());
        }
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
                try{
                    userController.addNewUser(username,password,firstname,lastname,phone);
                    MessageGenerator.setMessage("New user signed in successfully.");
                }
                catch(InterfaceException e){
                    MessageGenerator.setMessage(e.getMessage());
                }

                System.out.println(MessageGenerator.getMessage());
                displayAuthorisationMenu();
            }
            else{
                System.out.println("Passwords don't match: ");
            }
        }

    }

    private void displayDeleteUserPage(){
        System.out.println("------DELETE USER------");
        System.out.println("Enter username: ");
        String username = input.nextLine();

        boolean match = false;
        while(!match){
            System.out.println("Enter password: ");
            String password = input.nextLine();
            System.out.println("Enter password again: ");
            String passwordCheck = input.nextLine();

            if (passwordCheck.equals(password)){
                match = true;
                boolean result = userController.deleteUser(username, password);
                System.out.println(MessageGenerator.getMessage());
                if (result){
                    mainMenuView.displayMainMenu();
                }
                else{
                    displayAuthorisationMenu();
                }
            }
            else{
                System.out.println("Passwords don't match.");
            }
        }
    }
}
