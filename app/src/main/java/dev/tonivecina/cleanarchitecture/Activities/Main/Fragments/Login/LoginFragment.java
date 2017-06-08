package dev.tonivecina.cleanarchitecture.Activities.Main.Fragments.Login;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import dev.tonivecina.cleanarchitecture.Entities.Credentials;
import dev.tonivecina.cleanarchitecture.R;
import dev.tonivecina.cleanarchitecture.Views.EditTexts.LoginFormEditText;

/**
 * @author Toni Vecina on 6/7/17.
 */

public class LoginFragment extends Fragment {

    private LoginFormEditText mEmailEditText, mPasswordEditText;

    @SuppressWarnings("FieldCanBeLocal")
    private Button mLoginButton;

    private LoginFragmentCredentialsService mCredentialsService;
    private LoginFragmentOnClickListener mOnClickListenerService;

    public static LoginFragment get() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        synchronized (this) {
            mCredentialsService = new LoginFragmentCredentialsService();
            mOnClickListenerService = new LoginFragmentOnClickListener(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Credentials credentials = new Credentials();

        mEmailEditText = (LoginFormEditText) view.findViewById(R.id.fragment_login_editText_email);
        mEmailEditText.setText(credentials.getEmail());

        mPasswordEditText = (LoginFormEditText) view.findViewById(R.id.fragment_login_editText_password);
        mPasswordEditText.setText(credentials.getPasswordHash());

        mLoginButton = (Button) view.findViewById(R.id.fragment_login_button);
        mLoginButton.setOnClickListener(mOnClickListenerService);
    }

    //region GettersReg
    LoginFormEditText getEmailEditText() {
        return mEmailEditText;
    }

    LoginFormEditText getPasswordEditText() {
        return mPasswordEditText;
    }

    LoginFragmentCredentialsService getCredentialsService() {
        return mCredentialsService;
    }
    //endregion
}
