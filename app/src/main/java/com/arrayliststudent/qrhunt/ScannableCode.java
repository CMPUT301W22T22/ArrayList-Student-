package com.arrayliststudent.qrhunt;

public class ScannableCode {

    String codeName;
    int codeScore;

    ScannableCode(String codeName, int codeScore){
        this.codeName = codeName;
        this.codeScore = codeScore;
    }

    ScannableCode() {}

    public String getCodeName() {
        return codeName;
    }

    public int getCodeScore() {
        return codeScore;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public void setCodeScore(int codeScore) {
        this.codeScore = codeScore;
    }
}
