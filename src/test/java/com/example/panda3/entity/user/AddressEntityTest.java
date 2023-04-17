package com.example.panda3.entity.user;

import com.example.panda3.entity.user.AddressEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AddressEntityTest {
    AddressEntity addressEntity = new AddressEntity(1L, "Krakow", "Karola", "11-111");
    private static Validator validator;

    @Test
    public void constructorTest() {
        addressEntity.getCity().equals("Krakow");
        addressEntity.getRoad().equals("Karola");
        addressEntity.getPostCode().equals("11-111");

    }

    @BeforeAll
    public static void setUp() {
        ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    @Test
    public void shouldNotPopulateWithNullCity() {
        addressEntity.setCity(null);
        assertEquals("CITY", addressEntity.getCity());
        Assertions.assertTrue(validator.validate(addressEntity).isEmpty());
    }

    @Test
    public void shouldNotPopulateWithNullRoad() {
        addressEntity.setRoad(null);
        assertEquals("ROAD", addressEntity.getRoad());
        Assertions.assertTrue(validator.validate(addressEntity).isEmpty());
    }

    @Test
    public void shouldNotPopulateWithNullPostCode() {
        addressEntity.setPostCode(null);
        assertEquals("POSTCODE", addressEntity.getPostCode());
    }

    @Test
    public void shouldVerifyPostalCodeFormat() {
        addressEntity.setPostCode("12321");
        assertFalse(validator.validate(addressEntity).isEmpty());

        Set<ConstraintViolation<AddressEntity>> violations = validator.validate(addressEntity);
        assertFalse(violations.isEmpty());

        ConstraintViolation<AddressEntity> expect = violations.stream()
                .filter(violation -> violation.getMessage().equals("Postal code must be in the format xx-xxx"))
                .findFirst()
                .orElse(null);

        assertEquals("Postal code must be in the format xx-xxx", expect.getMessage());
    }

}



