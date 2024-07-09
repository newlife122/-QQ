package common;

import java.io.Serializable;

/**
 * @author raoxin
 */
public class User implements Serializable {
    private String UserID;
    private String PassWord;
    private static final long serialVersionUID = 1L;

    public User() {

    }

    public User(String userID, String passWord) {
        this.UserID = userID;
        this.PassWord = passWord;
    }

    public String getUserID() {
        return UserID;
    }

    public void setUserID(String userID) {
        UserID = userID;
    }

    public String getPassWord() {
        return PassWord;
    }

    public void setPassWord(String passWord) {
        PassWord = passWord;
    }
}
