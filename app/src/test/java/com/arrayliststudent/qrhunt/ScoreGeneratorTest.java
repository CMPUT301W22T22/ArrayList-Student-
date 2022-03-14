package com.arrayliststudent.qrhunt;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * ScoreGeneratorTest
 * This is a test of the ScoreGenerator class
 */
public class ScoreGeneratorTest {
    private ScoreGenerator generator;

    @Before
    public void createScoreGenerator(){
        generator = new ScoreGenerator();
    }

    @Test
    public void calculateScoreTest(){
        int score = generator.calculateScore("12345aa667");
        assertEquals(16, score);
    }
}
