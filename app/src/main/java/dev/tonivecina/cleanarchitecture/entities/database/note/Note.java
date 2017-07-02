package dev.tonivecina.cleanarchitecture.entities.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Calendar;
import java.util.Date;

/**
 * @author Toni Vecina on 7/2/17.
 */

@Entity(tableName = "note")
public final class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "createdAt")
    private long createdAt;

    @ColumnInfo(name = "modifiedAt")
    private long modifiedAt;

    //region Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreatedAt() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(createdAt);

        return calendar.getTime();
    }

    public Date getModifiedAt() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(modifiedAt);

        return calendar.getTime();
    }
    //endregion
}
