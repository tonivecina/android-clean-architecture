package dev.tonivecina.cleanarchitecture.Activities.Main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import dev.tonivecina.cleanarchitecture.R;

/**
 * @author Toni Vecina on 6/7/17.
 */

public class MainActivity extends AppCompatActivity {

    private FrameLayout mContainerFrameLayout;

    private MainActivityProcessor mProcessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        synchronized (this) {
            mProcessor = new MainActivityProcessor(this);
        }

        mContainerFrameLayout = (FrameLayout) findViewById(R.id.activity_main_frameLayout);

        mProcessor
                .getRoutes()
                .replaceLoginFragment();
    }

    //region GettersReg
    FrameLayout getContainerFrameLayout() {
        return mContainerFrameLayout;
    }

    public MainActivityProcessor getProcessor() {
        return mProcessor;
    }
    //endregion
}
