package com.sh.study.udacitynano.planner.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.sh.study.udacitynano.planner.constants.SHDebug;
import com.sh.study.udacitynano.planner.database.CategoryEntity;
import com.sh.study.udacitynano.planner.database.DatabaseRepository;

import java.util.List;

/**
 * ViewModel for main view - List
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-07-19
 */
public class ListViewModel extends ViewModel {
    private static final String CLASS_NAME = "ListViewModel";
    private final DatabaseRepository repository;

    ListViewModel(DatabaseRepository repository) {
        SHDebug.debugTag(CLASS_NAME, "constructor");
        this.repository = repository;
    }
    public LiveData<List<CategoryEntity>> getCategories(String searchText) {
        return repository.getFilteredCategoriesFromDB(searchText);
    }

    public LiveData<List<CategoryEntity>> getAllCategories() {
        return repository.getCategoriesFromDB();
    }
}
