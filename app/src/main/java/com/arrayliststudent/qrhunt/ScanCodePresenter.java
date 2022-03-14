package com.arrayliststudent.qrhunt;

/**
 * ScanCodePresenter
 * @author vparab
 * Takes the QR code
 * Calculates Score
 * Creates ScannableCode object
 */
public class ScanCodePresenter {

    public static int calculateScore(byte[] rawValue){
        ScoreGenerator generate = new ScoreGenerator();

        String hash = "";

        for(int i = 0; i < rawValue.length; i++){
            hash = hash + rawValue[i];
        }

        int score = generate.calculateScore(hash);
        System.out.println(score);

        return score;
    }

    public static void createScannableCode(String name, int score){
        ScannableCode code = new ScannableCode(name, score);
    }

}
