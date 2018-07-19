package com.sh.study.udacitynano.planner.database;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;
/**
 * Date converter
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
