package com.backtake.simpleconsoleapp.helpers;

import com.backtake.simpleconsoleapp.user.User;
import com.backtake.simpleconsoleapp.user.UserRepository;
import com.backtake.simpleconsoleapp.view.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Validator {

    private UserRepository repository;
    private View view;

    @Autowired
    public Validator(UserRepository repository, View view) {
        this.repository = repository;
        this.view = view;
    }

    public boolean isLoginValid(String login) {
        List<User> users = (List<User>) repository.findAll();
        if (login.length()<3) {
            view.displayCreateInfoNotLongEnough();
            return false;
        }
        for(User u: users) {
            if(login.equals(u.getLogin())){
                view.displayCreateLoginInfoIfAlreadyExists();
                return false;
            }
        }
        return true;
    }

    public boolean isPasswordValid(String password) {
        if((password.length()>=8)&&(hasReqPattern(password))) {
            return true;
        }
        view.displayCreatePasswordNoReqCharactersInfo();
        return false;
    }

    private boolean hasReqPattern(String password) {
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}";
        return password.matches(pattern);
    }

    public boolean isEmailValid(String email) {
        if(hasEmailReqPattern(email)){
            return true;
        }
        view.displayCreateEmailNoReqCharactersInfo();
        return false;
    }

    public boolean hasEmailReqPattern(String email) {
        String pattern = "[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})";
        return email.matches(pattern);
    }

    public boolean isPhoneNumberValid(String phoneNumber) {
        String pattern = "(?=.*[0-9])(?=\\S+$).{9}";
        return phoneNumber.matches(pattern);
    }
}
