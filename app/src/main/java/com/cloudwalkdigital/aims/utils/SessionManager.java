package com.cloudwalkdigital.aims.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.cloudwalkdigital.aims.LoginActivity;
import com.cloudwalkdigital.aims.data.model.User;
import com.google.gson.Gson;

import javax.inject.Inject;

/**
 * Created by alleoindong on 6/19/17.
 */

public class SessionManager {
    protected SharedPreferences sharedPreferences;

    @Inject
    public SessionManager(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public User getUserInformation() {
        Gson gson = new Gson();
        String json = sharedPreferences.getString("user", "");
        User user = gson.fromJson(json, User.class);

        return user;
    }

    public void setUserInformation(User user) {
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        editor.putString("user", gson.toJson(user));
        editor.apply();
    }

    public void logout(Context context) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        // Bring user back to login
        Intent intent = new Intent(context, LoginActivity.class);
        context.startActivity(intent);
    }

    public Boolean isLoggedIn() {
        User user = getUserInformation();

        return user != null;
    }
}
