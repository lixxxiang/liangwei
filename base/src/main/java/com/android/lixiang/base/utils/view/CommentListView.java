package com.android.lixiang.base.utils.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by lixiang on 2017/11/29.
 * 解决滑动冲突的listview
 */

public class CommentListView extends ListView {

    public CommentListView(Context context) {
        super(context);
    }

    public CommentListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CommentListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
