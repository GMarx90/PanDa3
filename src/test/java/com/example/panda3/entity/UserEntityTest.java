package com.example.panda3.entity;

import com.example.panda3.factory.Facotry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

public class UserEntityTest {
    private static Validator validator;
    public Facotry facotry= new Facotry();

    @BeforeAll
    public static void setUp(){
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void constructorTest() {
        UserEntity userEntity = new UserEntity(1L, "Grzegorz", "Braun", "mail", "Password");

        Assertions.assertEquals(1L, userEntity.getId());
        Assertions.assertEquals("Grzegorz", userEntity.getFirstName());
        Assertions.assertEquals("Braun", userEntity.getLastName());
        Assertions.assertEquals("mail", userEntity.getEmail());
        Assertions.assertEquals("Password", userEntity.getPassword());
    }

    @Test
    public void areFieldsValidate() {
        UserEntity userEntity1 = facotry.createUserWithWrongPassword();
        UserEntity userEntity2 = facotry.createUserWithToLongNameAndSurname();
        UserEntity userEntity3 = facotry.createUserWithProperFiled();
        UserEntity userEntity4 =facotry.createUserWithoutName();

        Assertions.assertFalse(validator.validate(userEntity1).isEmpty());
        Assertions.assertFalse(validator.validate(userEntity2).isEmpty());
        Assertions.assertTrue(validator.validate(userEntity3).isEmpty());
        Assertions.assertFalse(validator.validate(userEntity4).isEmpty());
    }

}