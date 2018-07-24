package com.sh.study.udacitynano.planner.ui.category;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.sh.study.udacitynano.planner.database.CategoryEntity;
import com.sh.study.udacitynano.planner.database.DatabaseRepository;

/**
 * Factory for Category ViewModel
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-07-23
 */
public class CategoryViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final DatabaseRepository repository;
    private final int parentCategoryItem;
    private final int mainCategoryItem;

    public CategoryViewModelFactory(DatabaseRepository repository, int parentCategoryId, int mainCategoryId) {
        this.repository = repository;
        this.parentCategoryItem = parentCategoryId;
        this.mainCategoryItem = mainCategoryId;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return (T) new CategoryViewModel(repository, parentCategoryItem, mainCategoryItem);
    }
}
