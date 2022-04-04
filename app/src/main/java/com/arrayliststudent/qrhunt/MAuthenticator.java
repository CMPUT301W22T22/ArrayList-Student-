package com.arrayliststudent.qrhunt;




/**
 * The MAuthenticator is responsible for logging the user into the app, and determining
 * if the user has owner privledges.
 */
public class MAuthenticator {
    
    private String android_id;
    private boolean ownerPrivledge;
    private User owner;

    private static final MAuthenticator mAuthenticator  = new MAuthenticator();

    public static MAuthenticator getInstance(){
        return mAuthenticator;
    }

    private MAuthenticator() {
        ownerPrivledge = false;
    }

    /**
     * Sets the current user of the app by recording the users androidId, and getting that
     * user from the Users collection if it exists.
     * @param androidId
     * Android id of current user.
     */
    public void setCurrentUser(String androidId) {
        UserDataModel model = UserDataModel.getInstance();
        model.fetchCurrentUser(androidId);
        this.android_id = androidId;
        model.setUserId(this.android_id);
        owner = model.getOwnerById(android_id);

    }

    /**
     * Checks if the user log in was successful, indicating the User has registered previously.
     * @return
     * Boolean indicating if users exists in Users collection.
     */
    public boolean loggedIn() {
        UserDataModel model = UserDataModel.getInstance();
        User user = model.getCurrentUser();
        if ( user.getUserId() == null) {
            System.out.println("User Not Found");
            return  false;
        } else {
            model.setUserCodes();
            setPrivledges();
            return true;
        }
    }

    /**
     * Sets the owner privledges of the current user. Checks if the current user was found
     * in the Owners collection.
     */
    private void setPrivledges() {
        if(owner.getName() != null) {
            ownerPrivledge = true;
        } else {
            ownerPrivledge = false;
        }
    }

    /**
     * Toggles the current user's owner privledges by adding or removing the user from the
     * Owners collection and changing the ownerPrivledge boolean.
     */
    public void toggleOwner() {
        UserDataModel model = UserDataModel.getInstance();
        if(ownerPrivledge) {
            model.removeOwner(android_id);
        } else {
            model.addOwner(android_id);
        }
        ownerPrivledge = !ownerPrivledge;
    }

    /**
     * Getter for ownerPrivledge boolean indicating the current user has owner privledges.
     */
    public boolean checkPrivledge() {
        return ownerPrivledge;
    }
}
