package dev.tonivecina.cleanarchitecture.activities.main;

import android.os.AsyncTask;

import java.util.Collections;
import java.util.List;

import dev.tonivecina.cleanarchitecture.application.AppDataBase;
import dev.tonivecina.cleanarchitecture.application.Configuration;
import dev.tonivecina.cleanarchitecture.entities.database.note.Note;

/**
 * @author Toni Vecina on 7/2/17.
 */

final class MainInteractorNotes {

    private AppDataBase dataBase;
    private MainListeners.NotesListener listener;

    MainInteractorNotes(MainListeners.NotesListener listener) {
        dataBase = Configuration
                .getInstance()
                .getAppDataBase();

        this.listener = listener;
    }

    //region Getters
    void getAll() {
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                List<Note> notes = dataBase.noteDao().getAll();
                Collections.reverse(notes);

                listener.onNotesReceived(notes);
            }
        });
    }
    //endregion
}
