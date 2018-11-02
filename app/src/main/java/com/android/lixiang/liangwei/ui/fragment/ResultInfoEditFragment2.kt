package com.android.lixiang.liangwei.ui.fragment

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.base.utils.view.DimenUtil
import com.android.lixiang.base.utils.view.GridSpacingItemDecoration
import com.android.lixiang.liangwei.R
import com.android.lixiang.liangwei.ui.adapter.AllInfoAdapter
import com.android.lixiang.liangwei.ui.adapter.ResultInfoEdit2Adapter
import com.android.lixiang.liangwei.ui.adapter.ResultInfoEditAdapter
import com.android.lixiang.liangwei.ui.fragment.area1.InfoFragment
import com.blankj.utilcode.util.AppUtils
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_result_info_edit2.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.util.*


class ResultInfoEditFragment2 : SupportFragment(), View.OnClickListener {

    private var mAdapter: ResultInfoEdit2Adapter? = null
    private var mLayoutManager: LinearLayoutManager? = null
    private var mData: MutableList<String>? = mutableListOf()
    private var list: ArrayList<HashMap<String, String>>? = null
    private var mEditState: Boolean? = false

    override fun onClick(v: View?) {
        when (v) {
//            mIdConfirmRL -> {
//                start(LocationInfoFragment().newInstance())
//            }
            mEditBtn -> {

            }

            mDeleteBtn -> {

            }
        }
    }

    private fun initViews() {
        mAllInfoToolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(mAllInfoToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        list = ArrayList()

        mLayoutManager = LinearLayoutManager(activity)
        mAdapter = ResultInfoEdit2Adapter(mData!!, context)
        mAllInfoRV.adapter = mAdapter
        mAdapter!!.setCounts(list!!.size)

        mAdapter!!.notifyDataSetInvalidated()

        mAllInfoRV.setOnItemClickListener { adapterView, view, i, l ->
            if (mEditState!!) {
            } else {
                start(InfoFragment().newInstance())
            }
        }
//        val headView = layoutInflater.inflate(R.layout.header_view, null)
//        mAllInfoRV.addHeaderView(headView)
        mAllInfoRV.adapter = mAdapter
        mAllInfoToolbar.setNavigationOnClickListener {
            pop()
        }
        mEditBtn.setOnClickListener(this)
        mDeleteBtn.setOnClickListener(this)
        for (i in 0 until 10) {
            mData!!.add(i.toString())
        }
    }

    fun newInstance(): ResultInfoEditFragment2 {
        val args = Bundle()
        val fragment = ResultInfoEditFragment2()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result_info_edit2, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
//        mIdConfirmRL.setOnClickListener(this)

    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
//        StatusBarUtil.setColor(activity, Color.parseColor("#FFFFFF"), 0)
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

    }
}
