package dev.tonivecina.cleanarchitecture.Activities.Main;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.widget.FrameLayout;

import dev.tonivecina.cleanarchitecture.Activities.Main.Fragments.Detail.DetailFragment;

/**
 * @author Toni Vecina on 6/7/17.
 */

class MainActivityNavigations {

    private MainActivity mMainActivity;

    MainActivityNavigations(final MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }

    private void replace(FrameLayout frameLayout, Fragment fragment) {
        FragmentTransaction fragmentTransaction = mMainActivity
                .getFragmentManager()
                .beginTransaction();

        fragmentTransaction.replace(frameLayout.getId(), fragment, fragment.getTag());
        fragmentTransaction.commit();
    }

    public void replaceDetailFragment() {
        FrameLayout frameLayout = mMainActivity.getContainerFrameLayout();
        DetailFragment fragment = DetailFragment.get();

        replace(frameLayout, fragment);
    }
}
