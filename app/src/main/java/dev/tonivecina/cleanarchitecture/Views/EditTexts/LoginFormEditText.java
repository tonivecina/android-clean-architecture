package dev.tonivecina.cleanarchitecture.Views.EditTexts;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

/**
 * @author Toni Vecina on 6/7/17.
 */

public class LoginFormEditText extends AppCompatEditText {

    public LoginFormEditText(Context context) {
        super(context);
        view(context);
    }

    public LoginFormEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        view(context);
    }

    public LoginFormEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view(context);
    }

    private void view(Context context) {
        // Custom properties here.
    }
}
