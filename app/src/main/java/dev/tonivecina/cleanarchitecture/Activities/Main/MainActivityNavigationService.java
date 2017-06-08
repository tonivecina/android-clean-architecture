package dev.tonivecina.cleanarchitecture.Activities.Main;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.widget.FrameLayout;

import dev.tonivecina.cleanarchitecture.Activities.Main.Fragments.Detail.DetailFragment;
import dev.tonivecina.cleanarchitecture.Activities.Main.Fragments.Login.LoginFragment;

/**
 * @author Toni Vecina on 6/7/17.
 */

public class MainActivityNavigationService {

    private MainActivity mMainActivity;

    MainActivityNavigationService(final MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }

    private void replace(FrameLayout frameLayout, Fragment fragment) {
        FragmentTransaction fragmentTransaction = mMainActivity
                .getFragmentManager()
                .beginTransaction();

        String tag = fragment.getClass().getSimpleName();

        fragmentTransaction.replace(frameLayout.getId(), fragment, tag);
        fragmentTransaction.commit();
    }

    public void replaceDetailFragment(final String origin) {
        FrameLayout frameLayout = mMainActivity.getContainerFrameLayout();
        DetailFragment fragment = DetailFragment.get(origin);

        replace(frameLayout, fragment);
    }

    void replaceLoginFragment() {
        FrameLayout frameLayout = mMainActivity.getContainerFrameLayout();
        LoginFragment fragment = LoginFragment.get();

        replace(frameLayout, fragment);
    }
}
