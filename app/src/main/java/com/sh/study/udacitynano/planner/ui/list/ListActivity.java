package com.sh.study.udacitynano.planner.ui.list;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.sh.study.udacitynano.planner.constants.MyConstants;
import com.sh.study.udacitynano.planner.database.CategoryEntity;
import com.sh.study.udacitynano.planner.utils.InjectorUtils;
import com.sh.study.udacitynano.planner.ui.category.CategoryActivity;
import com.sh.study.udacitynano.planner.R;
import com.sh.study.udacitynano.planner.constants.SHDebug;

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

    private SearchView searchView;
    private ListViewModel viewModel;
    private ListFragment fragment;

    private View runningEvent;

    private static final String CLASS_NAME = "ListActivity";

    /*
        TODO list:
        TODO: Category details: swipe right:
        - add new caption with information how much time is in events
        TODO: Widget
        TODO: Save data using services
        TODO: When run detail activity -> orientation change -> back to main = not saved by ViewModel!
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
        fragment.listAdapter.setClickHandler(this::onCategoryClick);

        ListViewModelFactory factory = InjectorUtils.provideListActivityViewModelFactory(this.getApplicationContext());
        viewModel = ViewModelProviders.of(this, factory).get(ListViewModel.class);

        fab.setOnClickListener(view -> {
            SHDebug.debugTag(CLASS_NAME, "fab.setOnClickListener:Start");
            Intent addCategory = new Intent(ListActivity.this, CategoryActivity.class);
            addCategory.putExtra(MyConstants.INTENT_PARENT_CATEGORY_ID, 0);
            addCategory.putExtra(MyConstants.INTENT_MAIN_CATEGORY_ID, 0);
            startActivity(addCategory);
        });
        SHDebug.debugTag(CLASS_NAME, "onCreate:End");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SHDebug.debugTag(CLASS_NAME, "onCreateOptionsMenu:Start");
        getMenuInflater().inflate(R.menu.menu_list, menu);
        searchView = (SearchView) menu.findItem(R.id.menu_list_item_action_search).getActionView();
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
                //Toast.makeText(this, "search selected", Toast.LENGTH_SHORT).show();
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
                // TODO: Temporary:
                getFilteredCategories(MyConstants.SOURCE_STATUS, "");
                break;
            case R.id.menu_list_item_action_data:
                // TODO: Implement services
                Toast.makeText(this, "Create report", Toast.LENGTH_SHORT).show();
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
        viewModel.getCategories(source, searchText).observe(ListActivity.this, categoryEntities -> {

            fragment.listAdapter.setDataList(categoryEntities);
        });

        //TODO: V2
/*
        viewModel.getData(ListPreferences.getListStatusPreferences(this), searchText).observe(ListActivity.this, categoryEntities -> {
            fragment.listAdapter.setDataList(categoryEntities);
        });
*/
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
        Snackbar.make(getCurrentFocus(), "Event is started", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        /*
            check if another event is running
                yes: close previous and open this*
                no: open this*

            open this event:
                - keep information in ViewModel or here to change color back
                - send broadcast for widget
                - save event into database with only start date
                - create a new color resource for background

            close previous event:
                - if another category is clicked previous must be closed
                - find view and change color
                - save end date and time as long

            close this event:
                - view is known - no problem to finish
                - save end date and time as long
                - send broadcast for widget

            refresh color when activity is created after orientation changes

        */

        view.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));


    }
}