package dev.tonivecina.cleanarchitecture.entities.database.note;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * @author Toni Vecina on 7/2/17.
 */

@Dao
public interface NoteDao {
    @Query("SELECT * FROM notes")
    List<Note> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note note);
}
