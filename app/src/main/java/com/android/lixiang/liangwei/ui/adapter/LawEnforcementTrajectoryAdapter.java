package com.android.lixiang.liangwei.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import com.android.lixiang.liangwei.R;

import java.util.List;

public class LawEnforcementTrajectoryAdapter extends BaseAdapter {
    private List<String> mTitleList;
    private List<String> mDetailList;
    private LayoutInflater inflater;
    private Context context;

    public LawEnforcementTrajectoryAdapter() {
    }

    public LawEnforcementTrajectoryAdapter(List<String> mTitleList, List<String> mDetailList, Context context) {
        this.mTitleList = mTitleList;
        this.context = context;
        this.mDetailList = mDetailList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mTitleList == null ? 0 : mTitleList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder")


        View view = inflater.inflate(R.layout.item_law_enforcement_trajectory_listview, null);
        AppCompatTextView mTitleTV = view.findViewById(R.id.mTitleTV);
        AppCompatTextView mDetailTV = view.findViewById(R.id.mDetailTV);
        mTitleTV.setText(mTitleList.get(position));
        if (!mDetailList.get(position).equals(""))
            mDetailTV.setText(mDetailList.get(position));
//        if (position == 0) {
//            mTextInImageTV1.setText("车");
//            rl.setBackgroundResource(R.drawable.round_relativelayout_5_ff7445);
//        } else {
//            mTextInImageTV1.setText("行");
//            rl.setBackgroundResource(R.drawable.round_relativelayout_5_62b5ff);
//        }
        return view;
    }
}
