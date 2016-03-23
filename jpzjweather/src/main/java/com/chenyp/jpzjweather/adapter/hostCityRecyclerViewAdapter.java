package com.chenyp.jpzjweather.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.IconTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.chenyp.jpzjweather.R;
import com.chenyp.jpzjweather.ui.activity.AddCityActivity;
import com.chenyp.jpzjweather.ui.activity.MainActivity;
import com.chenyp.jpzjweather.ui.activity.SelectedCityActivity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by chenyp on 15-7-7.
 */
public class hostCityRecyclerViewAdapter extends RecyclerView.Adapter<hostCityRecyclerViewAdapter.hostCityViewHolder> {

    private List<String> hostList;
    private int itemLayout;
    private Context context;

    public hostCityRecyclerViewAdapter(Context context, List<String> hostList, int itemLayout) {
        this.context = context;
        this.hostList = hostList;
        this.itemLayout = itemLayout;
    }

    @Override
    public hostCityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new hostCityViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(itemLayout, parent, false));
    }

    @Override
    public void onBindViewHolder(hostCityViewHolder holder, final int position) {
        ArrayList<String> sCityList = new ArrayList<>();
        sCityList.addAll(((AddCityActivity) context).getSCityList());
        holder.cityName.setText(hostList.get(position));
        if(sCityList.remove(hostList.get(position))) {
            holder.iconCheck.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String city = hostList.get(position);
                ArrayList<String> sCityList = new ArrayList<>();
                sCityList.addAll(((AddCityActivity) context).getSCityList());
                if (!sCityList.remove(city)) {
                    Intent intent = new Intent();
                    intent.putExtra(AddCityActivity.ADD_CITY, city);
                    ((AddCityActivity) context).setResult(Activity.RESULT_OK, intent);
                    ((AddCityActivity) context).finish();
                } else {
                    Toast.makeText(context, "不能添加重复城市", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    @Override
    public int getItemCount() {
        return hostList.size();
    }

    public static class hostCityViewHolder extends RecyclerView.ViewHolder {

        public TextView cityName;
        public IconTextView iconCheck;

        public hostCityViewHolder(View itemView) {
            super(itemView);
            cityName = (TextView) itemView.findViewById(R.id.id_tv_host_city_name);
            iconCheck = (IconTextView) itemView.findViewById(R.id.id_icon_check);
        }
    }
}
