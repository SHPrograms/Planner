package com.sh.study.udacitynano.planner.ui.list;

import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sh.study.udacitynano.planner.R;
import com.sh.study.udacitynano.planner.constants.SHDebug;
import com.sh.study.udacitynano.planner.utils.InjectorUtils;

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
public class ListFragment extends Fragment implements ListInterface {
    @BindView(R.id.search_results_list)
    RecyclerView recyclerView;

    private static final String CLASS_NAME = "ListFragment";
    private Unbinder unbinder;
    public ListAdapter listAdapter;
    private ListInterface listClickListener;
    private ListViewModel listViewModel;

    public ListFragment() {
        SHDebug.debugTag(CLASS_NAME, "constructor");
    }

/*
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        SHDebug.debugTag(CLASS_NAME, "onAttach");
        if (context instanceof ListInterface) {
            listClickListener = (ListInterface) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ListInterface");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        SHDebug.debugTag(CLASS_NAME, "onDetach");
        listClickListener = null;
    }
*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SHDebug.debugTag(CLASS_NAME, "onCreate");
/*
        try {
            ListViewModelFactory factory = InjectorUtils.provideListActivityViewModelFactory(getActivity().getApplicationContext());
            listViewModel = ViewModelProviders.of(getActivity(), factory).get(ListViewModel.class);
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SHDebug.debugTag(CLASS_NAME, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        unbinder = ButterKnife.bind(this, view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        listAdapter = new ListAdapter(this);
        recyclerView.setAdapter(listAdapter);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SHDebug.debugTag(CLASS_NAME, "onDestroyView");
        unbinder.unbind();
    }
}

