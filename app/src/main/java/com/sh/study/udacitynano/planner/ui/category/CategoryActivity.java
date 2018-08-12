package com.sh.study.udacitynano.planner.ui.category;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sh.study.udacitynano.planner.R;
import com.sh.study.udacitynano.planner.constants.MyConstants;
import com.sh.study.udacitynano.planner.constants.SHDebug;
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
    @BindView(R.id.new_category_name_label)
    public TextView newCategoryNameLabel;
    @BindView(R.id.new_category_name_et)
    public EditText newCategoryName;
    @BindView(R.id.new_category_time_label)
    public TextView newCategoryTimeLabel;

    private static final String CLASS_NAME = "CategoryActivity";
    private CategoryViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SHDebug.debugTag(CLASS_NAME, "onCreate:Start");
        setContentView(R.layout.activity_category);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        CategoryViewModelFactory factory = InjectorUtils.provideCategoryActivityViewModelFactory(
                this.getApplicationContext(),
                getIntent().getIntExtra(MyConstants.INTENT_PARENT_CATEGORY_ID, 0),
                getIntent().getIntExtra(MyConstants.INTENT_MAIN_CATEGORY_ID, 0));
        viewModel = ViewModelProviders.of(this, factory).get(CategoryViewModel.class);

        viewModel.getParentCategory().observe(this, parentCategory -> {
            if (parentCategory == null) {
                newCategorySearchLabel.setVisibility(View.GONE);
            } else {
                newCategorySearchLabel.setText(
                        String.format(getString(R.string.new_category_search_label) + parentCategory.getName()));
            }
        });

        viewModel.getTime().observe(this, aLong -> {
            if ((aLong == null) || (aLong == 0)) {
                newCategoryTimeLabel.setVisibility(View.INVISIBLE);
            } else {
                newCategoryTimeLabel.setVisibility(View.VISIBLE);

                newCategoryTimeLabel.setText(String.format(getString(R.string.new_category_time_label) +
                        " " +
                        String.format("%02d:%02d:%02d", aLong / (3600 * 1000),
                                aLong / (60 * 1000) % 60,
                                aLong / 1000 % 60)
                ));
            }
        });

        fab.setOnClickListener(view -> {
            SHDebug.debugTag(CLASS_NAME, "fab.setOnClickListener:Start");
            String name = getNewCategoryName();
            if (!name.isEmpty()) {
                viewModel.setCategoryToDB(name);
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SHDebug.debugTag(CLASS_NAME, "onCreateOptionsMenu:Start");
        getMenuInflater().inflate(R.menu.menu_category, menu);

        viewModel.getMainCategory().observe(this, mainCategory -> {
            if (mainCategory == null) {
                menu.findItem(R.id.menu_category_item_action_delete).setVisible(false);
                menu.findItem(R.id.menu_category_item_action_events).setVisible(false);
            } else {
                setTitle(getString(R.string.app_name_category_details));
                if (!mainCategory.getStatus()) {
                    newCategoryNameLabel.setText(R.string.new_category_name_label_deactivated);
                    menu.findItem(R.id.menu_category_item_action_delete).setVisible(false);
                    newCategoryName.setEnabled(false);
                    fab.hide();
                }
                menu.findItem(R.id.menu_category_item_action_events).setVisible(false);
                newCategoryName.setText(mainCategory.getName());
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SHDebug.debugTag(CLASS_NAME, "onOptionsItemSelected:Start");
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else if (id == R.id.menu_category_item_action_delete) {
            String name = getNewCategoryName();
            if (!name.isEmpty()) {
                viewModel.setAsInactiveStatusCategoryToDB(name);
                finish();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private String getNewCategoryName() {
        String name = newCategoryName.getText().toString().trim();
        if (name.isEmpty()) {
            Snackbar.make(getCurrentFocus(), getString(R.string.category_name_cant_be_empty), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }
        return name;
    }
}
