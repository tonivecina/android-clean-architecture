package dev.tonivecina.cleanarchitecture.Activities.Main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import dev.tonivecina.cleanarchitecture.Entities.Credentials;
import dev.tonivecina.cleanarchitecture.R;

/**
 * @author Toni Vecina on 6/7/17.
 */

public class MainActivity extends AppCompatActivity {

    private FrameLayout mContainerFrameLayout;

    private MainActivityNavigationService mNavigationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        synchronized (this) {
            mNavigationService = new MainActivityNavigationService(this);
        }

        mContainerFrameLayout = (FrameLayout) findViewById(R.id.activity_main_frameLayout);

        mNavigationService.replaceLoginFragment();
    }

    //region GettersReg
    FrameLayout getContainerFrameLayout() {
        return mContainerFrameLayout;
    }

    public MainActivityNavigationService getNavigationService() {
        return mNavigationService;
    }
    //endregion
}
