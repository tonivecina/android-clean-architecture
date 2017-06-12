package dev.tonivecina.cleanarchitecture.Activities.Main;

import dev.tonivecina.cleanarchitecture.Activities.Main.Fragments.Detail.DetailFragment;
import dev.tonivecina.cleanarchitecture.Activities.Main.Fragments.Login.LoginFragment;

/**
 * @author Toni Vecina on 6/7/17.
 */

public final class MainActivityRoutes {

    private MainActivityProcessor mProcessor;

    MainActivityRoutes(final MainActivityProcessor processor) {
        mProcessor = processor;
    }

    public void replaceDetailFragment(final String origin) {
        DetailFragment fragment = DetailFragment.get(origin);

        mProcessor
                .getView()
                .replace(fragment);
    }

    void replaceLoginFragment() {
        LoginFragment fragment = LoginFragment.get();

        mProcessor
                .getView()
                .replace(fragment);
    }
}
