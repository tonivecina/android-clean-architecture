package dev.tonivecina.cleanarchitecture.activities.main;

import java.util.List;

import dev.tonivecina.cleanarchitecture.entities.database.note.Note;

/**
 * @author Toni Vecina on 7/2/17.
 */

abstract class MainListeners {

    interface ActionListener {
        void createNote();
    }

    interface NotesListener {
        void onNotesReceived(List<Note> notes);
    }
}
