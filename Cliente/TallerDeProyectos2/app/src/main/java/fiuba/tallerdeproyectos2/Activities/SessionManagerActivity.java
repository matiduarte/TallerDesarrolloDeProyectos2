package fiuba.tallerdeproyectos2.Activities;

import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SessionManagerActivity {

    private SharedPreferences pref;
    private Editor editor;
    private Context context;

    private static final String PREF_NAME = "SessionData";
    private static final String IS_LOGIN = "IsLoggedIn";
    static final String KEY_NAME = "name";
    static final String KEY_SURNAME = "surname";
    static final String KEY_EMAIL = "email";
    static final String KEY_IMAGE = "image";
    public static final String KEY_ID = "id";

    public SessionManagerActivity(Context context) {
        this.context = context;
        int PRIVATE_MODE = 0;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    void createLoginSession(String email, String name, String surname, String image, String id){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_SURNAME, surname);
        editor.putString(KEY_IMAGE, image);
        editor.putString(KEY_ID, id);
        editor.commit();
    }

    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<>();
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));
        user.put(KEY_SURNAME, pref.getString(KEY_SURNAME, null));
        user.put(KEY_IMAGE, pref.getString(KEY_IMAGE, null));
        user.put(KEY_ID, pref.getString(KEY_ID, null));
        return user;
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        if(!this.isLoggedIn()){
            Intent i = new Intent(context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }

    /**
     * Clear session details
     * */
    void logoutUser(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    private boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

}