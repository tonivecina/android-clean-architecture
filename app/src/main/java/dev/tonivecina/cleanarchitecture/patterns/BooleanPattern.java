package dev.tonivecina.cleanarchitecture.patterns;

import android.util.Patterns;

/**
 * @author Toni Vecina on 6/7/17.
 */

public final class Boolean {

    private Boolean() {
        // empty constructor
    }

    public static boolean isValidEmail(CharSequence email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
