package com.arrayliststudent.qrhunt;

import java.util.ArrayList;
import java.util.Locale;

/**
 * @author jmgraham
 * This class can be used to calculate the score of a given code hash
 * Based on the Proposed Scoring System on Eclass
 */
public class ScoreCalculator {
    private ArrayList hexTable;

    public ScoreCalculator(){
        char[] hexArray = {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};
        hexTable = new ArrayList();

        for(char c : hexArray) {
            hexTable.add(c);
        }
    }

    public int getScore(String codeHash){
        char[] hash = codeHash.toLowerCase(Locale.ROOT).toCharArray();
        int score = 0;
        int counter = 0;

        for(int i = 1; i < hash.length; i++){

            if(hash[i] == hash[i-1]){//counter increments as characters are repeated
                counter++;
            }else{//counter is reset and points added to score if repetition ends

                if (hash[i-1] == '0') {//If the current character is 0

                    if (counter > 0) {//A series of 0 gets a base 20
                        score += Math.pow(20, counter);
                    } else {//A single 0 gets 1 point
                        score++;
                    }
                }else{//If the current character isn't 0
                    if (counter > 0) {
                        score += Math.pow(hexTable.indexOf(hash[i-1]), counter);
                    }
                }

                counter = 0;
            }

            if((i+1) == hash.length){//a check for the final character in the hash

                if (hash[i-1] == '0') {//If the current character is 0

                    if (counter > 0) {//A series of 0 gets a base 20
                        score += Math.pow(20, counter);
                    } else {//A single 0 gets 1 point
                        score++;
                    }
                }else{//If the current character isn't 0
                    if (counter > 0) {
                        score += Math.pow(hexTable.indexOf(hash[i-1]), counter);
                    }
                }
            }
        }

        return score;
    }
}
