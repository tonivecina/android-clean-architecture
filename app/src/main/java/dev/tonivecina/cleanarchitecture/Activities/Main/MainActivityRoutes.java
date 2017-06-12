package dev.tonivecina.cleanarchitecture.Activities.Main;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.widget.FrameLayout;

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

    private void replace(FrameLayout frameLayout, Fragment fragment) {
        FragmentTransaction fragmentTransaction = mProcessor
                .getView()
                .getFragmentManager()
                .beginTransaction();

        String tag = fragment.getClass().getSimpleName();

        fragmentTransaction.replace(frameLayout.getId(), fragment, tag);
        fragmentTransaction.commit();
    }

    public void replaceDetailFragment(final String origin) {
        FrameLayout frameLayout = mProcessor
                .getView()
                .getContainerFrameLayout();
        DetailFragment fragment = DetailFragment.get(origin);

        replace(frameLayout, fragment);
    }

    void replaceLoginFragment() {
        FrameLayout frameLayout = mProcessor
                .getView()
                .getContainerFrameLayout();
        LoginFragment fragment = LoginFragment.get();

        replace(frameLayout, fragment);
    }
}
