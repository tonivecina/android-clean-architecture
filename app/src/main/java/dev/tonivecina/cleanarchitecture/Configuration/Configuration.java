package dev.tonivecina.cleanarchitecture.Configuration;

import android.app.Application;

import dev.tonivecina.cleanarchitecture.Services.DLog;

/**
 * @author Toni Vecina on 6/7/17.
 */

final public class Configuration extends Application {

    public enum Status {
        INACTIVE,
        FOREGROUND,
        BACKGROUND
    }

    private static Configuration mInstance;

    private ConfigurationActivityLifecycleCallbacks mActivityLifecycleCallbacks;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        synchronized (this) {
            mActivityLifecycleCallbacks = new ConfigurationActivityLifecycleCallbacks();
        }

        registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);

        DLog.success("Application is ready!");
    }

    @Override
    public void onTerminate() {
        unregisterActivityLifecycleCallbacks(mActivityLifecycleCallbacks);

        super.onTerminate();
    }

    //region GettersReg
    public static synchronized Configuration get() { return mInstance; }

    public Status getStatus() {
        return mActivityLifecycleCallbacks.getStatus();
    }
    //endregion
}
