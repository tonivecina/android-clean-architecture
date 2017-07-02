package dev.tonivecina.cleanarchitecture.activities.addnote;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import dev.tonivecina.cleanarchitecture.R;
import dev.tonivecina.cleanarchitecture.entities.database.note.Note;
import dev.tonivecina.cleanarchitecture.views.buttons.AddButton;

public class AddNoteActivity extends AppCompatActivity {
    public static final String BUNDLE_NOTE = "BUNDLE_NOTE";

    private EditText titleEditText;
    private EditText descriptionEditText;

    @SuppressWarnings("FieldCanBeLocal")
    private AddButton applyButton;

    @SuppressWarnings("FieldCanBeLocal")
    private AddNoteProcessor processor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        synchronized (this) {
            processor = new AddNoteProcessor(this);
        }

        titleEditText = (EditText) findViewById(R.id.activity_addNote_editText_title);
        descriptionEditText = (EditText) findViewById(R.id.activity_addNote_editText_description);

        View.OnClickListener onClickListener = processor.getOnClickListener();

        applyButton = (AddButton) findViewById(R.id.activity_addNote_button_apply);
        applyButton.setOnClickListener(onClickListener);

        processor.onCreate();
    }

    @Override
    public void onBackPressed() {
        setResult(Activity.RESULT_CANCELED);
        super.onBackPressed();
    }

    //region Getters
    String getTitleText() {
        return titleEditText.getText().toString();
    }

    String getDescriptionText() {
        return descriptionEditText.getText().toString();
    }
    //endregion
}
