package com.android.lixiang.liangwei.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import com.android.lixiang.liangwei.R;
import com.android.lixiang.liangwei.presenter.data.bean.ListExamInfoBean;
import com.android.lixiang.liangwei.presenter.data.bean.SearchBean;
import com.android.lixiang.liangwei.ui.fragment.area1.ApprovalInfomationViewPagerFragment;
import com.android.lixiang.liangwei.ui.fragment.area2.ApprovalInfomationEditViewPagerFragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApprovalInfomationViewPagerAdapter extends BaseAdapter {
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
    private ApprovalInfomationViewPagerFragment fragment;
    private List<String> mContentList = new ArrayList<>();
    private Map<Integer, Boolean> map = new HashMap<>();
    private ArrayList<String> mNumberList = new ArrayList<>();
    private ArrayList<String> mIdList = new ArrayList<>();
    private ArrayList<String> mWorkList = new ArrayList<>();
    private ArrayList<String> mDateList = new ArrayList<>();
    private ArrayList<String> mLabelList = new ArrayList<>();
    private ListExamInfoBean listExamInfoBean = new ListExamInfoBean();


    public ApprovalInfomationViewPagerAdapter(ListExamInfoBean listExamInfoBean, Context context, ApprovalInfomationViewPagerFragment fragment) {
        this.context = context;
        this.listExamInfoBean = listExamInfoBean;
        this.fragment = fragment;
        inflater = LayoutInflater.from(context);
        for (int i = 0; i < listExamInfoBean.getData().getList().size(); i++) {
            mNumberList.add(listExamInfoBean.getData().getList().get(i).getNumber());
            mIdList.add("" + listExamInfoBean.getData().getList().get(i).getId());
            mWorkList.add(listExamInfoBean.getData().getList().get(i).getWork());
            mDateList.add("" + listExamInfoBean.getData().getList().get(i).getCreatetime());
            mLabelList.add(listExamInfoBean.getData().getList().get(i).getLabel());
        }
    }


    @Override
    public int getCount() {
        return mNumberList.size() == 0 ? 1 : mNumberList.size();
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        @SuppressLint("ViewHolder")
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_approval_information_info_list, null);
            holder.mNumberTV = convertView.findViewById(R.id.mNumberTV);
            holder.mIdTV = convertView.findViewById(R.id.mIdTV);
            holder.mWorkTV = convertView.findViewById(R.id.mWorkTV);
            holder.mDateTV = convertView.findViewById(R.id.mDateTV);
            holder.mCheckIV = convertView.findViewById(R.id.mEditCheckBox);
            holder.mLabelTV = convertView.findViewById(R.id.mLabelTV);


//            holder.mRelativeLayout = convertView.findViewById(R.id.mHasDetailRL);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.mNumberTV.setText(mNumberList.get(position));
        holder.mWorkTV.setText(mWorkList.get(position));
        holder.mIdTV.setText(mIdList.get(position));
        holder.mDateTV.setText(stampToDate(mDateList.get(position)));
        if (mLabelList.get(position).isEmpty()) {
            holder.mLabelTV.setVisibility(View.GONE);
        } else
            holder.mLabelTV.setText(mLabelList.get(position));


        return convertView;
    }

    final class ViewHolder {
        AppCompatTextView mNumberTV;
        AppCompatTextView mIdTV;
        RelativeLayout mRelativeLayout;
        AppCompatTextView mWorkTV;
        AppCompatTextView mDateTV;
        AppCompatTextView mLabelTV;
        AppCompatImageView mCheckIV;
    }

    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
}
