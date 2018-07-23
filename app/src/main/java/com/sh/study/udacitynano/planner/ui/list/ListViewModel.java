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

    // TODO: V2
/*
    public LiveData<List<CategoryEntity>> getCategories() {
        SHDebug.debugTag(CLASS_NAME, "getCategories");
        if (categories == null) {
            SHDebug.debugTag(CLASS_NAME, "getCategories create MutableLiveData");
            categories = new MutableLiveData<>();
            //TODO: Here should be an observer orTransformations? to fetch data from Room into
            categories.setValue(repository.getCategoriesFromDB().getValue());
        }
        return categories;
    }

    public void setCategories(String searchText) {
        SHDebug.debugTag(CLASS_NAME, "setCategories");
        if (categories == null) {
            SHDebug.debugTag(CLASS_NAME, "setCategories create MutableLiveData");
            categories = new MutableLiveData<>();
        }
        //TODO: Here should be an observer orTransformations? to fetch data from Room into
        categories.setValue(repository.getFilteredCategoriesFromDB(searchText).getValue());
    }
*/

//    categories = Transformations.switchMap(repository.getFilteredCategoriesFromDB(searchText), )

// TODO: V1
    public LiveData<List<CategoryEntity>> getCategories(boolean active, String searchText) {
        searchText = "%" + searchText + "%";
        return repository.getFilteredCategoriesFromDB(active, searchText);
    }

    public LiveData<List<CategoryEntity>> getAllCategories(boolean active) {
        return repository.getCategoriesFromDB(active);
    }
}