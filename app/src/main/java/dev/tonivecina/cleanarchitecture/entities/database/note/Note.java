package dev.tonivecina.cleanarchitecture.entities.database.note;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Toni Vecina on 7/2/17.
 */

@Entity(tableName = "notes")
public final class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "createdAt")
    private long createdAt;

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

    public long getCreatedAt() {
        return createdAt;
    }

    @Ignore
    public Date getCreatedDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(createdAt);

        return calendar.getTime();
    }
    //endregion

    //region Setters
    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }
    //endregion
}
