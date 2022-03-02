package com.arrayliststudent.qrhunt;

/**
 * @author jmgraham
 */
public class ScoreGenerator{
    ScoreCalculator calculator = new ScoreCalculator();

    public int calculateScore(String hash){
        return calculator.getScore(hash);
    }
}
