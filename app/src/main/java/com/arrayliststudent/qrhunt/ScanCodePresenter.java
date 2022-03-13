package com.arrayliststudent.qrhunt;

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
