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
 * Operations on table category
 *
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-07-13
 */
@Dao
public interface CategoryDao {
    @Insert
    void insertCategory(CategoryEntry categoryEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCategory(CategoryEntry categoryEntry);

    @Delete
    void deleteCategory(CategoryEntry categoryEntry);

    @Query("SELECT * FROM category ORDER BY name")
    LiveData<List<CategoryEntry>> loadAllCategories();

    @Query("SELECT * FROM category WHERE parent_id = :id ORDER BY name")
    LiveData<CategoryEntry> loadCategoriesByParentId(int id);
}
