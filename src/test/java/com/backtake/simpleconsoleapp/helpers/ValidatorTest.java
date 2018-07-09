package com.backtake.simpleconsoleapp.helpers;

import com.backtake.simpleconsoleapp.user.User;
import com.backtake.simpleconsoleapp.user.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ValidatorTest {

    @InjectMocks
    Validator validator;

    @Mock
    UserRepository userRepository;

    User user;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        this.validator = new Validator(userRepository);
        user = new User();
        user.setLogin("Jan");
        List<User> users = new ArrayList<>();
        users.add(user);
        Mockito.when(userRepository.findAll())
                .thenReturn(users);
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

    @Test
    public void isLoginValidTestTooShort() {
        assertFalse(validator.isLoginValid("ja"));
    }

    @Test
    public void isLoginValidTestNotUnique() {
        assertFalse(validator.isLoginValid("Jan"));
    }

    @Test
    public void isLoginValidTestCorrect() {
        assertTrue(validator.isLoginValid("Piotr"));
    }
}