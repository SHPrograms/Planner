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
 * Event interface
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

    /**
     * There is no option for multi running events so previous must be closed before run next one
     * @return single running Event
     */
    @Query("SELECT * FROM event WHERE time = 0 LIMIT 1")
    LiveData<EventEntity> loadRunningEvent();

    @Query("SELECT SUM(time) FROM event WHERE category_id = :id")
    LiveData<Long> loadEventsTimeForCategory(int id);

/*
    @Query("SELECT * FROM event ORDER BY id")
    LiveData<List<EventEntity>> loadAllEvents();

    @Query("SELECT * FROM event WHERE category_id = :id ORDER BY id")
    LiveData<EventEntity> loadEventsByCategoryId(int id);
*/
}
