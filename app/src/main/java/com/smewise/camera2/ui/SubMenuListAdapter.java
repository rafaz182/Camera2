package com.smewise.camera2.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.smewise.camera2.R;


/**
 * Created by wenzhe on 11/22/17.
 */

public class SubMenuListAdapter extends RecyclerView.Adapter<SubMenuListAdapter.MyViewHolder> {

    private CameraPreference mPref;
    private Context mContext;

    public SubMenuListAdapter(Context context, CameraPreference pref) {
        mContext = context;
        mPref = pref;
    }

    public void updateDataSet(CameraPreference pref) {
        mPref = pref;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.option_item_layout, parent,
                false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.itemText.setText(mPref.getEntries()[position]);
        if (mPref.getEntryIcons() != null) {
            holder.itemIcon.setBackgroundResource(mPref.getEntryIcons()[position]);
            holder.itemIcon.setVisibility(View.VISIBLE);
        } else {
            holder.itemIcon.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mPref.getEntries().length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        Button itemIcon;
        TextView itemText;
        MyViewHolder(View itemView) {
            super(itemView);
            itemIcon = (Button) itemView.findViewById(R.id.item_icon);
            itemText = (TextView) itemView.findViewById(R.id.item_text);
        }
    }

}
