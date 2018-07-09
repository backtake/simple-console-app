package com.backtake.simpleconsoleapp.helpers.passwordutils;

import com.backtake.simpleconsoleapp.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class VerifyUserPassword {

    @Value("${salt}")
    private String salt;
    private UserRepository userRepository;

    @Autowired
    public VerifyUserPassword(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean verifyUserPassword(String login, String passwordToCheck) {
        String securedPass = userRepository.findByLogin(login).get().getPassword();
        return PasswordUtils.verifyUserPassword(passwordToCheck, securedPass, salt);
    }
}
