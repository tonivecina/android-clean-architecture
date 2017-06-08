package dev.tonivecina.cleanarchitecture.Activities.Main.Fragments.Login;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import dev.tonivecina.cleanarchitecture.Activities.Main.MainActivity;
import dev.tonivecina.cleanarchitecture.R;

/**
 * @author Toni Vecina on 6/7/17.
 */

class LoginFragmentOnClickListener implements View.OnClickListener {

    private LoginFragment mLoginFragment;
    private Context mContext;

    LoginFragmentOnClickListener(LoginFragment fragment) {
        mLoginFragment = fragment;
        mContext = mLoginFragment.getContext();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.fragment_login_button:
                onClickLogin();
                break;

            default:
                break;
        }
    }

    private void onClickLogin() {
        try {
            String email = mLoginFragment
                    .getEmailEditText()
                    .getText()
                    .toString();

            String password = mLoginFragment
                    .getPasswordEditText()
                    .getText()
                    .toString();

            mLoginFragment
                    .getCredentialsService()
                    .setCredentials(email, password);

            if (mContext instanceof MainActivity) {
                MainActivity activity = (MainActivity) mContext;
                activity
                        .getNavigationService()
                        .replaceDetailFragment(mLoginFragment.getClass().getSimpleName());
            }

        } catch (Exception exception) {
            Toast.makeText(mContext, exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
