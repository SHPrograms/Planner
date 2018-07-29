package com.sh.study.udacitynano.planner.ui.list;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.sh.study.udacitynano.planner.constants.MyConstants;
import com.sh.study.udacitynano.planner.constants.SHDebug;
import com.sh.study.udacitynano.planner.database.CategoryEntity;
import com.sh.study.udacitynano.planner.database.DatabaseRepository;
import com.sh.study.udacitynano.planner.database.EventEntity;

import java.util.Date;
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

    private LiveData<EventEntity> activeEvent;

    private boolean status;
    private String searchText;


    ListViewModel(DatabaseRepository repository) {
        SHDebug.debugTag(CLASS_NAME, "constructor");
        this.repository = repository;
    }

    public LiveData<EventEntity> getActiveEvent() {
        return activeEvent = repository.getActiveEvent();
    }

    public String getSearchText() {
        return searchText;
    }

    public void setStatus(boolean status) {
        SHDebug.debugTag(CLASS_NAME, "setStatus");
        this.status = status;
    }

    public boolean getStatus() {
        return status;
    }


    public LiveData<List<CategoryEntity>> getCategories(String source, String searchText) {
        SHDebug.debugTag(CLASS_NAME, "getCategories");

        if (source.equals(MyConstants.SOURCE_STATUS)) {
            searchText = "%" + this.searchText + "%";
        } else {
            this.searchText = searchText;
            searchText = "%" + searchText + "%";
        }
        if (this.searchText.isEmpty()) {
            SHDebug.debugTag(CLASS_NAME, "getCategories: all, " + this.searchText);
            return repository.getCategoriesFromDB(status);
        } else {
            SHDebug.debugTag(CLASS_NAME, "getCategories: filtered, " + this.searchText);
            return repository.getFilteredCategoriesFromDB(status, searchText);
        }
    }

    public void setEventToDB(CategoryEntity category) {
        Date now = new Date();
        if (category != null) {
            SHDebug.debugTag(CLASS_NAME, "setEventToDB: just create a new one");
            final EventEntity entity = new EventEntity(now, null, 0, category.getId());
            repository.setInsertActiveEventToDB(entity);
        } else if (activeEvent.getValue() != null) {
            SHDebug.debugTag(CLASS_NAME, "setEventToDB: Just save it");
            EventEntity entity = activeEvent.getValue();
            entity.setDateEnd(now);
            entity.setTime(now.getTime() - entity.getDateStart().getTime());
            repository.setUpdateActiveEventToDB(entity);
        } else {
            throw new UnsupportedOperationException("Not implemented yet.");
        }
    }
}
