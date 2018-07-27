package com.sh.study.udacitynano.planner.ui.list;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sh.study.udacitynano.planner.R;
import com.sh.study.udacitynano.planner.constants.MyConstants;
import com.sh.study.udacitynano.planner.constants.SHDebug;
import com.sh.study.udacitynano.planner.ui.category.CategoryActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Main list Fragment
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-07-09
 */
public class ListFragment extends Fragment {
    @BindView(R.id.search_results_list)
    RecyclerView recyclerView;

    private static final String CLASS_NAME = "ListFragment";
    private Unbinder unbinder;
    public ListAdapter listAdapter;

    public ListFragment() {
        SHDebug.debugTag(CLASS_NAME, "constructor");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SHDebug.debugTag(CLASS_NAME, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listAdapter = new ListAdapter();
        recyclerView.setAdapter(listAdapter);


        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                SHDebug.debugTag(CLASS_NAME, "onMove");
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Intent category = new Intent(getActivity(), CategoryActivity.class);
                int position = viewHolder.getAdapterPosition();

                if (direction == ItemTouchHelper.LEFT) {
                    SHDebug.debugTag(CLASS_NAME, "onSwiped:Left");
                    // Send actual category as parent of new one
                    int parent = listAdapter.getItem(position).getId();
                    category.putExtra(MyConstants.INTENT_PARENT_CATEGORY_ID, parent);
                    // Create new category
                    category.putExtra(MyConstants.INTENT_MAIN_CATEGORY_ID, 0);
                    SHDebug.debugTag(CLASS_NAME, "onSwiped:Left, pos: " + position + " , parent: " + parent);
                } else {
                    // Send actual information about existing category
                    int id = listAdapter.getItem(position).getId();
                    category.putExtra(MyConstants.INTENT_MAIN_CATEGORY_ID, id);
                    int parent = listAdapter.getItem(position).getParentId();
                    category.putExtra(MyConstants.INTENT_PARENT_CATEGORY_ID, parent);
                    SHDebug.debugTag(CLASS_NAME, "onSwiped:Right, pos: " + position + " , parent: " + parent + " category id=" + id);
                }
                listAdapter.notifyItemChanged(position);
                startActivity(category);
            }
        }).attachToRecyclerView(recyclerView);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SHDebug.debugTag(CLASS_NAME, "onDestroyView");
        unbinder.unbind();
    }
}

