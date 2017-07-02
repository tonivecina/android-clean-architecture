package dev.tonivecina.cleanarchitecture.activities.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import dev.tonivecina.cleanarchitecture.R;
import dev.tonivecina.cleanarchitecture.entities.database.note.Note;
import dev.tonivecina.cleanarchitecture.views.textviews.DateTextView;
import dev.tonivecina.cleanarchitecture.views.textviews.TitleTextView;

/**
 * @author Toni Vecina on 7/2/17.
 */

final class MainActivityViewHolderNote extends RecyclerView.ViewHolder {

    private TitleTextView titleTextView;
    private DateTextView dateTextView;

    MainActivityViewHolderNote(View itemView) {
        super(itemView);

        titleTextView = (TitleTextView) itemView.findViewById(R.id.cardView_note_textView_title);
        dateTextView = (DateTextView) itemView.findViewById(R.id.cardView_note_textView_date);
    }

    void setView(final Note note) {
        titleTextView.setText(note.getTitle());
        dateTextView.setDate(note.getModifiedDate(), "EEE dd, MMM");
    }
}
