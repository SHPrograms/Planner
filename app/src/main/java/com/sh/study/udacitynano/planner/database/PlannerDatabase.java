package com.sh.study.udacitynano.planner.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.ContentValues;
import android.content.Context;

import com.sh.study.udacitynano.planner.constants.SHDebug;

/**
 * Database instance
 *
 * Adventure with Android Architecture Components wouldn't be possible without support from:
 * {@Link "https://github.com/udacity/ud851-Exercises/tree/student/Lesson09b-ToDo-List-AAC"}
 * {@link "https://github.com/googlecodelabs/android-build-an-app-architecture-components"}
 * {@link "http://www.zoftino.com/android-search-functionality-using-searchview-and-room"}
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-07-13
 */
@Database(entities = {EventEntity.class, CategoryEntity.class}, version = 1, exportSchema = false)
@TypeConverters(DateConverter.class)
public abstract class PlannerDatabase extends RoomDatabase {
    private static final String CLASS_NAME = "PlannerDatabase";

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "planner";
    private static PlannerDatabase sInstance;

    public static PlannerDatabase getInstance(Context context) {
        SHDebug.debugTag(CLASS_NAME, "getInstance:start");
        if (sInstance == null) {
            synchronized (LOCK) {
                SHDebug.debugTag(CLASS_NAME, "getInstance:create");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        PlannerDatabase.class, PlannerDatabase.DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .addCallback(callback) // TODO: temporary!
                        .build();
            }
        }
        return sInstance;
    }

    public abstract EventDao eventDao();

    public abstract CategoryDao categoryDao();

    // TODO: Temporary!

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        public void onCreate(SupportSQLiteDatabase db) {

        }

        public void onOpen(SupportSQLiteDatabase db) {
            db.execSQL("Delete From category");
            db.execSQL("Delete From event");

            ContentValues contentValues = new ContentValues();

            contentValues.put("name", "Popular Movies");
            contentValues.put("parent_id", 0);
            contentValues.put("status", 0);
            db.insert("category", OnConflictStrategy.IGNORE, contentValues);

            contentValues.put("name", "Build It Bigger");
            contentValues.put("parent_id", 0);
            contentValues.put("status", 0);
            db.insert("category", OnConflictStrategy.IGNORE, contentValues);

            contentValues.put("name", "Capstone, Stage 1 - Design");
            contentValues.put("parent_id", 0);
            contentValues.put("status", 0);
            db.insert("category", OnConflictStrategy.IGNORE, contentValues);

            contentValues.put("name", "Capstone, Stage 2 - Build");
            contentValues.put("parent_id", 0);
            contentValues.put("status", 1);
            db.insert("category", OnConflictStrategy.IGNORE, contentValues);
        }
    };
}
