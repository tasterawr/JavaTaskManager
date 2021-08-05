package org;

import org.view.AuthorisationView;
import org.view.MainMenuView;

public class Main {
    public static void main(String[] args) {
        AuthorisationView authorisationView = new AuthorisationView();
        MainMenuView mainMenuView = new MainMenuView();
        //mainMenuView.displayMainMenu();
        authorisationView.displayAuthorisationMenu();
    }
}
