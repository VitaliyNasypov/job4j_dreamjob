package ru.job4j.dream.store;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PasswordHashTest {
    @Test
    public void shouldEqualsPassword() {
        String passwordHash = new PasswordHash()
                .generatePasswordHash("test", "test@test.ru",
                        10000, 512, "PBKDF2WithHmacSHA512");
        Assertions.assertTrue(new PasswordHash()
                .validatePassword("test", passwordHash));
    }
}
