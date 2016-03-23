package com.chenyp.jpzjweather.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chenyp.jpzjweather.R;
import com.chenyp.jpzjweather.bean.Area;
import com.chenyp.jpzjweather.db.AreaDB;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.RecyclerSwipeAdapter;

import java.util.ArrayList;

/**
 * Created by chenyp on 15-7-6.
 */
public class CityRecyclerViewAdapter extends RecyclerSwipeAdapter<CityRecyclerViewAdapter.CityViewHolder> {

    private ArrayList<String> mList;
    private int itemLayout;

    public CityRecyclerViewAdapter(ArrayList<String> mList, int itemLayout) {
        this.mList = mList;
        this.itemLayout = itemLayout;
    }

    @Override
    public CityRecyclerViewAdapter.CityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CityViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(itemLayout, parent, false));
    }

    @Override
    public void onBindViewHolder(final CityRecyclerViewAdapter.CityViewHolder holder, final int position) {
        //界面设置
        holder.setItemData(mList.get(position));
        holder.swipeLayout.setShowMode(SwipeLayout.ShowMode.LayDown);
        holder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {
            @Override
            public void onOpen(SwipeLayout layout) {
                YoYo.with(Techniques.Tada).duration(500).delay(100).playOn(layout.findViewById(R.id.trash));
            }
        });
        holder.swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
            @Override
            public void onDoubleClick(SwipeLayout layout, boolean surface) {
                Toast.makeText(holder.itemView.getContext(), "DoubleClick", Toast.LENGTH_SHORT).show();
            }
        });
        holder.buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mItemManger.removeShownLayouts(holder.swipeLayout);
                mList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mList.size());
                mItemManger.closeAllItems();
                Toast.makeText(view.getContext(), "Deleted " + holder.city.getText().toString() + "!", Toast.LENGTH_SHORT).show();
            }
        });
        mItemManger.bindView(holder.itemView, position);
    }

    @Override
    public int getItemCount() {
        return mList == null? 0 : mList.size();
    }

    @Override
    public int getSwipeLayoutResourceId(int i) {
        return R.id.id_rv_item_swipe;
    }

    public ArrayList<String> getmList() {
        return mList == null? new ArrayList<String>() : mList;
    }

    public void setmList(ArrayList<String> mList) {
        this.mList = mList;
    }

    public static class CityViewHolder extends RecyclerView.ViewHolder {

        public SwipeLayout swipeLayout;
        public TextView city;
        public TextView district;
        public TextView province;
        public Button buttonDelete;
        public AreaDB areaDB;

        public CityViewHolder(View itemView) {
            super(itemView);
            areaDB = AreaDB.getInstance(itemView.getContext());
            swipeLayout = (SwipeLayout) itemView.findViewById(R.id.id_rv_item_swipe);
            city = (TextView) itemView.findViewById(R.id.id_rv_item_city);
            district = (TextView) itemView.findViewById(R.id.id_rv_item_district);
            province = (TextView) itemView.findViewById(R.id.id_rv_item_province);
            buttonDelete = (Button) itemView.findViewById(R.id.id_rv_item_delete);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d(getClass().getSimpleName(), "onItemSelected: " + city.getText().toString());
                    Toast.makeText(view.getContext(), "onItemSelected: " + city.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        public void setItemData(String areaName) {
            Area area = areaDB.getAreaByName(areaName);
            city.setText(area.getNamecn());
            district.setText(area.getDistrictcn());
            province.setText(area.getProvincecn());
        }

    }
}
