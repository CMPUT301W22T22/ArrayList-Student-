package com.arrayliststudent.qrhunt;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

    public static int createScannableCode(String name, byte[] bytes){

        int score = calculateScore(bytes);
        String hash = "";

        for(int i = 0; i < bytes.length; i++){
            hash = hash + bytes[i];
        }
        ScannableCode code = new ScannableCode(name, score, hash);
        return score;
    }

    public static void UpdateCode(ScannableCode code){
        UserDataModel model = UserDataModel.getInstance();
        model.addCode(code);

    }



}
