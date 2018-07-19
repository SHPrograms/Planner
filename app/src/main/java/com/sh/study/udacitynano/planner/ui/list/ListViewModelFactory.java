package com.sh.study.udacitynano.planner.ui.list;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.sh.study.udacitynano.planner.database.DatabaseRepository;

/**
 * Factory for List ViewModel
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-07-19
 */
public class ListViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final DatabaseRepository repository;

    public ListViewModelFactory(DatabaseRepository repository) {
        this.repository = repository;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new ListViewModel(repository);
    }
}
