package com.android.lixiang.liangwei.ui.adapter;

import com.android.lixiang.liangwei.R;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResultInfoEdit2Adapter extends BaseAdapter {
    private LayoutInflater inflater = null;
    private Context context;
    private int selectedItem = -1;
    private ArrayList<HashMap<String, String>> list;
    private int count = 0;
    private List<String> check;
    private int sizeAfter = 0;
    private boolean delete = false;
    private boolean addOrDeleteFlag = true;
    private int index = -1;
    private boolean isEdit = false;
    private List<String> mContentList = new ArrayList<>();
    private Map<Integer, Boolean> map = new HashMap<>();

    public ResultInfoEdit2Adapter(List<String> mContentList, Context context) {
        this.context = context;
        this.mContentList = mContentList;
        this.inflater = LayoutInflater.from(context);
    }

    public ResultInfoEdit2Adapter(Context context, List<String> mContentList, List<String> mTimeList) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    public ResultInfoEdit2Adapter(ArrayList<HashMap<String, String>> list, Context context) {
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    public ResultInfoEdit2Adapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
//        DimenUtil dimenUtil = new DimenUtil();
//        return (dimenUtil.px2dip(context, dimenUtil.getScreenHeight(context)) - 130) / 50;
        return mContentList.size() == 0 ? 1 : mContentList.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setCounts(int count) {
        this.count = count;
        delete = false;
//        sizeAfter = count;
    }

    public void setCheck(ArrayList<HashMap<String, String>> list) {
        check = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).get("flag").equals("true")) {
                check.add("" + i);
            }
        }
        delete = false;
    }

    public void check(int index) {
        this.index = index;
        map.put(index, true);
    }

    public void setUnCheck(ArrayList<HashMap<String, String>> list) {
        check = null;
        delete = false;
        addOrDeleteFlag = true;
    }


    public void deleteItem(ArrayList<HashMap<String, String>> list) {
        delete = true;
        addOrDeleteFlag = false;
        sizeAfter = 0;
        for (int i = 0; i < list.size(); i++) {
            if (!list.get(i).get("content").equals(""))
                sizeAfter++;
        }
    }

    public void isEdit() {
        isEdit = true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder")
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_result_info_list, null);
            holder.mContentTV = convertView.findViewById(R.id.mNumberTV);
//            holder.mContentTV2 = convertView.findViewById(R.id.mContentTV2);
//            holder.mTimeTV = convertView.findViewById(R.id.mTimeTV);
            holder.mCheckIV = convertView.findViewById(R.id.mEditCheckBox);
//            holder.mRelativeLayout = convertView.findViewById(R.id.mHasDetailRL);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
}

final class ViewHolder {
    AppCompatTextView mContentTV;
    AppCompatTextView mContentTV2;
    RelativeLayout mRelativeLayout;
    AppCompatTextView mTimeTV;
    AppCompatImageView mCheckIV;
}
}
