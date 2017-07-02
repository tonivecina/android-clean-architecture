package dev.tonivecina.cleanarchitecture.activities.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import dev.tonivecina.cleanarchitecture.R;
import dev.tonivecina.cleanarchitecture.entities.database.note.Note;

/**
 * @author Toni Vecina on 7/2/17.
 */

final class MainActivityRecyclerViewNoteAdapter extends RecyclerView.Adapter<MainActivityViewHolderNote> {

    private List<Note> notes = new ArrayList<>();
    private Context context;

    @Override
    public MainActivityViewHolderNote onCreateViewHolder(ViewGroup parent, int viewType) {

        if (context == null) {
            context = parent.getContext();
        }

        View holder = LayoutInflater.from(context).inflate(R.layout.cardview_note, parent, false);

        return new MainActivityViewHolderNote(holder);
    }

    @Override
    public void onBindViewHolder(MainActivityViewHolderNote holder, int position) {
        Note note = notes.get(position);
        holder.setView(note);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    //region Management
    void setNotes(final List<Note> notes) {
        this.notes.clear();
        this.notes.addAll(notes);
        notifyDataSetChanged();
    }

    void addNote(final Note note) {
        notes.add(0, note);
        notifyDataSetChanged();
    }

    void updateNote(final int index, final Note note) {
        notes.remove(index);
        notes.add(index, note);
        notifyDataSetChanged();
    }
    //endregion
}
