package com.chenyp.weather.adapter;

/**
 * Created by chenyp on 15-6-25.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenyp.weather.R;
import com.chenyp.weather.bean.CurWeatherIndex;
import com.chenyp.weather.bean.IndexInfo;

import java.util.List;

public class IndexRecyclerViewAdapter extends RecyclerView.Adapter<IndexRecyclerViewAdapter.IndexViewHolder>{

    private List<IndexInfo> mList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public IndexRecyclerViewAdapter(Context context, List<IndexInfo> mList) {
        this.mList = mList;
        this.mContext = context;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public IndexViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        return new IndexViewHolder(mLayoutInflater.inflate(R.layout.weather_main_index, parent, false));
    }

    @Override
    public void onBindViewHolder(IndexViewHolder holder, int i) {
        //界面设置
        holder.setItemData(mList.get(i));
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class IndexViewHolder extends RecyclerView.ViewHolder{

        private ImageView icon;
        private TextView title;
        private TextView value;

        public IndexViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.id_index_icon);
            title = (TextView) itemView.findViewById(R.id.id_index_title);
            value = (TextView) itemView.findViewById(R.id.id_index_value);
        }

        private void setItemData(IndexInfo index){
            title.setText(index.getTitle());
            value.setText(index.getValue());
        }

    }
}
