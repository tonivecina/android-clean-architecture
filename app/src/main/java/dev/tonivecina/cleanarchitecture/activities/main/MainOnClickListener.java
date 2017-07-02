package dev.tonivecina.cleanarchitecture.activities.main;

import android.view.View;

import dev.tonivecina.cleanarchitecture.R;

/**
 * @author Toni Vecina on 7/2/17.
 */

final class MainActivityOnClickListener implements View.OnClickListener {

    private MainActivityListeners.ActionListeners listener;

    MainActivityOnClickListener(MainActivityListeners.ActionListeners listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();

        if (id == R.id.activity_main_button_add) {
            listener.createNote();
        }
    }
}
