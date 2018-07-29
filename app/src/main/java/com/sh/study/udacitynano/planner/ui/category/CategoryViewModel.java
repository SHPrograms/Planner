package com.sh.study.udacitynano.planner.ui.category;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

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
    private LiveData<Long> time;

    CategoryViewModel(DatabaseRepository repository, int parentCategoryItem, int mainCategoryItem) {
        SHDebug.debugTag(CLASS_NAME, "constructor");
        this.repository = repository;
        this.parentCategory = repository.getCategoryByIdFromDB(parentCategoryItem);
        this.mainCategory = repository.getCategoryByIdFromDB(mainCategoryItem);

        time = Transformations.switchMap(mainCategory, t -> {
            if (mainCategory.getValue() == null) {
                return repository.getTimeForCategoryFromDb(0);
            } else {
                return repository.getTimeForCategoryFromDb(mainCategory.getValue().getId());
            }
        });
    }

    public LiveData<CategoryEntity> getParentCategory() {
        SHDebug.debugTag(CLASS_NAME, "getParentCategory");
        return parentCategory;
    }

    public LiveData<CategoryEntity> getMainCategory() {
        SHDebug.debugTag(CLASS_NAME, "getMainCategory");
        return mainCategory;
    }

    public void setCategoryToDB(String name) {
        SHDebug.debugTag(CLASS_NAME, "setCategoryToDB");

        if (mainCategory.getValue() != null) {
            // Update method
            int id = mainCategory.getValue().getId();
            boolean status = mainCategory.getValue().getStatus();
            int parent = mainCategory.getValue().getParentId();

            final CategoryEntity categoryEntity = new CategoryEntity(id, name, parent, status);
            repository.setUpdateCategoryInDB(categoryEntity);
        } else if (parentCategory.getValue() != null) {
            // Insert method
            int parent = parentCategory.getValue().getId();
            final CategoryEntity categoryEntity = new CategoryEntity(name, parent, true);
            repository.setInsertCategoryInDB(categoryEntity);
        } else {
            final CategoryEntity categoryEntity = new CategoryEntity(name, 0, true);
            repository.setInsertCategoryInDB(categoryEntity);
        }
    }

    public void setAsInactiveStatusCategoryToDB(String name) {
        SHDebug.debugTag(CLASS_NAME, "setAsInactiveStatusCategoryToDB");
        if (mainCategory.getValue() != null) {
            CategoryEntity categoryEntity = mainCategory.getValue();
            categoryEntity.setName(name);
            categoryEntity.setStatus(false);
            repository.setUpdateCategoryInDB(categoryEntity);
        }
    }

    public LiveData<Long> getTime() {
        SHDebug.debugTag(CLASS_NAME, "getTime");
            return time;
    }
}

