package com.sh.study.udacitynano.planner.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Operations on table event
 *
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-07-13
 */
@Dao
public interface EventDao {
    @Insert
    void insertEvent(EventEntry EventEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateEvent(EventEntry EventEntry);

    @Delete
    void deleteEvent(EventEntry EventEntry);

    @Query("SELECT * FROM Event ORDER BY id")
    LiveData<List<EventEntry>> loadAllEvents();

    @Query("SELECT * FROM Event WHERE category_id = :id ORDER BY id")
    LiveData<EventEntry> loadEventsByCategoryId(int id);
}
