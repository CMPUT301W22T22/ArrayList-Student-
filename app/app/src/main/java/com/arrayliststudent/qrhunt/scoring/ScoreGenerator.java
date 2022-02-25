package com.arrayliststudent.qrhunt.scoring;

/**
 * @author jmgraham
 */
public class ScoreGenerator{
    ScoreCalculator calculator = new ScoreCalculator();

    public int calculateScore(String hash){
        return calculator.getScore(hash);
    }
}
