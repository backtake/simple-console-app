package com.backtake.simpleconsoleapp.helpers;

import com.backtake.simpleconsoleapp.user.User;
import com.backtake.simpleconsoleapp.user.UserRepository;
import com.backtake.simpleconsoleapp.view.View;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
}
