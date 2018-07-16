package com.sh.study.udacitynano.planner.list;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sh.study.udacitynano.planner.R;

/**
 * Temporary class for Mockups
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-07-09
 */
public class AddCategoryActivityFragment extends Fragment {

    public AddCategoryActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_category, container, false);
    }
}
