package com.sh.study.udacitynano.planner.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.sh.study.udacitynano.planner.constants.SHDebug;
import com.sh.study.udacitynano.planner.database.CategoryEntity;
import com.sh.study.udacitynano.planner.database.DatabaseRepository;

import java.util.List;
import java.util.Objects;

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

    // TODO V2
/*
    private final MutableLiveData<List<CategoryEntity>> categories = new MutableLiveData<>();
    private final MutableLiveData<List<CategoryEntity>> allCategories = new MutableLiveData<>();
    private String searchText;
    private boolean status;
*/


    ListViewModel(DatabaseRepository repository) {
        SHDebug.debugTag(CLASS_NAME, "constructor");
        this.repository = repository;
    }

    public LiveData<List<CategoryEntity>> getCategories(boolean active, String searchText) {
        SHDebug.debugTag(CLASS_NAME, "getCategories");
        searchText = "%" + searchText + "%";
        return repository.getFilteredCategoriesFromDB(active, searchText);
    }

    public LiveData<List<CategoryEntity>> getAllCategories(boolean active) {
        SHDebug.debugTag(CLASS_NAME, "getAllCategories");
        return repository.getCategoriesFromDB(active);
    }

// TODO: V2
/*

    public MutableLiveData<List<CategoryEntity>> getData(boolean active, String searchText) {
        SHDebug.debugTag(CLASS_NAME, "getData");
        if ((this.status == active) && (Objects.equals(this.searchText, searchText))) {
            return categories;
        } else {
            getCategories(active, searchText);
            return categories;
        }
    }

    public MutableLiveData<List<CategoryEntity>> getAllData(boolean active) {
        SHDebug.debugTag(CLASS_NAME, "getAllData");
        if (this.status == active) {
            return allCategories;
        } else {
            getAllCategories(active);
            return categories;
        }
    };


    public void getCategories(boolean active, String searchText) {
        SHDebug.debugTag(CLASS_NAME, "getCategories");
        if ((this.status == active) && (Objects.equals(this.searchText, searchText))) {
//            return categories;
        } else {
            this.searchText = searchText;
            this.status = active;

            searchText = "%" + searchText + "%";
            categories.setValue(repository.getFilteredCategoriesFromDB(active, searchText).getValue());
//            return categories;
        }
    }

    public void getAllCategories(boolean active) {
        SHDebug.debugTag(CLASS_NAME, "getAllCategories");
        if (this.status == active) {
//            return allCategories;
        } else {
            this.status = active;
            categories.setValue(repository.getCategoriesFromDB(active).getValue());
//            return categories;
        }
    }
*/
}