package dev.tonivecina.cleanarchitecture.activities.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import dev.tonivecina.cleanarchitecture.DLog;
import dev.tonivecina.cleanarchitecture.R;
import dev.tonivecina.cleanarchitecture.activities.addnote.AddNoteActivity;
import dev.tonivecina.cleanarchitecture.entities.database.note.Note;
import dev.tonivecina.cleanarchitecture.views.buttons.AddButton;

/**
 * @author Toni Vecina on 6/7/17.
 */

public class MainActivity extends AppCompatActivity {

    static final int REQUEST_CODE = 1001;

    @SuppressWarnings("FieldCanBeLocal")
    private RecyclerView noteRecyclerView;

    @SuppressWarnings("FieldCanBeLocal")
    private AddButton addButton;

    private MainRecyclerViewNoteAdapter noteAdapter;

    @SuppressWarnings("FieldCanBeLocal")
    private MainProcessor mProcessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        synchronized (this) {
            mProcessor = new MainProcessor(this);
            noteAdapter = new MainRecyclerViewNoteAdapter();
        }

        noteRecyclerView = (RecyclerView) findViewById(R.id.activity_main_recyclerView);
        noteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteRecyclerView.setItemAnimator(new DefaultItemAnimator());
        noteRecyclerView.setAdapter(noteAdapter);

        View.OnClickListener onClickListener = mProcessor.getOnClickListener();

        addButton = (AddButton) findViewById(R.id.activity_main_button_add);
        addButton.setOnClickListener(onClickListener);

        mProcessor.onCreate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            onNoteActivityResult(resultCode, data);
        }
    }

    //region Private methods
    private void onNoteActivityResult(int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK) {
            DLog.info("Note creation was cancelled");
            return;
        }

        Note note = (Note) data.getSerializableExtra(AddNoteActivity.BUNDLE_NOTE);

        if (note != null) {
            addNote(note);
        }
    }
    //endregion

    //region Setters
    void setNotes(final List<Note> notes) {
        noteAdapter.setNotes(notes);
        noteAdapter.notifyDataSetChanged();
    }

    void addNote(final Note note) {
        noteAdapter.addNote(note);
        noteAdapter.notifyDataSetChanged();
    }
    //endregion
}
