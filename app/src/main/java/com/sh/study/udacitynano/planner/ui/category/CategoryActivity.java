package com.sh.study.udacitynano.planner.ui.category;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.sh.study.udacitynano.planner.R;
import com.sh.study.udacitynano.planner.constants.MyConstants;
import com.sh.study.udacitynano.planner.constants.SHDebug;
import com.sh.study.udacitynano.planner.database.CategoryEntity;
import com.sh.study.udacitynano.planner.utils.InjectorUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Single category activity
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-07-09
 */
public class CategoryActivity extends AppCompatActivity {
    @BindView(R.id.toolbar_category)
    public Toolbar toolbar;
    @BindView(R.id.fab_category)
    public FloatingActionButton fab;
    @BindView(R.id.new_category_search_label)
    public TextView newCategorySearchLabel;

    private CategoryFragment fragment;

    private static final String CLASS_NAME = "CategoryActivity";
    private CategoryViewModel viewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SHDebug.debugTag(CLASS_NAME, "onCreate:Start");
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        fragment = (CategoryFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_new_category);

        CategoryViewModelFactory factory = InjectorUtils.provideCategoryActivityViewModelFactory(
                this.getApplicationContext(),
                getIntent().getIntExtra(MyConstants.INTENT_PARENT_CATEGORY_ID, 0),
                getIntent().getIntExtra(MyConstants.INTENT_MAIN_CATEGORY_ID, 0));
        viewModel = ViewModelProviders.of(this, factory).get(CategoryViewModel.class);

        viewModel.getParentCategory().observe(this, categoryEntity -> {
            if (categoryEntity == null) {
                newCategorySearchLabel.setVisibility(View.GONE);
            } else {
                newCategorySearchLabel.setText(
                        String.format(getString(R.string.new_category_search_label) + categoryEntity.getName()));
            }
        });

        viewModel.getMainCategory().observe(this, new Observer<CategoryEntity>() {
            @Override
            public void onChanged(@Nullable CategoryEntity categoryEntity) {
                if (categoryEntity == null) {
                    // TODO: Hide delete button and other data related to swiped right category - it is not update
                }
            }
        });


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SHDebug.debugTag(CLASS_NAME, "fab.setOnClickListener:Start");
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SHDebug.debugTag(CLASS_NAME, "onCreateOptionsMenu:Start");
        getMenuInflater().inflate(R.menu.menu_category, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SHDebug.debugTag(CLASS_NAME, "onOptionsItemSelected:Start");
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
