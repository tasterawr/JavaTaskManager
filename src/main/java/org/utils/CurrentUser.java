package org.utils;

import org.dao_layer.model.User;

public class CurrentUser {
    private static User user;

    public static User getUser() {
        return user;
    }

    public static void setUser(User user_) {
        user = user_;
    }

}
