package com.mobitrackbd.mobitrack.Response;

import com.mobitrackbd.mobitrack.Model.User;

/**
 * Created by IMATPC-12 on 07-Feb-18.
 */

public class LoginResponse {
    private int success;
    private String message;
    private User user;

    public LoginResponse() {
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
