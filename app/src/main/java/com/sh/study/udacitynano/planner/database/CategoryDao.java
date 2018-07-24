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
 * Category interface
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-07-13
 */
@Dao
public interface CategoryDao {
    /**
     * Instert category
     * @param categoryEntity
     */
    @Insert
    void insertCategory(CategoryEntity categoryEntity);

    /**
     * Update category
     * @param categoryEntity
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateCategory(CategoryEntity categoryEntity);

    /**
     * Delete category
     * @param categoryEntity
     */
    @Delete
    void deleteCategory(CategoryEntity categoryEntity);

    /**
     * Fetch all categories
     * @return list of categories
     */
    @Query("SELECT * FROM category ORDER BY status, name")
    LiveData<List<CategoryEntity>> loadAllCategories();

    /**
     * Fetch all active categories
     * @return list of categories
     */
    @Query("SELECT * FROM category WHERE status = 1 ORDER BY status, name")
    LiveData<List<CategoryEntity>> loadActiveCategories();

    /**
     * Search around all categories
     * @param filteredText Fetch categories where name contain filtered text
     * @return list of filtered categories
     */
    @Query("SELECT * FROM category WHERE name LIKE :filteredText ORDER BY status, name")
    LiveData<List<CategoryEntity>> loadCategoriesByText(String filteredText);

    /**
     * Search around active categories
     * @param filteredText Fetch categories where name contain filtered text
     * @return list of active and filtered categories
     */
    @Query("SELECT * FROM category WHERE status = 1 AND name LIKE :filteredText ORDER BY status, name")
    LiveData<List<CategoryEntity>> loadActiveCategoriesByText(String filteredText);

    /**
     * Single record by Id
     * @param id Id of category to check if is not as parent
     * @return First existing record
     */
    @Query("SELECT * FROM category WHERE id = :id")
    LiveData<CategoryEntity> loadCategoryById(int id);

    /**
     * We can't delete category when there exist subcategory
     * @param id Id of category to check if is not as parent
     * @return First existing record
     */
    @Query("SELECT * FROM category WHERE parent_id = :id LIMIT 1")
    LiveData<CategoryEntity> isCategoryAsParent(int id);


}
