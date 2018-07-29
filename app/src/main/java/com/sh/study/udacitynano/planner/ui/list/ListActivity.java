package com.sh.study.udacitynano.planner.ui.list;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;
import com.sh.study.udacitynano.planner.constants.MyConstants;
import com.sh.study.udacitynano.planner.database.CategoryEntity;
import com.sh.study.udacitynano.planner.utils.InjectorUtils;
import com.sh.study.udacitynano.planner.ui.category.CategoryActivity;
import com.sh.study.udacitynano.planner.R;
import com.sh.study.udacitynano.planner.constants.SHDebug;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilePermission;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Main list activity.
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-07-09
 */
public class ListActivity extends AppCompatActivity implements ListInterface {
    @BindView(R.id.toolbar_list)
    public Toolbar toolbar;
    @BindView(R.id.fab)
    public FloatingActionButton fab;
    @BindView(R.id.stop_event_button)
    Button stopEventButton;

    private static final String CLASS_NAME = "ListActivity";


    private ListViewModel viewModel;
    private ListFragment fragment;


    /*
        TODO: Save data using services:
        - send all events and categories
        - Use asyncTask for fetch data as report outside using services
        - delete not active categories and all events
        TODO: check if I have content descriptions and / or navigation using a D-pad.
        TODO: check if all strings are in a strings.xml file and enables RTL layout switching on all layouts.
        TODO: Sign apk - https://www.youtube.com/watch?v=xhQu7qyMqcI&feature=youtu.be
        TODO: Check if App builds and deploys using the installRelease Gradle task.
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SHDebug.debugTag(CLASS_NAME, "onCreate:Start");
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        // TODO: Only for testing
        Stetho.initializeWithDefaults(this);

        setSupportActionBar(toolbar);

        fragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        fragment.listAdapter.setClickHandler(this);

        ListViewModelFactory factory = InjectorUtils.provideListActivityViewModelFactory(
                this.getApplicationContext());
        viewModel = ViewModelProviders.of(this, factory).get(ListViewModel.class);

        getActiveEvent();

        fab.setOnClickListener(view -> {
            SHDebug.debugTag(CLASS_NAME, "fab.setOnClickListener:Start");
            Intent addCategory = new Intent(ListActivity.this, CategoryActivity.class);
            addCategory.putExtra(MyConstants.INTENT_PARENT_CATEGORY_ID, 0);
            addCategory.putExtra(MyConstants.INTENT_MAIN_CATEGORY_ID, 0);
            startActivity(addCategory);
        });

        stopEventButton.setOnClickListener((View v) -> {
            viewModel.setEventToDB(null);
            ListPreferences.setWidgetDataPreferences(getApplication(), this, "");
        });

        SHDebug.debugTag(CLASS_NAME, "onCreate:End");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SHDebug.debugTag(CLASS_NAME, "onCreateOptionsMenu:Start");
        getMenuInflater().inflate(R.menu.menu_list, menu);

        viewModel.setStatus(ListPreferences.getSourceStatusPreferences(this));

        SearchView searchView = (SearchView) menu.findItem(R.id.menu_list_item_action_search).getActionView();
        searchView.setOnQueryTextListener(onQueryTextListener);
        searchView.setQuery(viewModel.getSearchText(), true);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SHDebug.debugTag(CLASS_NAME, "onOptionsItemSelected:Start");

        switch (item.getItemId()) {
            case R.id.menu_list_item_action_search:
                // SearchView Filter for Categories
                //Toast.makeText(this, "sSnaearch selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_list_item_action_filter:
                if (viewModel.getStatus()) {
                    viewModel.setStatus(false);
                    item.setIcon(ContextCompat.getDrawable(this, android.R.drawable.checkbox_off_background));
                    Toast.makeText(this, "All categories", Toast.LENGTH_SHORT).show();
                } else {
                    viewModel.setStatus(true);
                    item.setIcon(ContextCompat.getDrawable(this, android.R.drawable.checkbox_on_background));
                    Toast.makeText(this, "Only active categories", Toast.LENGTH_SHORT).show();
                }
                getFilteredCategories(MyConstants.SOURCE_STATUS, "");
                break;
            case R.id.menu_list_item_action_data:
                /**
                 * TODO: Information for reviewer:
                 * This is for check service
                 */
//                Crashlytics.getInstance().crash();

                /**
                 * AsyncTask
                 */


                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        SHDebug.debugTag(CLASS_NAME, "onPrepareOptionsMenu:Start");
        if (viewModel.getStatus()) {
            menu.findItem(R.id.menu_list_item_action_filter).setIcon(ContextCompat.getDrawable(this, android.R.drawable.checkbox_on_background));
        } else {
            menu.findItem(R.id.menu_list_item_action_filter).setIcon(ContextCompat.getDrawable(this, android.R.drawable.checkbox_off_background));
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void getFilteredCategories(String source, String searchText) {
        SHDebug.debugTag(CLASS_NAME, "getFilteredCategories:Start");
        viewModel.getCategories(source, searchText).observe(ListActivity.this, categoryEntities -> {
            SHDebug.debugTag(CLASS_NAME, "getFilteredCategories, size: " + categoryEntities.size());
            fragment.listAdapter.setDataList(categoryEntities);
        });
    }

    private void getActiveEvent() {
        SHDebug.debugTag(CLASS_NAME, "getActiveEvent:Start");
        viewModel.getActiveEvent().observe(ListActivity.this, activeEvent -> {
            SHDebug.debugTag(CLASS_NAME, "getActiveEvent:observe");
            if (activeEvent != null) {
                SHDebug.debugTag(CLASS_NAME, "getActiveEvent:observe, event: " + activeEvent.getId());
                fragment.recyclerView.setVisibility(View.INVISIBLE);
                fab.setVisibility(View.INVISIBLE);
                stopEventButton.setVisibility(View.VISIBLE);
            } else {
                fragment.recyclerView.setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
                stopEventButton.setVisibility(View.INVISIBLE);
            }
        });
    }

    /**
     * SearchView Listener. Fetching data into RecyclerView in Fragment {R.id.fragment}
     */
    private SearchView.OnQueryTextListener onQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            SHDebug.debugTag(CLASS_NAME, "searchView.setOnQueryTextListener:onQueryTextSubmit, query = " + query);
            getFilteredCategories(MyConstants.SOURCE_QUERY, query);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            SHDebug.debugTag(CLASS_NAME, "searchView.setOnQueryTextListener:onQueryTextChange, newText = " + newText);
            getFilteredCategories(MyConstants.SOURCE_QUERY, newText);
            return true;
        }
    };

    @Override
    public void onCategoryClick(CategoryEntity category, View view) {
        viewModel.setEventToDB(category);
        ListPreferences.setWidgetDataPreferences(getApplication(), this, category.getName());
    }


    @Override
    protected void onPause() {
        super.onPause();
        SHDebug.debugTag(CLASS_NAME, "onSaveInstanceState:start");
        ListPreferences.setSourceStatusPreferences(this, viewModel.getStatus());
    }
}