package com.arrayliststudent.qrhunt.scoring;

/**
 * @author jmgraham
 *
 */
public interface ScoreStrategy {
    int getScore(String codeHash);
}
