package com.arrayliststudent.qrhunt;

public class ScannableCode {

    String codeName;
    String codeScore;

    ScannableCode(String codeName, String codeScore){
        this.codeName = codeName;
        this.codeScore = codeScore;
    }

    ScannableCode() {}

    public String getCodeName() {
        return codeName;
    }

    public String getCodeScore() {
        return codeScore;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName;
    }

    public void setCodeScore(String codeScore) {
        this.codeScore = codeScore;
    }
}
