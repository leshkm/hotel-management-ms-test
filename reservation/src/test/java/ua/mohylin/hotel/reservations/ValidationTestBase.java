package ua.mohylin.hotel.reservations;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidationTestBase {

    protected static Validator validator;

    @BeforeAll
    public static void setUpClass() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    protected <T> void assertValidationSuccessful(T object) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        assertTrue(violations.isEmpty());
    }

    protected <T> void assertViolationFound(T object, String propertyPath, String messageRegexp) {
        Set<ConstraintViolation<T>> violations = validator.validate(object);
        assertEquals(1, violations.size(), "must be only one violation");

        ConstraintViolation<T> violation = violations.iterator().next();
        System.out.println(violation);
        assertEquals(propertyPath, violation.getPropertyPath().toString(), "propertyPath is wrong");

        assertThat(violation.getMessage(), Matchers.matchesRegex(messageRegexp));

    }

}
