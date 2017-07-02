package dev.tonivecina.cleanarchitecture.configuration;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import dev.tonivecina.cleanarchitecture.entities.database.note.Note;
import dev.tonivecina.cleanarchitecture.entities.database.note.NoteDao;

/**
 * @author Toni Vecina on 7/2/17.
 */

@Database(entities = {Note.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {
    public abstract NoteDao noteDao();
}
