package com.android.lixiang.liangwei.ui.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.android.lixiang.liangwei.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

public class AllInfoAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public AllInfoAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        helper.setText(R.id.mNumberTV,item);
    }
}
