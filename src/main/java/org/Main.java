package org;

import org.interface_layer.view.AuthorisationView;
import org.interface_layer.view.MainMenuView;

public class Main {
    public static void main(String[] args) {
        AuthorisationView authorisationView = new AuthorisationView();
        MainMenuView mainMenuView = new MainMenuView();
        //mainMenuView.displayMainMenu();
        authorisationView.displayAuthorisationMenu();
    }
}
