package com.sh.study.udacitynano.planner.database;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;
/**
 * Date converter
 * Based on {@link "https://medium.com/@chrisbanes/room-time-2b4cf9672b98"}
 * Based on {@Link "https://github.com/udacity/ud851-Exercises/tree/student/Lesson09b-ToDo-List-AAC"}
 *
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-07-13
 */
public class DateConverter {
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
