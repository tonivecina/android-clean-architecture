package dev.tonivecina.cleanarchitecture.Patterns;

import android.util.Patterns;

/**
 * @author Toni Vecina on 6/7/17.
 */

final public class Boolean {

    public static boolean isValidEmail(CharSequence email) {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
