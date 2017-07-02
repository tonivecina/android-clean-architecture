package dev.tonivecina.cleanarchitecture.activities.addnote;

import android.widget.Toast;
import dev.tonivecina.cleanarchitecture.DLog;
import dev.tonivecina.cleanarchitecture.entities.database.note.Note;

/**
 * @author Toni Vecina on 7/2/17.
 */

final class AddNoteProcessor implements
        AddNoteListeners.ActionListener,
        AddNoteListeners.NotesListener
{
    private AddNoteActivity view;
    private AddNoteRoutes routes;
    private AddNoteOnClickListener onClickListener;
    private AddNoteInteractorNotes notesInteractor;

    AddNoteProcessor(AddNoteActivity view) {
        this.view = view;
    }

    void onCreate() {
        // empty note
    }

    //region Getters
    synchronized AddNoteOnClickListener getOnClickListener() {
        if (onClickListener == null) {
            onClickListener = new AddNoteOnClickListener(this);
        }

        return onClickListener;
    }

    private synchronized AddNoteRoutes getRoutes() {
        if (routes == null) {
            routes = new AddNoteRoutes(view);
        }

        return routes;
    }

    private synchronized AddNoteInteractorNotes getNotesInteractor() {
        if (notesInteractor == null) {
            notesInteractor = new AddNoteInteractorNotes(this);
        }

        return notesInteractor;
    }

    private synchronized boolean isNoteValid(final String title, final String description) {

        if (title.length() < 1) {
            Toast.makeText(view, "Please, insert title", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (description.length() < 1) {
            Toast.makeText(view, "Please, insert description.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
    //endregion

    //region ActionListener
    @Override
    public void applyNote() {
        final String title = view.getTitleText();
        final String description = view.getDescriptionText();

        if (!isNoteValid(title, description)) {
            DLog.warning("Invalid form.");
            return;
        }

        getNotesInteractor().create(title, description);
    }
    //endregion

    //region NotesListener
    @Override
    public void onNoteCreated(Note note) {
        getRoutes().finish(note);
    }
    //endregion
}
