package com.sh.study.udacitynano.planner.database;

import android.arch.lifecycle.LiveData;

import com.sh.study.udacitynano.planner.utils.AppExecutors;
import com.sh.study.udacitynano.planner.constants.SHDebug;

import java.util.List;

/**
 * Database repository
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-07-19
 */
public class DatabaseRepository {
    private static final String CLASS_NAME = "DatabaseRepository";
    private static final Object LOCK = new Object();

    private static DatabaseRepository sInstance;
    private static PlannerDatabase plannerDatabase;
    private static AppExecutors executors;

    private DatabaseRepository(PlannerDatabase plannerDatabase, AppExecutors executors) {
        SHDebug.debugTag(CLASS_NAME, "constructor");
        this.plannerDatabase = plannerDatabase;
        this.executors = executors;
  }

    public synchronized static DatabaseRepository getInstance(PlannerDatabase plannerDatabase, AppExecutors executors) {
        SHDebug.debugTag(CLASS_NAME, "getInstance");
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = new DatabaseRepository(plannerDatabase, executors);
                SHDebug.debugTag(CLASS_NAME, "getInstance: new instance");
            }
        }
        return sInstance;
    }

    /**
     * Query to DB - List of categories depends on search and active filter
     * @param active - Active or all
     * @param query - Search filter
     * @return LiveData List of categories
     */
    public LiveData<List<CategoryEntity>> getFilteredCategoriesFromDB(boolean active, String query){
        SHDebug.debugTag(CLASS_NAME, "getFilteredCategoriesFromDB");
        if (active) return plannerDatabase.categoryDao().loadActiveCategoriesByText(query);
        else return plannerDatabase.categoryDao().loadCategoriesByText(query);
    }

    /**
     * Query to DB - List of categories depends on active filter
     * @param active - Active or all
     * @return LiveData List of categories
     */
    public LiveData<List<CategoryEntity>> getCategoriesFromDB(boolean active) {
        SHDebug.debugTag(CLASS_NAME, "getCategoriesFromDB");
        if (active) return plannerDatabase.categoryDao().loadActiveCategories();
        else return plannerDatabase.categoryDao().loadAllCategories();
    }

    /**
     * Query to DB - Single category by id
     * @param id - Id filter
     * @return LiveData category
     */
    public LiveData<CategoryEntity> getCategoryByIdFromDB(int id) {
        SHDebug.debugTag(CLASS_NAME, "getCategoryByIdFromDB");
         return plannerDatabase.categoryDao().loadCategoryById(id);
    }

    /**
     * Insert to DB - Single new category
     * @param insertCategory - new category
     */
    public void setInsertCategoryInDB(CategoryEntity insertCategory) {
        SHDebug.debugTag(CLASS_NAME, "setInsertCategoryInDB");
        executors.diskIO().execute(() -> plannerDatabase.categoryDao().insertCategory(insertCategory));
    }

    /**
     * Update to DB - Single category
     * @param updateCategory - category
     */
    public void setUpdateCategoryInDB(CategoryEntity updateCategory) {
        SHDebug.debugTag(CLASS_NAME, "setUpdateCategoryInDB");
        executors.diskIO().execute(() -> plannerDatabase.categoryDao().updateCategory(updateCategory));
    }

    /**
     * Query to DB - Single running event (no end data)
     * @return event
     */
    public LiveData<EventEntity> getActiveEvent() {
        SHDebug.debugTag(CLASS_NAME, "getActiveEvent");
        return plannerDatabase.eventDao().loadRunningEvent();
    }

    public void setInsertActiveEventToDB(EventEntity newEvent) {
        SHDebug.debugTag(CLASS_NAME, "setActiveEvent");
        executors.diskIO().execute(() -> plannerDatabase.eventDao().insertEvent(newEvent));
    }

    public void setUpdateActiveEventToDB(EventEntity activeEvent) {
        SHDebug.debugTag(CLASS_NAME, "setActiveEvent");
        executors.diskIO().execute(() -> plannerDatabase.eventDao().updateEvent(activeEvent));
    }

    public LiveData<Long> getTimeForCategoryFromDb(int id) {
        SHDebug.debugTag(CLASS_NAME, "getTimeForCategoryFromDb");
        return plannerDatabase.eventDao().loadEventsTimeForCategory(id);
    }
}
