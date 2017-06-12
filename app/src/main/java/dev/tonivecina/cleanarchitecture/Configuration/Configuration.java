package dev.tonivecina.cleanarchitecture.Configuration;

import android.app.Application;

import dev.tonivecina.cleanarchitecture.DLog;

/**
 * @author Toni Vecina on 6/7/17.
 */

final public class Configuration extends Application {

    enum Status {
        INACTIVE,
        FOREGROUND,
        BACKGROUND
    }

    private static Configuration mInstance;

    private ConfigurationProcessor mProcessor;

    @Override
    public void onCreate() {
        super.onCreate();

        mInstance = this;

        synchronized (this) {
            mProcessor = new ConfigurationProcessor();
        }

        registerActivityLifecycleCallbacks(mProcessor.getActivityLifecycleCallbacks());

        DLog.success("Application is ready!");
    }

    @Override
    public void onTerminate() {
        unregisterActivityLifecycleCallbacks(mProcessor.getActivityLifecycleCallbacks());
        super.onTerminate();
    }

    //region GettersReg
    public static synchronized Configuration get() { return mInstance; }

    public Status getStatus() {
        return mProcessor
                .getActivityLifecycleCallbacks()
                .getStatus();
    }
    //endregion
}
