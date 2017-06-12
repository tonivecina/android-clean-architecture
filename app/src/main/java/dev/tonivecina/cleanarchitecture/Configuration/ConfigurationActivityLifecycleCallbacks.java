package dev.tonivecina.cleanarchitecture.Configuration;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * @author Toni Vecina on 6/7/17.
 */

class ConfigurationActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

    private int mStatusCounter = 0;
    private Configuration.Status mStatus = Configuration.Status.INACTIVE;

    //region ActivityLifecycleCallbacksReg

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {}

    @Override
    public void onActivityStarted(Activity activity) {
        mStatusCounter++;
        mStatus = mStatusCounter > 0 ?
                Configuration.Status.FOREGROUND : Configuration.Status.BACKGROUND;
    }

    @Override
    public void onActivityResumed(Activity activity) {}

    @Override
    public void onActivityPaused(Activity activity) {}

    @Override
    public void onActivityStopped(Activity activity) {
        mStatusCounter--;
        mStatus = mStatusCounter > 0 ?
                Configuration.Status.FOREGROUND : Configuration.Status.BACKGROUND;
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}

    @Override
    public void onActivityDestroyed(Activity activity) {}
    //endregion

    //region GettersReg
    Configuration.Status getStatus() { return mStatus; }
    //endregion
}
