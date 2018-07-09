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

    @Test
    public void isPasswordValidTestNoReqPattern() {
        assertFalse(validator.isPasswordValid("asdfghjkp"));
    }

    @Test
    public void isPasswordValidTestCorrectPassword() {
        assertTrue(validator.isPasswordValid("V@l1dpass"));
    }

    @Test
    public void isPhoneNumberValidTestCorrect() {
        assertTrue(validator.isPhoneNumberValid("111111111"));
    }

    @Test
    public void isPhoneNumberValidTestNotLongEnough() {
        assertFalse(validator.isPhoneNumberValid("21142"));
    }

    @Test
    public void isPhoneNumberValidTestTooLong() {
        assertFalse(validator.isPhoneNumberValid("11111111111111"));
    }

    @Test
    public void isPhoneNumberValidTestNotOnlyNumbers() {
        assertFalse(validator.isPhoneNumberValid("as2211331"));
    }

    @Test
    public void isPhoneNumberValidTestWhiteSpaces() {
        assertFalse(validator.isPhoneNumberValid("11 22 334"));
    }

    @Test
    public void isEmailValidTestWrongEmail1() {
        assertFalse(validator.isEmailValid("@.pl"));
    }

    @Test
    public void isEmailValidTestWrongEmail2() {
        assertFalse(validator.isEmailValid("aaaaa@."));
    }

    @Test
    public void isEmailValidTestWrongEmail3() {
        assertFalse(validator.isEmailValid("@aaaaaa.pl"));
    }

    @Test
    public void isEmailValidTestCorrect() {
        assertTrue(validator.isEmailValid("corect@email.pl"));
    }
}