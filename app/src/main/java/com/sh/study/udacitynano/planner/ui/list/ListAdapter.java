package com.sh.study.udacitynano.planner.ui.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sh.study.udacitynano.planner.R;
import com.sh.study.udacitynano.planner.constants.SHDebug;
import com.sh.study.udacitynano.planner.database.CategoryEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Adapter used to show list of categories.
 *
 * @author Sławomir Hagiel
 * @version 1.0
 * @since 2018-07-21
 */
class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListAdapterViewHolder> {
    private static final String CLASS_NAME = "ListAdapter";
    private final ListInterface clickHandler;
    private List<CategoryEntity> dataList;

    ListAdapter(ListInterface clickHandler) {
        SHDebug.debugTag(CLASS_NAME, "constructor");
        this.clickHandler = clickHandler;
    }

    public void setDataList(List<CategoryEntity> dataList) {
        SHDebug.debugTag(CLASS_NAME, "setDataList");
        this.dataList = dataList;
        this.notifyDataSetChanged();
    }

    public CategoryEntity getItem(int position) {
        return dataList.get(position);
    }

    @NonNull
    @Override
    public ListAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.filtered_item_layout, parent, false);
//        SHDebug.debugTag(CLASS_NAME, "onCreateViewHolder");
        return new ListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListAdapterViewHolder holder, int position) {
//        SHDebug.debugTag(CLASS_NAME, "onBindViewHolder, position = " + position);
        holder.itemName.setText(String.valueOf(dataList.get(position).getName()));
    }

    @Override
    public int getItemCount() {
//        SHDebug.debugTag(CLASS_NAME, "getItemCount");
        if (dataList == null) return 0;
        else return dataList.size();
    }

    public class ListAdapterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_category_name)
        TextView itemName;

        ListAdapterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
