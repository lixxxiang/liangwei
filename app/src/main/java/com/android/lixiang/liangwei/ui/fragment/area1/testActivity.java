//package com.android.lixiang.liangwei.ui.fragment.area1;
//
//import android.os.Bundle;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.support.v7.app.AppCompatActivity;
//import android.widget.ListView;
//
//import com.android.lixiang.base.utils.view.SwipeRefreshView;
//import com.android.lixiang.liangwei.R;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class testActivity  extends AppCompatActivity {
//    private List<String> mList;
//    private StringAdapter mAdapter;
//    private SwipeRefreshView mSwipeRefreshView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        mSwipeRefreshView = (SwipeRefreshView) findViewById(R.id.srl);
//        ListView listView = (ListView) findViewById(R.id.lv);
//
//        mList = new ArrayList<>();
//        mAdapter = new StringAdapter();
//        listView.setAdapter(mAdapter);
//
//
//        // 不能在onCreate中设置，这个表示当前是刷新状态，如果一进来就是刷新状态，SwipeRefreshLayout会屏蔽掉下拉事件
//        //swipeRefreshLayout.setRefreshing(true);
//
//        // 设置颜色属性的时候一定要注意是引用了资源文件还是直接设置16进制的颜色，因为都是int值容易搞混
//        // 设置下拉进度的背景颜色，默认就是白色的
//        mSwipeRefreshView.setProgressBackgroundColorSchemeResource(android.R.color.white);
//        // 设置下拉进度的主题颜色
//        mSwipeRefreshView.setColorSchemeResources(R.color.colorAccent,
//                android.R.color.holo_blue_bright, R.color.colorPrimaryDark,
//                android.R.color.holo_orange_dark, android.R.color.holo_red_dark, android.R.color.holo_purple);
//
//        mSwipeRefreshView.setItemCount(20);
//
//        // 手动调用,通知系统去测量
//        mSwipeRefreshView.measure(0, 0);
//        mSwipeRefreshView.setRefreshing(true);
//
//        initEvent();
//        initData();
//    }
//
//    private void initEvent() {
//
//        // 下拉时触发SwipeRefreshLayout的下拉动画，动画完毕之后就会回调这个方法
//        mSwipeRefreshView.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                initData();
//            }
//        });
//
//
//        // 设置下拉加载更多
//        mSwipeRefreshView.setOnLoadMoreListener(new SwipeRefreshView.OnLoadMoreListener() {
//            @Override
//            public void onLoadMore() {
//                loadMoreData();
//            }
//        });
//    }
//
//    private void loadMoreData() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                mList.clear();
//                mList.addAll(DataResource.getMoreData());
//                Toast.makeText(MainActivity.this, "加载了" + 20 + "条数据", Toast.LENGTH_SHORT).show();
//
//                // 加载完数据设置为不加载状态，将加载进度收起来
//                mSwipeRefreshView.setLoading(false);
//            }
//        }, 2000);
//    }
//
//
//    private void initData() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                mList.clear();
//                mList.addAll(DataResource.getData());
//                mAdapter.notifyDataSetChanged();
//
//                Toast.makeText(MainActivity.this, "刷新了20条数据", Toast.LENGTH_SHORT).show();
//
//                // 加载完数据设置为不刷新状态，将下拉进度收起来
//                if (mSwipeRefreshView.isRefreshing()) {
//                    mSwipeRefreshView.setRefreshing(false);
//                }
//            }
//        }, 2000);
//    }
//
//
//    /**
//     * 适配器
//     */
//    private class StringAdapter extends BaseAdapter {
//
//        @Override
//        public int getCount() {
//            return mList.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return mList.get(position);
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//            if (convertView == null) {
//                convertView = View.inflate(MainActivity.this, android.R.layout.simple_list_item_1, null);
//            }
//
//            TextView tv = (TextView) convertView;
//            tv.setGravity(Gravity.CENTER);
//            tv.setPadding(0, 20, 0, 20);
//            tv.setText(mList.get(position));
//
//            return convertView;
//        }
//    }
//
//
//    public static class DataResource {
//        private static List<String> datas = new ArrayList<>();
//        private static int page = 0;
//
//        public static List<String> getData() {
//            page = 0;
//            datas.clear();
//            for (int i = 0; i < 15; i++) {
//                datas.add("我是天才" + i + "号");
//            }
//
//            return datas;
//        }
//
//        public static List<String> getMoreData() {
//            page = page + 1;
//            for (int i = 20 * page; i < 20 * (page + 1); i++) {
//                datas.add("我是天才" + i + "号");
//            }
//
//            return datas;
//        }
//    }
//}
