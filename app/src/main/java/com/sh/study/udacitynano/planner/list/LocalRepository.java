package com.sh.study.udacitynano.planner.list;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.sh.study.udacitynano.planner.database.CategoryDao;
import com.sh.study.udacitynano.planner.database.CategoryEntity;
import com.sh.study.udacitynano.planner.database.PlannerDatabase;

import java.util.List;

/**
 * Temporary class for Mockups
 * based on {@link "http://www.zoftino.com/android-search-functionality-using-searchview-and-room"}
 *
 * TODO: RecyclerView for SeachView is possible?
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-07-16
 */
public class LocalRepository {
    private static PlannerDatabase dealsDatabase;
    private static final Object LOCK = new Object();

    public synchronized static PlannerDatabase getDealsDatabase(Context context) {
        if (dealsDatabase == null) {
            synchronized (LOCK) {
                if (dealsDatabase == null) {
                    // TODO: Must be one instance - plannerDatabase.class
                    dealsDatabase = Room.databaseBuilder(context,
                            PlannerDatabase.class, "planner2")
                            .fallbackToDestructiveMigration()
                            .addCallback(dbCallback).build();

                }
            }
        }
        return dealsDatabase;
    }
    public CategoryDao getDealsDAO(Context context){
        return getDealsDatabase(context).categoryDao();
    }

    public LiveData<List<CategoryEntity>> getDealsListInfo(Context context, String query){
        return getDealsDAO(context).getDealsList(query);
    }

    // TODO: What is the purpose for this in example from tutorial?
/*
    public Cursor getDealsCursor(Context context, String query){
        return getDealsDAO(context).getDealsCursor(query);
    }
*/

    private static RoomDatabase.Callback dbCallback = new RoomDatabase.Callback(){
        public void onCreate (SupportSQLiteDatabase db){

        }
        public void onOpen (SupportSQLiteDatabase db){
            //first delete existing data and insert laates deals
            db.execSQL("Delete From category");

            ContentValues contentValues = new ContentValues();
            contentValues.put("name", "Projekty");
            contentValues.put("parent_id", 0);
            db.insert("category", OnConflictStrategy.IGNORE, contentValues);

            contentValues = new ContentValues();
            contentValues.put("name", "Inne");
            contentValues.put("parent_id", 0);
            db.insert("category", OnConflictStrategy.IGNORE, contentValues);
        }
    };
}