package com.sh.study.udacitynano.planner.ui.category;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.sh.study.udacitynano.planner.constants.MyConstants;
import com.sh.study.udacitynano.planner.constants.SHDebug;
import com.sh.study.udacitynano.planner.database.CategoryEntity;
import com.sh.study.udacitynano.planner.database.DatabaseRepository;

/**
 * ViewModel for Category
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-07-23
 */
public class CategoryViewModel extends ViewModel {
    private static final String CLASS_NAME = "CategoryViewModel";
    private final DatabaseRepository repository;

    private LiveData<CategoryEntity> parentCategory;
    private LiveData<CategoryEntity> mainCategory;

    CategoryViewModel(DatabaseRepository repository, int parentCategoryItem, int mainCategoryItem) {
        SHDebug.debugTag(CLASS_NAME, "constructor");
        this.repository = repository;
        this.parentCategory = repository.getCategoryByIdFromDB(parentCategoryItem);
        this.mainCategory = repository.getCategoryByIdFromDB(mainCategoryItem);
    }

    public LiveData<CategoryEntity> getParentCategory() {
        SHDebug.debugTag(CLASS_NAME, "getParentCategory");
        return parentCategory;
    }

    public LiveData<CategoryEntity> getMainCategory() {
        SHDebug.debugTag(CLASS_NAME, "getMainCategory");
        return mainCategory;
    }

    public void insertCategory(String name) {
        SHDebug.debugTag(CLASS_NAME, "insertCategory");
        if (parentCategory.getValue() != null) {
            repository.setNewCategoryInDB(name, parentCategory.getValue().getId());
        } else {
            repository.setNewCategoryInDB(name, 0);
        }
    }
}

