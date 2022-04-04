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

    public static String getHash(byte[] rawValue){
        String hash = "";

        for(int i = 0; i < rawValue.length; i++){
            hash = hash + rawValue[i];
        }

        return hash;
    }

    public static int calculateScore(String hash){
        ScoreGenerator generate = new ScoreGenerator();
        int score = generate.calculateScore(hash);
        return score;
    }

    public static ScannableCode createScannableCode(String name, int score, String hash){
        ScannableCode code = new ScannableCode(name, score, hash);
        return code;
    }

    public static void UpdateCode(ScannableCode code) {
        UserDataModel model = UserDataModel.getInstance();
        model.addCode(code);
    }

}
