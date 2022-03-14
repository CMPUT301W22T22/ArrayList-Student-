package com.arrayliststudent.qrhunt;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

/**
 * UserProfileTest
 * This is a test of the UserProfile class
 */
public class UserProfileTest {
    private UserProfile profile;

    @Before
    public void createUserProfile(){
        profile = new UserProfile();
    }

    @Test
    public void getUserNameTest(){
        profile.setUserName("Vinay");
        String name = profile.getUserName();
        assertEquals("Vinay", name);
    }

    @Test
    public void getContactInfoTest(){
        profile.setContactInfo(905);
        int contactInfo = profile.getContactInfo();
        assertEquals(905, contactInfo);
    }

    @Test
    public void getAdminTest(){
        profile.setAdmin(true);
        boolean admin = profile.getAdmin();
        assertEquals(true, admin);
    }

    @Test
    public void getScoreTest(){
        profile.setScore(100);
        int score = profile.getScore();
        assertEquals(100, score);
    }

}
