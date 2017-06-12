package dev.tonivecina.cleanarchitecture.Configuration;

/**
 * @author Toni Vecina on 6/12/17.
 */

final class ConfigurationProcessor {

    private ConfigurationActivityLifecycleCallbacks mActivityLifecycleCallbacks;

    ConfigurationProcessor() {
        synchronized (this) {
            mActivityLifecycleCallbacks = new ConfigurationActivityLifecycleCallbacks();
        }
    }

    //region GettersReg
    public ConfigurationActivityLifecycleCallbacks getActivityLifecycleCallbacks() {
        return mActivityLifecycleCallbacks;
    }
    //endregion
}
