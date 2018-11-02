package com.android.lixiang.liangwei.ui.fragment.area1

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.base.utils.view.StatusBarUtil
import com.android.lixiang.liangwei.R
import kotlinx.android.synthetic.main.fragment_info_collect_entry.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import android.view.inputmethod.EditorInfo
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.liangwei.presenter.InfoCollectEntryPresenter
import com.android.lixiang.liangwei.presenter.data.bean.DetailBean
import com.android.lixiang.liangwei.presenter.injection.component.DaggerInfoCollectEntryFragmentComponent
import com.android.lixiang.liangwei.presenter.injection.module.InfoCollectEntryModule
import com.android.lixiang.liangwei.presenter.view.InfoCollectEntryView
import com.android.lixiang.liangwei.ui.NetworkChangeReceiver
import com.blankj.utilcode.util.KeyboardUtils
import com.orhanobut.logger.Logger


class InfoCollectEntryFragment :
        BaseMvpFragment<InfoCollectEntryPresenter>(),
//        SupportFragment(),
        View.OnClickListener, InfoCollectEntryView
//        , ISupportFragment
{

    private var isNetworkConnected: String? = null
    private var intentFilter: IntentFilter? = null
    private var networkChangeReceiver: NetworkChangeReceiver? = null

    private var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            isNetworkConnected = "false"
        }
    }

    private var broadcastReceiver2: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            isNetworkConnected = "true"
        }
    }

    override fun returnData2(s: DetailBean) {
        Logger.d(s.data.originalPrice)
    }

    override fun returnData(s: DetailBean) {
        Logger.d(s.data.originalPrice)
    }

    override fun test(string: String) {
        Logger.d(string)
    }

    override fun injectComponent() {
        DaggerInfoCollectEntryFragmentComponent.builder().fragmentComponent(fragmentComponent).infoCollectEntryModule(InfoCollectEntryModule()).build().inject(this)
        mPresenter.mView = this
    }

    private fun checkAccess() {
        intentFilter = IntentFilter()
        intentFilter!!.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        networkChangeReceiver = NetworkChangeReceiver()
        activity!!.registerReceiver(networkChangeReceiver, intentFilter)
    }

    override fun onClick(v: View?) {
        when (v) {
//            mInfoEntryCV -> {
//                start(InfoCollectFragment().newInstance())
//            }
            mAllInfoRL -> {
                if (isNetworkConnected == "true") {
                    start(DataCollectionFragment().newInstance())
                } else {
                    val snackBar = Snackbar.make(view!!, "请连接网络后操作", Snackbar.LENGTH_SHORT)
                    snackBar.show()
                }
            }

            mAllInfoRL2 -> {

                if (isNetworkConnected == "true") {
                    start(AllInfoFragment().newInstance())
                } else {
                    val snackBar = Snackbar.make(view!!, "请连接网络后操作", Snackbar.LENGTH_SHORT)
                    snackBar.show()
                }
            }
        }
    }

    private fun initViews() {
        mInfoSearchToolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(mInfoSearchToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mInfoSearchToolbar.setNavigationOnClickListener {
            KeyboardUtils.hideSoftInput(activity!!)

            pop()
        }
        checkAccess()
        activity!!.registerReceiver(broadcastReceiver, IntentFilter("NO_ACCESS"))
        activity!!.registerReceiver(broadcastReceiver2, IntentFilter("ACCESS"))
        mInfoEntrySearchET.setOnEditorActionListener { v, actionId, event ->
            /**
             * @param v
             * @param actionId 针对软键盘,若是实体键盘actionId=0
             * @param event 针对实体键盘,若是软键盘event=null
             * @return 返回true表示自己处理Enter事件,当imeOptions="actionSearch"时返回false此方法会被调用两次
             */
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                KeyboardUtils.hideSoftInput(activity)
                if (isNetworkConnected == "true") {
//                    start(AllInfoFragment().newInstance())
                    val fragment = ResultInfoFragment().newInstance()
                    val bundle = Bundle()
                    bundle.putString("SEARCHINFO", mInfoEntrySearchET.text.toString())
                    fragment.arguments = bundle
                    start(fragment)
                } else {
                    val snackBar = Snackbar.make(view!!, "请连接网络后操作", Snackbar.LENGTH_SHORT)
                    snackBar.show()
                }

                true
            } else false
        }

        mPresenter.getData2()
    }

    fun newInstance(): InfoCollectEntryFragment {
        val args = Bundle()
        val fragment = InfoCollectEntryFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_collect_entry, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        StatusBarUtil.setTranslucentForImageViewInFragment(activity, 0, null)
        initViews()
//        mInfoEntryCV.setOnClickListener(this)
        mAllInfoRL.setOnClickListener(this)
        mAllInfoRL2.setOnClickListener(this)
    }

//    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        onCreate(savedInstanceState)
//    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }

//    override val mDelegate = SupportFragmentDelegate(this)
//    override var _mActivity: FragmentActivity? = null
}
