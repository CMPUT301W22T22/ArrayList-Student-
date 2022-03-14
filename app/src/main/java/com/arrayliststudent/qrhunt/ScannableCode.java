package com.arrayliststudent.qrhunt;

/**
 * This class represents a ScannableCode object including the codeName and codeScore. The next
 * release should also contain an optional image of the object scanned, and an optional
 * geolocation.
 */
public class ScannableCode {

    String codeName;
    int codeScore;

    ScannableCode(String codeName, int codeScore){
        this.codeName = codeName;
        this.codeScore = codeScore;
    }

    ScannableCode() {}

    /**
     * Getter for the code name.
     * @return codeName
     * String representing the name of the ScannableCode.
     */
    public String getCodeName() {
        return codeName;
    }

    /**
     * Getter for the code score.
     * @return codeScore
     * String representing the score of the ScannableCode.
     */
    public int getCodeScore() {
        return codeScore;
    }

    /**
     * Setter for code name
     */
    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    /**
     * Setter for code score.
     */
    public void setCodeScore(int codeScore) {
        this.codeScore = codeScore;
    }
}
