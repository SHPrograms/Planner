package com.sh.study.udacitynano.planner.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

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
    private final DatabaseRepository repository;
//    private final LiveData<List<CategoryEntity>> categories;

    public ListViewModel(DatabaseRepository repository) {
        this.repository = repository;
//        this.categories = repository.getCategoriesFromDB();
    }

    public LiveData<List<CategoryEntity>> getCategories(String searchText) {
        return repository.getFilteredCategoriesFromDB(searchText);
    }

}
