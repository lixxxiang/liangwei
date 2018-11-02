package com.android.lixiang.liangwei.ui.fragment.area3

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.liangwei.R
import com.android.lixiang.liangwei.presenter.LawEnforcementTrajectoryDetailPresenter
import com.android.lixiang.liangwei.presenter.injection.component.DaggerApprovalInfomationEditFragmentComponent
import com.android.lixiang.liangwei.presenter.injection.component.DaggerLawEnforcementTrajectoryDetailFragmentComponent
import com.android.lixiang.liangwei.presenter.injection.module.ApprovalInfomationEditModule
import com.android.lixiang.liangwei.presenter.injection.module.LawEnforcementTrajectoryDetailModule
import com.android.lixiang.liangwei.presenter.view.LawEnforcementTrajectoryDetailView
import com.android.lixiang.liangwei.ui.adapter.LawEnforcementTrajectoryDetailAdapter
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_law_enforcement_trajectory_detail.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class LawEnforcementTrajectoryDetailFragment : BaseMvpFragment<LawEnforcementTrajectoryDetailPresenter>(), LawEnforcementTrajectoryDetailView{
    override fun injectComponent() {
        DaggerLawEnforcementTrajectoryDetailFragmentComponent.builder().fragmentComponent(fragmentComponent).lawEnforcementTrajectoryDetailModule(LawEnforcementTrajectoryDetailModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun returntest(string: String) {
        Logger.d(string)
    }

    private var mDetailList: MutableList<String>? = mutableListOf()
    private var mTitleList: MutableList<String>? = mutableListOf()
    private var adapter: LawEnforcementTrajectoryDetailAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_law_enforcement_trajectory_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        mLawEnforcementTrajectoryDetailToolbar.title = "执法轨迹"

        mTitleList!!.add("2018/08/14 18:30")
        mTitleList!!.add("2018/08/14 18:31")
        mTitleList!!.add("2018/08/14 18:32")


        mDetailList!!.add("补齐缺失材料1")
        mDetailList!!.add("补齐缺失材料2")
        mDetailList!!.add("补齐缺失材料3")

        (activity as AppCompatActivity).setSupportActionBar(mLawEnforcementTrajectoryDetailToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mLawEnforcementTrajectoryDetailToolbar.setNavigationOnClickListener {
            pop()
        }
        adapter = LawEnforcementTrajectoryDetailAdapter(mTitleList, mDetailList, activity)
        mLawEnforcementTrajectoryDetailLV.adapter = adapter

        mPresenter.test("hhh")
    }

    fun newInstance(): LawEnforcementTrajectoryDetailFragment {
        val args = Bundle()
        val fragment = LawEnforcementTrajectoryDetailFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }
}
