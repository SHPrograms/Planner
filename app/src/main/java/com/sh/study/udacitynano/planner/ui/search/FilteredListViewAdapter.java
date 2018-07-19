package com.sh.study.udacitynano.planner.ui.search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sh.study.udacitynano.planner.R;
import com.sh.study.udacitynano.planner.database.CategoryEntity;

import java.util.List;
/**
 * Temporary class for Mockups
 * based on {@link "http://www.zoftino.com/android-search-functionality-using-searchview-and-room"}
 *
 * TODO: RecyclerView for SeachView is possible?
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-07-16
 */
public class FilteredListViewAdapter extends ArrayAdapter {

    private List<CategoryEntity> dataList;
    private Context mContext;
    private int searchResultItemLayout;

    public FilteredListViewAdapter(Context context, int resource, List<CategoryEntity> storeSourceDataLst) {
        super(context, resource, storeSourceDataLst);
        dataList = storeSourceDataLst;
        mContext = context;
        searchResultItemLayout = resource;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public CategoryEntity getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public View getView(int position, View view, @NonNull ViewGroup parent) {

        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(searchResultItemLayout, parent, false);
        }

        CategoryEntity di = getItem(position);

        TextView categoryNameTV = view.findViewById(R.id.tv_category_name);
        categoryNameTV.setText(di.getName());

        return view;
    }
}