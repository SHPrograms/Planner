package com.sh.study.udacitynano.planner.ui.list;

import android.view.View;

import com.sh.study.udacitynano.planner.database.CategoryEntity;

import java.util.List;

interface ListInterface {
    void onCategoryClick(CategoryEntity category, View view);
}
