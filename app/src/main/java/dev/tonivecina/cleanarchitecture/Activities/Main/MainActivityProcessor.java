package dev.tonivecina.cleanarchitecture.Activities.Main;

/**
 * @author Toni Vecina on 6/12/17.
 */

public final class MainActivityProcessor {

    private MainActivity mView;
    private MainActivityRoutes mRoutes;

    MainActivityProcessor(MainActivity view) {
        mView = view;

        synchronized (this) {
            mRoutes = new MainActivityRoutes(this);
        }
    }

    void onCreate() {
        mRoutes.replaceLoginFragment();
    }

    //region GettersReg
    MainActivity getView() {
        return mView;
    }

    public MainActivityRoutes getRoutes() {
        return mRoutes;
    }
    //endregion
}
