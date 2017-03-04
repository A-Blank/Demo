package com.imer.Bean;

/**
 * Created by 丶 on 2017/3/3.
 */

public class Invitation {

    public String UserName;
    public String fromUserName;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }
}
