package dev.tonivecina.cleanarchitecture.activities.main;

import java.util.List;

import dev.tonivecina.cleanarchitecture.entities.database.note.Note;

/**
 * @author Toni Vecina on 6/12/17.
 */

final class MainProcessor implements
        MainListeners.ActionListener,
        MainListeners.NotesListener
{
    @SuppressWarnings("FieldCanBeLocal")
    private MainActivity view;

    private MainRoutes routes;
    private MainOnClickListener onClickListener;
    private MainInteractorNotes notesInteractor;

    MainProcessor(MainActivity view) {
        this.view = view;
    }

    void onCreate() {
        getNotes();
    }

    //region Getters
    private synchronized MainInteractorNotes getNotesInteractor() {
        if (notesInteractor == null) {
            notesInteractor = new MainInteractorNotes(this);
        }

        return notesInteractor;
    }

    private synchronized MainRoutes getRoutes() {
        if (routes == null) {
            routes = new MainRoutes(view);
        }

        return routes;
    }

    synchronized MainOnClickListener getOnClickListener() {

        if (onClickListener == null) {
            onClickListener = new MainOnClickListener(this);
        }

        return onClickListener;
    }

    private void getNotes() {
        getNotesInteractor().getAll();
    }
    //endregion

    //region ActionListener
    @Override
    public void createNote() {
        getRoutes().intentAddNoteActivity();
    }
    //endregion

    //region NotesListener
    @Override
    public void onNotesReceived(List<Note> notes) {
        view.setNotes(notes);
    }
    //endregion
}
