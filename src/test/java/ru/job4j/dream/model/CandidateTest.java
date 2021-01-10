package ru.job4j.dream.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CandidateTest {
    @Test
    public void shouldEqualsTwoCandidate() {
        Candidate candidateOne = new Candidate(1, "Name", "Surname", 34);
        candidateOne.setCityId(1);
        candidateOne.setCity("Krasnoyarsk");
        candidateOne.setIdPhoto("2.jpg");
        Candidate candidateTwo = new Candidate(1, "Name", "Surname", 34);
        candidateTwo.setCityId(1);
        candidateTwo.setCity("Krasnoyarsk");
        candidateTwo.setIdPhoto("2.jpg");
        Assertions.assertEquals(candidateOne, candidateTwo);
        Assertions.assertEquals(candidateOne.toString(), candidateTwo.toString());
    }
}
