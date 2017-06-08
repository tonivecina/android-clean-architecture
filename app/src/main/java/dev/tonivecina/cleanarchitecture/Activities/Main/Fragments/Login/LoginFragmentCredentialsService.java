package dev.tonivecina.cleanarchitecture.Activities.Main.Fragments.Login;

import dev.tonivecina.cleanarchitecture.Entities.Credentials;
import dev.tonivecina.cleanarchitecture.Patterns.Boolean;
import dev.tonivecina.cleanarchitecture.Services.DLog;

/**
 * @author Toni Vecina on 6/7/17.
 */

class LoginFragmentCredentialsService {

    LoginFragmentCredentialsService() {}

    void setCredentials(final String email, final String password) throws Exception {

        if (email.isEmpty())
            throw new Exception("Email not found");

        if (!Boolean.isValidEmail(email))
            throw new Exception("Email is invalid");

        if (password.length() < 4)
            throw new Exception("Password must contains more than 3 characters");

        Credentials credentials = new Credentials();
        credentials.set(email, password);

        DLog.success("Credentials were stored.");
    }
}
