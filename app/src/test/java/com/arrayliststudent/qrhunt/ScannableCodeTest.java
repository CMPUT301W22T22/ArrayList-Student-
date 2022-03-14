package com.arrayliststudent.qrhunt;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class ScannableCodeTest {
    private ScannableCode code;

    @Before
    public void createScannableCode(){
        code = new ScannableCode();
    }

    @Test
    public void getCodeNameTest(){
        code.setCodeName("QR");
        String name = code.getCodeName();
        assertEquals("QR", name);
    }

    @Test
    public void getCodeScoreTest(){
        code.setCodeScore(60);
        int score = code.getCodeScore();
        assertEquals(60, score);
    }
}
