package com.android.lixiang.liangwei.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import com.android.lixiang.liangwei.R;
import com.android.lixiang.liangwei.presenter.data.bean.SearchBean;
import com.android.lixiang.liangwei.ui.fragment.area2.AddTagFragment;
import com.android.lixiang.liangwei.ui.fragment.area2.AllInfoEditFragment;
import com.android.lixiang.liangwei.ui.fragment.area2.ResultInfoEditFragment;
import com.android.lixiang.liangwei.ui.fragment.area2.SelectPropertiesFragment;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResultInfoEditAdapter extends BaseAdapter {
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
    private ArrayList<String> mNumberList = new ArrayList<>();
    private ArrayList<String> mNameList = new ArrayList<>();
    private ArrayList<String> mIdList = new ArrayList<>();
    private ArrayList<String> mTelList = new ArrayList<>();
    private ArrayList<Integer> mLabelList = new ArrayList<>();
    private ArrayList<String> mLabelListTitle = new ArrayList<>();
    private SearchBean searchBean = new SearchBean();
    private int[] mSelectIndexs;
    private ResultInfoEditFragment fragment;

    public ResultInfoEditAdapter(SearchBean searchBean, Context context, ResultInfoEditFragment fragment) {
        this.context = context;
        this.searchBean = searchBean;
        this.inflater = LayoutInflater.from(context);
        this.fragment = fragment;
        mLabelListTitle.add("未整改");
        mLabelListTitle.add("责令后整改");
        mLabelListTitle.add("正在整改");
        mLabelListTitle.add("已拆除");
        mLabelListTitle.add("未整改且继续加盖");
        mLabelListTitle.add("其它");
        mSelectIndexs = new int[searchBean.getData().getList().size()];

        for (int i = 0; i < searchBean.getData().getList().size(); i++) {
            mNameList.add(searchBean.getData().getList().get(i).getName());
            mIdList.add(searchBean.getData().getList().get(i).getIid());
            mTelList.add(searchBean.getData().getList().get(i).getPhone());
            mNumberList.add(searchBean.getData().getList().get(i).getNumber());
            mLabelList.add(searchBean.getData().getList().get(i).getStatus());
            mSelectIndexs[i] = -1;
        }

    }

    @Override
    public int getCount() {
        return mNameList.size() == 0 ? 1 : mNameList.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void isEdit() {
        isEdit = true;
    }

    public void isNotEdit() {
        isEdit = false;
    }


    public int[] getSelectIndexs(){
        return mSelectIndexs;
    }

    public void select(int index) {
        if (mSelectIndexs[index] == -1) {
            mSelectIndexs[index] = 0;
        } else if (mSelectIndexs[index] == 0) {
            mSelectIndexs[index] = -1;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder")
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_result_info_list, null);
            holder.mNumberTV = convertView.findViewById(R.id.mNumberTV);
            holder.mNameTV = convertView.findViewById(R.id.mNameTV);
            holder.mIdTV = convertView.findViewById(R.id.mIdTV);
            holder.mTelTV = convertView.findViewById(R.id.mTelTV);
            holder.mLabelTV = convertView.findViewById(R.id.mLabelTV);
            holder.mCheckIV = convertView.findViewById(R.id.mEditCheckBox);
            holder.mBtn = convertView.findViewById(R.id.mBtn);

            holder.mChoosePropertiesBtn = convertView.findViewById(R.id.mChoosePropertiesBtn);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        holder.mNumberTV.setText(mNumberList.get(position));
        holder.mNameTV.setText(mNameList.get(position));
        holder.mIdTV.setText(mIdList.get(position));
        holder.mTelTV.setText(mTelList.get(position));

        if (mLabelList.get(position) == 0) {
            holder.mChoosePropertiesBtn.setVisibility(View.VISIBLE);
            holder.mLabelTV.setVisibility(View.GONE);
        } else {
            holder.mLabelTV.setText(mLabelListTitle.get(mLabelList.get(position) - 1));
            holder.mChoosePropertiesBtn.setVisibility(View.GONE);
        }

//        holder.mChoosePropertiesBtn.setOnClickListener(view -> {
//            SelectPropertiesFragment addTagFragment = new SelectPropertiesFragment().newInstance();
//            Bundle bundle = new Bundle();
//            bundle.putString("APPROVAL_ID", mNumberList.get(position));
//            addTagFragment.setArguments(bundle);
//            fragment.startForResult(addTagFragment, 0x008);
//        });
//
//        holder.mLabelTV.setOnClickListener(view -> {
//            SelectPropertiesFragment addTagFragment = new SelectPropertiesFragment().newInstance();
//            Bundle bundle = new Bundle();
//            bundle.putString("APPROVAL_ID", mNumberList.get(position));
//            addTagFragment.setArguments(bundle);
//            fragment.startForResult(addTagFragment, 0x008);
//        });

        holder.mBtn.setOnClickListener(view -> {
            Logger.d(">>>>>>>>>");
            SelectPropertiesFragment addTagFragment = new SelectPropertiesFragment().newInstance();
            Bundle bundle = new Bundle();
            bundle.putString("APPROVAL_ID", mNumberList.get(position));
            addTagFragment.setArguments(bundle);
            fragment.startForResult(addTagFragment, 0x008);
        });

        if (isEdit) {
            holder.mCheckIV.setVisibility(View.VISIBLE);

            for (int ignored : mSelectIndexs) {
                if (mSelectIndexs[position] == 0) {
                    holder.mCheckIV.setBackgroundResource(R.drawable.ic_check_blue_highlight);
                } else {
                    holder.mCheckIV.setBackgroundResource(R.drawable.ic_check_blue);
                }
            }
        } else {
            holder.mCheckIV.setVisibility(View.GONE);
        }

        return convertView;
    }

    final class ViewHolder {
        AppCompatTextView mNumberTV;
        AppCompatTextView mNameTV;
        AppCompatTextView mIdTV;
        AppCompatTextView mTelTV;
        AppCompatTextView mLabelTV;

        AppCompatImageView mCheckIV;
        AppCompatTextView mChoosePropertiesBtn;
        AppCompatButton mBtn;

    }
}
