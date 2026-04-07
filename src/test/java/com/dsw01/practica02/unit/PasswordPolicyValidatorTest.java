package com.dsw01.practica02.unit;

import com.dsw01.practica02.service.validation.PasswordPolicyValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PasswordPolicyValidatorTest {

    private final PasswordPolicyValidator validator = new PasswordPolicyValidator();

    @Test
    void shouldAcceptValidPassword() {
        assertDoesNotThrow(() -> validator.validate("Abcdef1!"));
    }

    @Test
    void shouldRejectNullOrBlankOrWhitespace() {
        assertThrows(IllegalArgumentException.class, () -> validator.validate(null));
        assertThrows(IllegalArgumentException.class, () -> validator.validate(""));
        assertThrows(IllegalArgumentException.class, () -> validator.validate("   "));
    }

    @Test
    void shouldRejectLengthOutsideRange() {
        assertThrows(IllegalArgumentException.class, () -> validator.validate("Aa1!aaa"));
        assertThrows(IllegalArgumentException.class, () -> validator.validate("Aa1!" + "a".repeat(61)));
    }

    @Test
    void shouldRejectMissingComplexityRules() {
        assertThrows(IllegalArgumentException.class, () -> validator.validate("abcdefg1!"));
        assertThrows(IllegalArgumentException.class, () -> validator.validate("ABCDEFG1!"));
        assertThrows(IllegalArgumentException.class, () -> validator.validate("Abcdefgh!"));
        assertThrows(IllegalArgumentException.class, () -> validator.validate("Abcdefg1"));
    }
}
