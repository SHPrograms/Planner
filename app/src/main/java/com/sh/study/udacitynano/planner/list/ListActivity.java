package com.sh.study.udacitynano.planner.list;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.widget.ListView;

import com.sh.study.udacitynano.planner.R;
import com.sh.study.udacitynano.planner.constants.SHDebug;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Main activity. List of triggers, categories
 *
 * Search function based on {@link "http://www.zoftino.com/android-search-functionality-using-searchview-and-room"}
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
    @BindView(R.id.search_results_list)
    public ListView listView;

    private SearchView searchView;
    private ListViewModel viewModel;
//    private LocalRepository localRepository = new LocalRepository();

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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent addCategory = new Intent(ListActivity.this, AddCategoryActivity.class);
                startActivity(addCategory);
            }
        });

        SHDebug.debugTag(CLASS_NAME, "onCreate:End");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SHDebug.debugTag(CLASS_NAME, "onCreateOptionsMenu:Start");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_list, menu);


        // Search view:
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(onQueryTextListener);

        return super.onCreateOptionsMenu(menu);
    }

    private SearchView.OnQueryTextListener onQueryTextListener =
            new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    getFilteredCategories(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    getFilteredCategories(newText);
                    return true;
                }

                private void getFilteredCategories(String searchText) {
                    searchText = "%"+searchText+"%";
                    viewModel.getCategories(searchText).observe(ListActivity.this, categoryEntities -> {
                        if (categoryEntities == null) {
                            return;
                        }
                        DealsListViewAdapter adapter = new DealsListViewAdapter(
                                ListActivity.this,
                                R.layout.deal_item_layout, categoryEntities);
                        listView.setAdapter(adapter);
                    });
                }
            };

/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SHDebug.debugTag(CLASS_NAME, "onOptionsItemSelected:Start");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
}
