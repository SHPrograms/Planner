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
    private static AppExecutors executors; // TODO: Do I need that?

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

    public LiveData<List<CategoryEntity>> getFilteredCategoriesFromDB(boolean active, String query){
        SHDebug.debugTag(CLASS_NAME, "getFilteredCategoriesFromDB");
        if (active) return plannerDatabase.categoryDao().loadActiveCategoriesByText(query);
        else return plannerDatabase.categoryDao().loadCategoriesByText(query);
    }

    public LiveData<List<CategoryEntity>> getCategoriesFromDB(boolean active) {
        SHDebug.debugTag(CLASS_NAME, "getCategoriesFromDB");
        if (active) return plannerDatabase.categoryDao().loadActiveCategories();
        else return plannerDatabase.categoryDao().loadAllCategories();
    }

    public LiveData<CategoryEntity> getCategoryByIdFromDB(int id) {
        SHDebug.debugTag(CLASS_NAME, "getCategoryByIdFromDB");
         return plannerDatabase.categoryDao().loadCategoryById(id);
    }

    public void setInsertCategoryInDB(CategoryEntity insertCategory) {
        SHDebug.debugTag(CLASS_NAME, "setInsertCategoryInDB");
        executors.diskIO().execute(() -> plannerDatabase.categoryDao().insertCategory(insertCategory));
    }

    public void setUpdateCategoryInDB(CategoryEntity updateCategory) {
        SHDebug.debugTag(CLASS_NAME, "setUpdateCategoryInDB");
        executors.diskIO().execute(() -> plannerDatabase.categoryDao().updateCategory(updateCategory));
    }
}
