package com.sh.study.udacitynano.planner.utils;

import android.content.Context;

import com.sh.study.udacitynano.planner.constants.SHDebug;
import com.sh.study.udacitynano.planner.database.DatabaseRepository;
import com.sh.study.udacitynano.planner.database.PlannerDatabase;
import com.sh.study.udacitynano.planner.ui.list.ListViewModelFactory;

/**
 * Connections between repositories
 *
 * Class based on {@link "https://github.com/googlecodelabs/android-build-an-app-architecture-components"}
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-07-19
 */
public class InjectorUtils {
    private static final String CLASS_NAME = "InjectorUtils";

    private static DatabaseRepository provideRepository(Context context) {
        SHDebug.debugTag(CLASS_NAME, "provideRepository");
        PlannerDatabase database = PlannerDatabase.getInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        return DatabaseRepository.getInstance(database, executors);
    }

    public static ListViewModelFactory provideListActivityViewModelFactory(Context context) {
        SHDebug.debugTag(CLASS_NAME, "provideListActivityViewModelFactory");
        DatabaseRepository repository = provideRepository(context.getApplicationContext());
        return new ListViewModelFactory(repository);
    }
}
