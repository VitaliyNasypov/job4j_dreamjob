package ru.job4j.dream.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserTest {
    @Test
    public void shouldEqualsTwoUser() {
        User userOne = new User();
        userOne.setId(1);
        userOne.setName("Name");
        userOne.setEmail("email@email.ru");
        userOne.setPassword("password");
        userOne.setGroup("Group");
        User userTwo = new User();
        userTwo.setId(1);
        userTwo.setName("Name");
        userTwo.setEmail("email@email.ru");
        userTwo.setPassword("password");
        userTwo.setGroup("Group");
        Assertions.assertEquals(userOne, userTwo);
        Assertions.assertEquals(userOne.toString(), userTwo.toString());
    }
}
