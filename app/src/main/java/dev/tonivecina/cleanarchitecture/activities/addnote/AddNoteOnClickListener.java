package dev.tonivecina.cleanarchitecture.activities.addnote;

import android.view.View;

import dev.tonivecina.cleanarchitecture.R;

/**
 * @author Toni Vecina on 7/2/17.
 */

final class AddNoteOnClickListener implements View.OnClickListener {

    private AddNoteListeners.ActionListener listener;

    AddNoteOnClickListener(AddNoteListeners.ActionListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        final int id = v.getId();

        if (id == R.id.activity_addNote_button_apply) {
            listener.applyNote();
        }
    }
}
