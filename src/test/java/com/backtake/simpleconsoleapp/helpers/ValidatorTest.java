package com.backtake.simpleconsoleapp.helpers;

import com.backtake.simpleconsoleapp.user.UserRepository;
import com.backtake.simpleconsoleapp.view.View;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ValidatorTest {

    @Autowired
    Validator validator;

    @MockBean
    UserRepository userRepository;

    @MockBean
    View view;

    @Before
    public void setUp(){
        this.validator = new Validator(userRepository, view);
    }

    @Test
    public void isPasswordValidTestNotLongEnough() {
        assertFalse(validator.isPasswordValid("test"));
    }
}