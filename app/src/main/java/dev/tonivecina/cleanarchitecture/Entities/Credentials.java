package dev.tonivecina.cleanarchitecture.Entities;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import dev.tonivecina.cleanarchitecture.Configuration.Configuration;

/**
 * @author Toni Vecina on 6/7/17.
 */

final public class Credentials {
    private final String PREFERENCES_NAME = "credentials";

    private final String BUNDLE_EMAIL = "email";
    private final String BUNDLE_PASSWORD_HASH = "passwordHash";
    private final String BUNDLE_TOKEN = "token";

    private SharedPreferences mPreferences;

    public Credentials() {
        Context context = Configuration.get();

        synchronized (this) {
            mPreferences = context
                    .getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
        }
    }

    //region GettersReg
    @Nullable
    public String getEmail() {
        return mPreferences.getString(BUNDLE_EMAIL, null);
    }

    @Nullable
    public String getPasswordHash() {
        return mPreferences.getString(BUNDLE_PASSWORD_HASH, null);
    }

    @Nullable
    public String getToken() {
        return mPreferences.getString(BUNDLE_TOKEN, null);
    }

    public boolean isLogged() {
        return getEmail() != null && getPasswordHash() != null && getToken() != null;
    }
    //endregion

    //region SettersReg
    public void set(final String email, final String passwordHash) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(BUNDLE_EMAIL, email);
        editor.putString(BUNDLE_PASSWORD_HASH, passwordHash);
        editor.apply();
    }

    public void set(final String token) {
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(BUNDLE_TOKEN, token);
        editor.apply();
    }
    //endregion
}
