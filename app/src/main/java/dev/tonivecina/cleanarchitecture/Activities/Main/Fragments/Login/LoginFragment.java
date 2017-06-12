package dev.tonivecina.cleanarchitecture.Activities.Main.Fragments.Login;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import dev.tonivecina.cleanarchitecture.R;
import dev.tonivecina.cleanarchitecture.Views.EditTexts.LoginFormEditText;

/**
 * @author Toni Vecina on 6/7/17.
 */

public class LoginFragment extends Fragment {

    @SuppressWarnings("FieldCanBeLocal")
    private Button mLoginButton;
    private LoginFormEditText mEmailEditText, mPasswordEditText;

    private LoginFragmentProcessor mProcessor;

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
            mProcessor = new LoginFragmentProcessor(this);
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

        mEmailEditText = (LoginFormEditText) view.findViewById(R.id.fragment_login_editText_email);
        mPasswordEditText = (LoginFormEditText) view.findViewById(R.id.fragment_login_editText_password);

        LoginFragmentOnClickListener onClickListener = mProcessor.getOnClickListener();

        mLoginButton = (Button) view.findViewById(R.id.fragment_login_button);
        mLoginButton.setOnClickListener(onClickListener);

        mProcessor.onViewCreated();
    }

    //region GettersReg
    String getEmail() {
        return mEmailEditText
                .getText()
                .toString();
    }

    String getPassword() {
        return mPasswordEditText
                .getText()
                .toString();
    }
    //endregion

    //region SettersReg
    void setCredentials(@Nullable String email, @Nullable String password) {
        mEmailEditText.setText(email);
        mPasswordEditText.setText(password);
    }
    //endregion
}
