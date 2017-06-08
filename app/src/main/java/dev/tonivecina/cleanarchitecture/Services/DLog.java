package dev.tonivecina.cleanarchitecture.Services;

import android.util.Log;

import dev.tonivecina.cleanarchitecture.BuildConfig;

/**
 * @author Toni Vecina on 6/7/17.
 */

final public class DLog {

    private enum Type {
        DEBUG,
        ERROR,
        INFO,
        WARNING
    }

    final private static String SIGNATURE = "Clean Architecture";

    private static void set(final String message, final Type type) {

        if (!BuildConfig.DEBUG) return;

        String separator = "----------------------------------------";

        switch (type) {
            case DEBUG:
                Log.d(SIGNATURE, message);
                Log.d(SIGNATURE, separator);
                break;

            case ERROR:
                Log.e(SIGNATURE, message);
                Log.e(SIGNATURE, message);
                break;

            case INFO:
                Log.i(SIGNATURE, message);
                Log.i(SIGNATURE, message);
                break;

            case WARNING:
                Log.w(SIGNATURE, message);
                Log.w(SIGNATURE, message);
                break;
        }
    }

    public static void success(final String message) {
        set(message, Type.DEBUG);
    }

    public static void error(final String message) {
        set(message, Type.ERROR);
    }

    public static void info(final String message) {
        set(message, Type.INFO);
    }

    public static void warning(final String message) {
        set(message, Type.WARNING);
    }
}
