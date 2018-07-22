package com.sh.study.udacitynano.planner.ui.list;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.sh.study.udacitynano.planner.utils.InjectorUtils;
import com.sh.study.udacitynano.planner.ui.category.CategoryActivity;
import com.sh.study.udacitynano.planner.R;
import com.sh.study.udacitynano.planner.constants.SHDebug;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Main list activity.
 * <p>
 * Search function based on {@link "http://www.zoftino.com/android-search-functionality-using-searchview-and-room"}
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-07-09
 */
public class ListActivity extends AppCompatActivity implements ListInterface {
    @BindView(R.id.toolbar)
    public Toolbar toolbar;
    @BindView(R.id.fab)
    public FloatingActionButton fab;
    @BindView(R.id.search_results_list)
    public RecyclerView recyclerView;

    private SearchView searchView;
    private ListViewModel viewModel;

    private ListAdapter listAdapter;

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

        ListViewModelFactory factory = InjectorUtils.provideListActivityViewModelFactory(this.getApplicationContext());
        viewModel = ViewModelProviders.of(this, factory).get(ListViewModel.class);


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listAdapter = new ListAdapter(this);
        recyclerView.setAdapter(listAdapter);

        viewModel.getAllCategories().observe(ListActivity.this, items -> {
            listAdapter.setDataList(items);
        });

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
//        searchView.setSubmitButtonEnabled(false);
        searchView.setOnQueryTextListener(onQueryTextListener);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SHDebug.debugTag(CLASS_NAME, "onOptionsItemSelected:Start");
        switch (item.getItemId()) {
            case R.id.menu_list_item_action_search:
                Toast.makeText(this, "search selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_list_item_action_filter:
                Toast.makeText(this, "filter selected", Toast.LENGTH_SHORT).show();
                break;
            case R.id.menu_list_item_action_data:
                Toast.makeText(this, "data selected", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Listener for search view in main list view
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


        private void getFilteredCategories(String searchText) {
            searchText = "%" + searchText + "%";

            viewModel.getCategories(searchText).observe(ListActivity.this, categoryEntities -> {
                if (categoryEntities == null) {
                    return;
                }
                listAdapter.setDataList(categoryEntities);
            });
        }
    };
}