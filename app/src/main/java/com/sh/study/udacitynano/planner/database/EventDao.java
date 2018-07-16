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
    void insertEvent(EventEntity EventEntity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateEvent(EventEntity EventEntity);

    @Delete
    void deleteEvent(EventEntity EventEntity);

    @Query("SELECT * FROM event ORDER BY id")
    LiveData<List<EventEntity>> loadAllEvents();

    @Query("SELECT * FROM event WHERE category_id = :id ORDER BY id")
    LiveData<EventEntity> loadEventsByCategoryId(int id);
}
