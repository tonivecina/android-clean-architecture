package dev.tonivecina.cleanarchitecture.activities.addnote;

import android.os.AsyncTask;

import java.util.Calendar;

import dev.tonivecina.cleanarchitecture.application.Configuration;
import dev.tonivecina.cleanarchitecture.entities.database.note.Note;
import dev.tonivecina.cleanarchitecture.entities.database.note.NoteDao;

/**
 * @author Toni Vecina on 7/2/17.
 */

final class AddNoteInteractorNotes {

    private NoteDao noteDao;
    private AddNoteListeners.NotesListener listener;

    AddNoteInteractorNotes(AddNoteListeners.NotesListener listener) {
        noteDao = Configuration
                .getInstance()
                .getAppDataBase()
                .noteDao();

        this.listener = listener;
    }

    void create(final String title, final String description) {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Calendar calendar = Calendar.getInstance();
                final long milliseconds = calendar.getTimeInMillis();

                Note note = new Note();
                note.setTitle(title);
                note.setDescription(description);
                note.setCreatedAt(milliseconds);

                noteDao.insert(note);
                listener.onNoteCreated(note);
            }
        });
    }
}
