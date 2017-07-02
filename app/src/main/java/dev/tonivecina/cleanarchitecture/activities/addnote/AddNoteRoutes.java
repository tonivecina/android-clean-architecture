package dev.tonivecina.cleanarchitecture.activities.addnote;

import android.app.Activity;
import android.content.Intent;

import dev.tonivecina.cleanarchitecture.entities.database.note.Note;

/**
 * @author Toni Vecina on 7/2/17.
 */

final class AddNoteRoutes {

    private AddNoteActivity view;

    AddNoteRoutes(AddNoteActivity view) {
        this.view = view;
    }

    void finish(final Note note) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(AddNoteActivity.BUNDLE_NOTE, note);
        view.setResult(Activity.RESULT_OK, resultIntent);
        view.finish();
    }
}
