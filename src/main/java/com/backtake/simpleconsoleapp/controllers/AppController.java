package com.backtake.simpleconsoleapp.controllers;

import com.backtake.simpleconsoleapp.helpers.Validator;
import com.backtake.simpleconsoleapp.helpers.passwordutils.EncryptPassword;
import com.backtake.simpleconsoleapp.helpers.passwordutils.PasswordGenerator;
import com.backtake.simpleconsoleapp.helpers.passwordutils.VerifyUserPassword;
import com.backtake.simpleconsoleapp.user.User;
import com.backtake.simpleconsoleapp.user.UserRepository;
import com.backtake.simpleconsoleapp.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Scanner;

@Service
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

    private String getInput(Scanner scanner){
        return scanner.nextLine();
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

    private void goToLoggedMenu(Scanner scanner, User user) {
        boolean isRunning = true;

        while(isRunning) {
            view.displayLoggedMenu(user);

            switch (getInput(scanner)) {
                case "1":
                    user.setPhoneNumber(enterPhoneNumber(scanner));
                    break;
                case "2":
                    user.setEmail(enterEmail(scanner));
                    break;
                case "0":
                    isRunning = false;
                    break;
            }
        }
        repository.save(user);
    }

    private void registerUser(Scanner scanner) {
        boolean isRunning = true;
        User user = new User();

        while(isRunning) {
            view.displayRegistrationMenu();
            switch (getInput(scanner)) {
                case "1":
                    user.setLogin(enterLogin(scanner));
                    break;
                case "2":
                    user.setPassword(generateSecuredPassword(enterPassword(scanner)));
                    break;
                case "3":
                    user.setPhoneNumber(enterPhoneNumber(scanner));
                    break;
                case "4":
                    user.setEmail(enterEmail(scanner));
                    break;
                case "0":
                    isRunning = false;
                    break;
            }
        }
        try {
            saveToDbIfProvidedDataCorrect(user);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void saveToDbIfProvidedDataCorrect(User user) throws IllegalAccessException {
        Field[] attributes = user.getClass().getDeclaredFields();

        for(Field attribute: attributes) {
            attribute.setAccessible(true);
            if(attribute.getType().getName().equals("java.lang.String")&&attribute.get(user)==null) {
                view.displayNotSavedInfo();
                return;
            }
        }
        repository.save(user);
    }

    private String enterEmail(Scanner scanner) {
        String email;
        view.displayCreateEmail();

        while(true) {
            email = getInput(scanner);
            if(validator.isEmailValid(email)) {
                return email;
            }
        }
    }

    private String enterPhoneNumber(Scanner scanner) {
        String phoneNumber;
        view.displayCreatePhoneNumber();

        while(true) {
            phoneNumber = getInput(scanner);
            if(validator.isPhoneNumberValid(phoneNumber)) {
                return phoneNumber;
            }
            view.displayCreateInfoNotLongEnough();
        }
    }

    private String enterPassword(Scanner scanner) {

        while(true) {
            view.displayEnterPasswordMenu();
            switch (getInput(scanner)) {
                case "1":
                    return generatePassword();
                case "2":
                    return providePassword(scanner);
            }
        }
    }

    private String providePassword(Scanner scanner) {
        String password;
        view.displayEnterPassword();

        while(true) {
            password = getInput(scanner);
            if(validator.isPasswordValid(password)) {
                return password;
            }
        }
    }

    private String generatePassword() {
        String password;
        PasswordGenerator passwordGenerator = new PasswordGenerator.PasswordGeneratorBuilder()
                .useDigits(true)
                .useLower(true)
                .useUpper(true)
                .usePunctuation(true)
                .build();
        password = passwordGenerator.generate(8);
        view.displayGeneratedPassword(password);
        return password;
    }

    private String generateSecuredPassword(String password) {
        return encryptPassword.getSecuredPassword(password);
    }

    private String enterLogin(Scanner scanner) {
        String login;
        view.displayEnterLogin();

        while(true) {
            login = getInput(scanner);
            if(validator.isLoginValid(login)) {
                return login;
            }
        }
    }

    private void listAllUsers() {
        ((List<User>) repository.findAll()).stream().map(User::toString).forEach(System.out::println);
    }
}
