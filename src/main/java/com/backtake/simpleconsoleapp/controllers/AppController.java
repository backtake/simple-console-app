package com.backtake.simpleconsoleapp.controllers;

import com.backtake.simpleconsoleapp.helpers.Validator;
import com.backtake.simpleconsoleapp.helpers.passwordutils.EncryptPassword;
import com.backtake.simpleconsoleapp.helpers.passwordutils.VerifyUserPassword;
import com.backtake.simpleconsoleapp.user.User;
import com.backtake.simpleconsoleapp.user.UserRepository;
import com.backtake.simpleconsoleapp.view.View;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Scanner;

public class AppController {

    private View view;
    private UserRepository repository;
    private Validator validator;
    private EncryptPassword encryptPassword;
    private VerifyUserPassword verifyUserPassword;

    @Autowired
    public AppController(View view, UserRepository repository, Validator validator, EncryptPassword encryptPassword,
                         VerifyUserPassword verifyUserPassword) {
        this.view = view;
        this.repository = repository;
        this.validator = validator;
        this.encryptPassword = encryptPassword;
        this.verifyUserPassword = verifyUserPassword;
    }

    public void runApp() {
        boolean isRunning = true;
        Scanner scanner = new Scanner(System.in);

        while(isRunning) {
            view.displayMenu();
            switch (getInput(scanner)) {
                case "1":
                    logIn(scanner);
                    break;
                case "2":
                    registerUser(scanner);
                    break;
                case "666":
                    listAllUsers();
                    break;
                case "0":
                    view.displayByeByeInfo();
                    isRunning = false;
                    break;
            }
        }
    }

    private void logIn(Scanner scanner) {
        User user = getLoggedUser(scanner);
        if(user!=null) {
            goToLoggedMenu(scanner, user);
        } else {
            view.displayWrongLoginOrPasswordInfo();
        }
    }

    private User getLoggedUser(Scanner scanner) {
        view.displayEnterLogin();
        String login = getInput(scanner);
        view.displayEnterPassword();
        String password = getInput(scanner);

        return (verifyLoginInfo(login,password)) ? repository.findByLogin(login).get() : null;
    }

    private boolean verifyLoginInfo(String login, String password) {
        return repository.findByLogin(login).isPresent()&&verifyUserPassword.verifyUserPassword(login, password);
    }


    private String getInput(Scanner scanner){
        return scanner.nextLine();
    }
}