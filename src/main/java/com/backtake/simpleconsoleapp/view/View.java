package com.backtake.simpleconsoleapp.view;

import com.backtake.simpleconsoleapp.user.User;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class View {
    public void displayMenu() {
        System.out.println("Choose option: \n1) Log In \n2) Register \n0) Exit");
    }

    public void displayRegistrationMenu() {
        System.out.println("Enter: \n1) Login  \n2) Password \n3) Phone Number" +
                "\n4) Email" +
                " \n0) Back");
    }

    public void displayEnterLogin() {
        System.out.println("Enter login: ");
    }

    public void displayCreateLoginInfoIfAlreadyExists() {
        System.out.println("Login already exists");
    }

    public void displayEnterPasswordMenu() {
        System.out.println("Choose option: \n1) Generate password \n2) Provide password");
    }

    public void displayGeneratedPassword(String password) {
        System.out.println("Generated password for you is: \n" + password + "\n please keep it safe");
    }

    public void displayEnterPassword() {
        System.out.println("Enter password: ");
    }

    public void displayCreateInfoNotLongEnough() {
        System.out.println("Provided data is not long enough or it's characters are not allowed");
    }

    public void displayCreatePasswordNoReqCharactersInfo() {
        System.out.println("Password should be at least 8 characters long with: 1 capital letter, 1 number and 1 special character");
    }

    public void displayCreatePhoneNumber() {
        System.out.println("Enter phone number: ");
    }

    public void displayCreateEmail() {
        System.out.println("Enter email: ");
    }

    public void displayNotSavedInfo() {
        System.out.println("You have not provided necessary data for creating an account.\n" +
                "Account has not been saved");
    }

    public void displayByeByeInfo() {
        System.out.println("BYE BYE");
    }

    public void displayWrongLoginOrPasswordInfo() {
        System.out.println("Login or password you have provided is incorrect");
    }

    public void displayLoggedMenu(User user) {
        System.out.println("You are logged as: \n" + user.toString() + "\nChoose option: \n1) Change phone number \n2) Change email \n0) Log out");
    }

    public void displayCreateEmailNoReqCharactersInfo() {
        System.out.println("Provide correct email:");
    }
}
