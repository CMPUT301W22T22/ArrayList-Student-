package com.arrayliststudent.qrhunt;

/**
 * ScanCodePresenter
 * Takes the QR code
 * Calculates Score (Temp. formula being used)
 * Creates ScanmableCode object
 */
public class ScanCodePresenter {

    public static int calculateScore(byte[] rawValue){
        int score = 0;

        for(int i = 0; i < rawValue.length; i++){
            score = score + rawValue[i];
        }
        return score;
    }

    public static void createScannableCode(String name, int score){
        ScannableCode code = new ScannableCode(name, score);
    }

}
