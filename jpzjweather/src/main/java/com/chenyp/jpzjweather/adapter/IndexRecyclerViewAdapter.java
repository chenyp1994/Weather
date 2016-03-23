package com.chenyp.jpzjweather.adapter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chenyp.jpzjweather.R;
import com.chenyp.jpzjweather.bean.IndexInfo;
import com.chenyp.jpzjweather.util.IconUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chenyp on 15-6-25.
 */
public class IndexRecyclerViewAdapter extends RecyclerView.Adapter<IndexRecyclerViewAdapter.IndexViewHolder> {

    private List<IndexInfo> mList;
    private int itemLayout;

    public IndexRecyclerViewAdapter(List<IndexInfo> mList, int itemLayout) {
        this.mList = mList;
        this.itemLayout = itemLayout;
    }

    @Override
    public IndexViewHolder onCreateViewHolder(ViewGroup parent, int i) {
        return new IndexViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(itemLayout, parent, false));
    }

    @Override
    public void onBindViewHolder(IndexViewHolder holder, int position) {
        //界面设置
        holder.setItemData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class IndexViewHolder extends RecyclerView.ViewHolder {

        public ImageView icon;
        public TextView title;
        public TextView value;
        private DisplayImageOptions options;

        public IndexViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.id_index_icon);
            title = (TextView) itemView.findViewById(R.id.id_index_title);
            value = (TextView) itemView.findViewById(R.id.id_index_value);
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.mipmap.ic_launcher)
                    .showImageOnFail(R.drawable.undefined)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();
        }

        public void setItemData(IndexInfo index) {
            title.setText(index.getTitle());
            value.setText(index.getValue());
            /*icon.setImageResource(IconUtil.getIIconId(index.getTitle()));*/
            String imageUri = "drawable://" + (IconUtil.getIIconId(index.getTitle()));
            ImageLoader.getInstance().displayImage(imageUri, icon, options);
        }

    }
}
