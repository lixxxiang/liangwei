package com.android.lixiang.liangwei.ui.fragment.area2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import com.android.lixiang.base.utils.view.StatusBarUtil
import com.android.lixiang.liangwei.R
import com.android.lixiang.liangwei.ui.fragment.area1.ResultInfoFragment
import com.blankj.utilcode.util.KeyboardUtils
import kotlinx.android.synthetic.main.fragment_info_collect_entry.*
import kotlinx.android.synthetic.main.fragment_information_maintenance.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


class InfomationMaintenanceFragment : SupportFragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v) {
//            mMyInsuranceRL -> {
//                start(AllInfoFragment().newInstance())
//
//            }
            mAllInfoEditedCV -> {
                start(AllInfoEditFragment().newInstance())
            }
        }
    }

    private fun initViews() {
        mSurveyToolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(mSurveyToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mSurveyToolbar.setNavigationOnClickListener {
            KeyboardUtils.hideSoftInput(activity!!)

            pop()
        }

        mInfoMaintenanceSearchET.setOnEditorActionListener { v, actionId, event ->
            /**
             * @param v
             * @param actionId 针对软键盘,若是实体键盘actionId=0
             * @param event 针对实体键盘,若是软键盘event=null
             * @return 返回true表示自己处理Enter事件,当imeOptions="actionSearch"时返回false此方法会被调用两次
             */
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                KeyboardUtils.hideSoftInput(activity)
                val fragment = ResultInfoEditFragment().newInstance()
                val bundle = Bundle()
                bundle.putString("SEARCHTEXT",mInfoMaintenanceSearchET.text.toString())
                fragment.arguments = bundle
                start(fragment)
                true
            } else false
        }
    }

    fun newInstance(): InfomationMaintenanceFragment {
        val args = Bundle()
        val fragment = InfomationMaintenanceFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_information_maintenance, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        StatusBarUtil.setTranslucentForImageViewInFragment(activity, 0, null)
        initViews()
//        mInfoEntryRelativeLayout.setOnClickListener(this)
//        mAllInfoRL.setOnClickListener(this)
        mAllInfoEditedCV.setOnClickListener(this)
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }
}
