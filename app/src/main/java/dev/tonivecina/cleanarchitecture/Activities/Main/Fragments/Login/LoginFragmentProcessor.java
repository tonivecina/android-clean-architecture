package dev.tonivecina.cleanarchitecture.Activities.Main.Fragments.Login;

import android.content.Context;
import android.widget.Toast;

import dev.tonivecina.cleanarchitecture.Activities.Main.MainActivity;
import dev.tonivecina.cleanarchitecture.Entities.Local.Credentials;

/**
 * @author Toni Vecina on 6/12/17.
 */

final class LoginFragmentProcessor {

    private LoginFragment mView;
    private Context mContext;

    private LoginFragmentInteractorCredentials mInteractorCredentials;

    private LoginFragmentOnClickListener mOnClickListener;

    LoginFragmentProcessor(LoginFragment view) {
        mView = view;
        mContext = mView.getContext();

        synchronized (this) {
            mInteractorCredentials = new LoginFragmentInteractorCredentials();

            mOnClickListener = new LoginFragmentOnClickListener(this);
        }
    }

    void onViewCreated() {
        Credentials credentials = mInteractorCredentials.getCredentials();
        mView.setCredentials(credentials.getEmail(), credentials.getPasswordHash());
    }

    //region GettersReg
    LoginFragment getView() {
        return mView;
    }

    LoginFragmentOnClickListener getOnClickListener() {
        return mOnClickListener;
    }
    //endregion

    //region SettersReg
    void setCredentials() {
        try {
            String email = mView.getEmail();
            String password = mView.getPassword();

            mInteractorCredentials.set(email, password);

            if (mContext instanceof MainActivity) {
                MainActivity activity = (MainActivity) mContext;
                activity
                        .getProcessor()
                        .getRoutes()
                        .replaceDetailFragment(mView.getClass().getSimpleName());
            }

        } catch (Exception exception) {
            Toast.makeText(mContext, exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    //endregion
}
