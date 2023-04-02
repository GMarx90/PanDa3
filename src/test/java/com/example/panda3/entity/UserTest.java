package com.example.panda3.entity;

import com.example.panda3.entity.User;
import com.example.panda3.factory.Facotry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class UserTest {
    private static Validator validator;
    public Facotry facotry= new Facotry();

    @BeforeAll
    public static void setUp(){
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void constructorTest() {
        User user = new User(1L, "Grzegorz", "Braun", "mail", "Password");

        Assertions.assertEquals(1L, user.getId());
        Assertions.assertEquals("Grzegorz", user.getFirstName());
        Assertions.assertEquals("Braun", user.getLastName());
        Assertions.assertEquals("mail", user.getEmail());
        Assertions.assertEquals("Password", user.getPassword());
    }

    @Test
    public void areFieldsValidate() {
        User user1 = facotry.createUserWithWrongPassword();
        User user2 = facotry.createUserWithToLongNameAndSurname();
        User user3 = facotry.createUserWithProperFiled();
        User user4=facotry.createUserWithoutName();

        Assertions.assertFalse(validator.validate(user1).isEmpty());
        Assertions.assertFalse(validator.validate(user2).isEmpty());
        Assertions.assertTrue(validator.validate(user3).isEmpty());
        Assertions.assertFalse(validator.validate(user4).isEmpty());
    }

}