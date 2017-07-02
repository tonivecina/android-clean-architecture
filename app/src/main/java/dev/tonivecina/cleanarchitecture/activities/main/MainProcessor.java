package dev.tonivecina.cleanarchitecture.activities.main;

import java.util.List;

import dev.tonivecina.cleanarchitecture.entities.database.note.Note;

/**
 * @author Toni Vecina on 6/12/17.
 */

final class MainActivityProcessor implements MainActivityListeners.ActionListeners {

    @SuppressWarnings("FieldCanBeLocal")
    private MainActivity view;

    private MainActivityRoutes routes;
    private MainActivityOnClickListener onClickListener;
    private MainActivityInteractorNotes notesInteractor;

    MainActivityProcessor(MainActivity view) {
        this.view = view;
    }

    void onCreate() {
        getNotes();
    }

    //region Getters
    private synchronized MainActivityInteractorNotes getNotesInteractor() {
        if (notesInteractor == null) {
            notesInteractor = new MainActivityInteractorNotes();
        }

        return notesInteractor;
    }

    private synchronized MainActivityRoutes getRoutes() {
        if (routes == null) {
            routes = new MainActivityRoutes(view);
        }

        return routes;
    }

    synchronized MainActivityOnClickListener getOnClickListener() {

        if (onClickListener == null) {
            onClickListener = new MainActivityOnClickListener(this);
        }

        return onClickListener;
    }

    private void getNotes() {
        List<Note> notes = getNotesInteractor().getAll();
        view.setNotes(notes);
    }
    //endregion

    //region ActionListeners
    @Override
    public void createNote() {
        getRoutes().intentAddNoteActivity();
    }
    //endregion
}
