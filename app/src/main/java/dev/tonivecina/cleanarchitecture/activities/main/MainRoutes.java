package dev.tonivecina.cleanarchitecture.activities.main;

import android.content.Context;
import android.content.Intent;

import dev.tonivecina.cleanarchitecture.activities.addnote.AddNoteActivity;

/**
 * @author Toni Vecina on 6/7/17.
 */

final class MainActivityRoutes {

    private MainActivity view;

    MainActivityRoutes(MainActivity view) {
        this.view = view;
    }

    void intentAddNoteActivity() {
        Intent intent = new Intent(view, AddNoteActivity.class);
        view.startActivityForResult(intent, MainActivity.REQUEST_CODE, null);
    }
}
