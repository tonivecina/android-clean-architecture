package dev.tonivecina.cleanarchitecture.activities.main;

import java.util.List;

import dev.tonivecina.cleanarchitecture.configuration.Configuration;
import dev.tonivecina.cleanarchitecture.entities.database.note.Note;
import dev.tonivecina.cleanarchitecture.entities.database.note.NoteDao;

/**
 * @author Toni Vecina on 7/2/17.
 */

final class MainActivityInteractorNotes {

    private NoteDao noteDao;

    MainActivityInteractorNotes() {
        noteDao = Configuration
                .getInstance()
                .getAppDataBase()
                .noteDao();
    }

    //region Getters
    List<Note> getAll() {
        return noteDao.getAll();
    }
    //endregion
}
