package ru.job4j.dream.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class PostTest {
    @Test
    public void shouldEqualsTwoPost() {
        LocalDateTime localDateTime = LocalDateTime.now();
        Post postOne = new Post(1, "Name", "Description", localDateTime);
        Post postTwo = new Post(1, "Name", "Description", localDateTime);
        Assertions.assertEquals(postOne, postTwo);
        Assertions.assertEquals(postOne.getCreatedToString(), postTwo.getCreatedToString());
        Assertions.assertEquals(postOne.toString(), postTwo.toString());
    }
}
