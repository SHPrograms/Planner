package com.sh.study.udacitynano.planner.ui.list;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

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
public class ListActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.fab)
    public FloatingActionButton fab;

    private SearchView searchView;
    private ListViewModel viewModel;
    private ListFragment fragment;
    private String filteredText;

    private static final String CLASS_NAME = "ListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SHDebug.debugTag(CLASS_NAME, "onCreate:Start");
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);
        // TODO: Only for testing
//        Stetho.initializeWithDefaults(this);
        setSupportActionBar(toolbar);

        //TODO: FilteredText and status from onSaveInstanceState
        // here
        filteredText = "";

        fragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

        ListViewModelFactory factory = InjectorUtils.provideListActivityViewModelFactory(this.getApplicationContext());
        viewModel = ViewModelProviders.of(this, factory).get(ListViewModel.class);

        fab.setOnClickListener(view -> {
            SHDebug.debugTag(CLASS_NAME, "fab.setOnClickListener:Start");
            Intent addCategory = new Intent(ListActivity.this, CategoryActivity.class);
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
                // Filter categories
                if (ListPreferences.getListStatusPreferences(this)) {
                    ListPreferences.setListStatusPreferences(this, false);
                    item.setIcon(ContextCompat.getDrawable(this, android.R.drawable.checkbox_off_background));
                    getFilteredCategories(filteredText);

                    Toast.makeText(this, "All categories", Toast.LENGTH_SHORT).show();
                } else {
                    ListPreferences.setListStatusPreferences(this, true);
                    item.setIcon(ContextCompat.getDrawable(this, android.R.drawable.checkbox_on_background));
                    getFilteredCategories(filteredText);
                    Toast.makeText(this, "Only active categories", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.menu_list_item_action_data:
                Toast.makeText(this, "data selected", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (ListPreferences.getListStatusPreferences(this)) {
            menu.findItem(R.id.menu_list_item_action_filter).setIcon(ContextCompat.getDrawable(this, android.R.drawable.checkbox_on_background));
        } else {
            menu.findItem(R.id.menu_list_item_action_filter).setIcon(ContextCompat.getDrawable(this, android.R.drawable.checkbox_off_background));
        }
        return super.onPrepareOptionsMenu(menu);
    }

    private void getFilteredCategories(String searchText) {
        this.filteredText = searchText;
        viewModel.getCategories(ListPreferences.getListStatusPreferences(this), searchText).observe(ListActivity.this, categoryEntities -> {
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
            getFilteredCategories(query);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            SHDebug.debugTag(CLASS_NAME, "searchView.setOnQueryTextListener:onQueryTextChange, newText = " + newText);
            getFilteredCategories(newText);
            return true;
        }
    };
}