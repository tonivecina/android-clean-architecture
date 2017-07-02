package dev.tonivecina.cleanarchitecture.activities.addnote;

import dev.tonivecina.cleanarchitecture.entities.database.note.Note;

/**
 * @author Toni Vecina on 7/2/17.
 */

abstract class AddNoteListeners {

    interface ActionListener {
        void applyNote();
    }

    interface NotesListener {
        void onNoteCreated(Note note);
    }
}
