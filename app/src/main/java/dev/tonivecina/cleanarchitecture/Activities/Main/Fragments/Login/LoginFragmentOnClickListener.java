package dev.tonivecina.cleanarchitecture.Activities.Main.Fragments.Login;

import android.view.View;

import dev.tonivecina.cleanarchitecture.R;

/**
 * @author Toni Vecina on 6/7/17.
 */

class LoginFragmentOnClickListener implements View.OnClickListener {

    private LoginFragmentProcessor mProcessor;

    LoginFragmentOnClickListener(LoginFragmentProcessor processor) {
        mProcessor = processor;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.fragment_login_button:
                mProcessor.setCredentials();
                break;

            default:
                break;
        }
    }
}
