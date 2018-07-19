package com.sh.study.udacitynano.planner.list;

import android.content.Context;

import com.sh.study.udacitynano.planner.AppExecutors;
import com.sh.study.udacitynano.planner.database.DatabaseRepository;
import com.sh.study.udacitynano.planner.database.PlannerDatabase;

/**
 * Connections between repositories
 *
 * TODO: Dagger
 *
 * Temporary version based on {@link "https://github.com/googlecodelabs/android-build-an-app-architecture-components"}
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-07-19
 */
public class InjectorUtils {

    public static DatabaseRepository provideRepository(Context context) {
        PlannerDatabase database = PlannerDatabase.getInstance(context.getApplicationContext());
        AppExecutors executors = AppExecutors.getInstance();
        return DatabaseRepository.getInstance(database, executors);
    }

    public static ListViewModelFactory provideListActivityViewModelFactory(Context applicationContext) {
        DatabaseRepository repository = provideRepository(applicationContext.getApplicationContext());
        return new ListViewModelFactory(repository);
    }
}
