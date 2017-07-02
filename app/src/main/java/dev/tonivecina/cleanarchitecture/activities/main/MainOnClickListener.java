package dev.tonivecina.cleanarchitecture.activities.main;

import android.view.View;

import dev.tonivecina.cleanarchitecture.R;

/**
 * @author Toni Vecina on 7/2/17.
 */

final class MainOnClickListener implements View.OnClickListener {

    private MainListeners.ActionListener listener;

    MainOnClickListener(MainListeners.ActionListener listener) {
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
