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
import com.android.lixiang.liangwei.ui.NetworkChangeReceiver
import com.android.lixiang.liangwei.ui.fragment.area2.ShowLandFragment
import kotlinx.android.synthetic.main.fragment_data_collection.*
import kotlinx.android.synthetic.main.fragment_info_collect_entry.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


class DataCollectionFragment : SupportFragment(), View.OnClickListener {

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
    override fun onClick(v: View?) {
        when (v) {
            mDirectAcceptBtn -> {
                if (isNetworkConnected == "true") {
//                    start(AllInfoFragment().newInstance())
                    start(MarkLandFragment().newInstance())
                } else {
                    val snackBar = Snackbar.make(view!!, "请连接网络后操作", Snackbar.LENGTH_SHORT)
                    snackBar.show()
                }
//                start(MarkLandFragment().newInstance())
            }

            mContinueAcceptBtn -> {
//                start(ShowLandFragment().newInstance())
                if (isNetworkConnected == "true") {
//                    start(AllInfoFragment().newInstance())
//                    val fragment = ResultInfoFragment().newInstance()
//                    val bundle = Bundle()
//                    bundle.putString("SEARCHINFO", mInfoEntrySearchET.text.toString())
//                    fragment.arguments = bundle
//                    start(fragment)
                    start(ShowLandFragment().newInstance())

                } else {
                    val snackBar = Snackbar.make(view!!, "请连接网络后操作", Snackbar.LENGTH_SHORT)
                    snackBar.show()
                }
            }
        }
    }

    private fun initViews() {
        StatusBarUtil.setTranslucentForImageViewInFragment(activity, 0, null)
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        mQuickAcceptInsuranceToolbar.title = "数据采集"
        (activity as AppCompatActivity).setSupportActionBar(mQuickAcceptInsuranceToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mQuickAcceptInsuranceToolbar.setNavigationOnClickListener {
            pop()
        }

        checkAccess()
        activity!!.registerReceiver(broadcastReceiver, IntentFilter("NO_ACCESS"))
        activity!!.registerReceiver(broadcastReceiver2, IntentFilter("ACCESS"))
    }

    fun newInstance(): DataCollectionFragment {
        val args = Bundle()
        val fragment = DataCollectionFragment()
        fragment.arguments = args
        return fragment
    }

    private fun checkAccess() {
        intentFilter = IntentFilter()
        intentFilter!!.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        networkChangeReceiver = NetworkChangeReceiver()
        activity!!.registerReceiver(networkChangeReceiver, intentFilter)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_data_collection, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        mDirectAcceptBtn.setOnClickListener(this)
        mContinueAcceptBtn.setOnClickListener(this)
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

    }
}
