package com.backtake.simpleconsoleapp.helpers.passwordutils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EncryptPassword {

    @Value("${salt}")
    private String salt;

    public String getSecuredPassword(String myPass) {
        return PasswordUtils.generateSecurePassword(myPass, salt);
    }
}
