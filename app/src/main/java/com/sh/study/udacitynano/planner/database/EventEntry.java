package com.sh.study.udacitynano.planner.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Events
 *
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-07-13
 */
@Entity(tableName = "event")
public class EventEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "date_start")
    private Date dateStart;
    @ColumnInfo(name = "date_end")
    private Date dateEnd;
    private long time;
    private String description;
    @ColumnInfo(name = "category_id")
    private int categoryId;
    @ColumnInfo(name = "updated_at")
    private Date updatedAt;

    @Ignore
    public EventEntry(Date dateStart, Date dateEnd, long time, String description, int categoryId, Date updatedAt) {
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.time = time;
        this.description = description;
        this.categoryId = categoryId;
        this.updatedAt = updatedAt;
    }

    public EventEntry(int id, Date dateStart, Date dateEnd, long time, String description, int categoryId, Date updatedAt) {
        this.id = id;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.time = time;
        this.description = description;
        this.categoryId = categoryId;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}