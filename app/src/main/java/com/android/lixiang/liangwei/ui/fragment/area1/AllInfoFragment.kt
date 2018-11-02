package com.android.lixiang.liangwei.ui.fragment.area1

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.base.utils.view.SwipeRefreshView
import com.android.lixiang.liangwei.R
import com.android.lixiang.liangwei.presenter.AllInfoEditPresenter
import com.android.lixiang.liangwei.presenter.AllInfoPresenter
import com.android.lixiang.liangwei.presenter.data.bean.ListExamInfoBean
import com.android.lixiang.liangwei.presenter.data.bean.SearchBean
import com.android.lixiang.liangwei.presenter.injection.component.DaggerAllInfoFragmentComponent
import com.android.lixiang.liangwei.presenter.injection.module.AllInfoModule
import com.android.lixiang.liangwei.presenter.view.AllInfoView
import com.android.lixiang.liangwei.ui.fragment.area3.bak.Find_tab_Adapter
import kotlinx.android.synthetic.main.fragment_illegal_architectural_contours_info.*
import kotlinx.android.synthetic.main.fragment_my_insure.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


class AllInfoFragment : BaseMvpFragment<AllInfoPresenter>(), View.OnClickListener, AllInfoView {
    private var searchBean: SearchBean? = null
    private var listExamInfoBean: ListExamInfoBean? = null
    private var listFragment: MutableList<Fragment>? = mutableListOf()
    private var listTitle: MutableList<String>? = mutableListOf()
    private var fAdapter: FragmentPagerAdapter? = null


    override fun returnListExamInfo(listExamInfoBean: ListExamInfoBean) {
        this.listExamInfoBean = listExamInfoBean
//        Handler().postDelayed({
            mMyInsureVP.adapter = fAdapter
//        },2000)


    }

    fun getPresenter(): AllInfoPresenter {
        return mPresenter
    }

    override fun returnSearch(searchBean: SearchBean) {
        this.searchBean = searchBean
        mPresenter.listExamInfo("1", "100")
    }

    override fun injectComponent() {
        DaggerAllInfoFragmentComponent.builder().fragmentComponent(fragmentComponent).allInfoModule(AllInfoModule()).build().inject(this)
        mPresenter.mView = this
    }

    private var toast: Toast? = null
    override fun onClick(v: View?) {
        when (v) {
        }
    }

    fun getSearchData(): SearchBean? {
        return if (searchBean != null)
            searchBean!!
        else
            null
    }

    fun getListExamData(): ListExamInfoBean? {
        return if (listExamInfoBean != null)
            listExamInfoBean!!
        else
            null
    }

    private fun initViews() {
        mMyInsureToolbar.title = "全部信息"
        (activity as AppCompatActivity).setSupportActionBar(mMyInsureToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mMyInsureToolbar.setNavigationOnClickListener {
            pop()
        }


        val illegalArchitecturalContoursFragment = IllegalArchitecturalContoursFragment()
        val approvalInfomationViewPagerFragment = ApprovalInfomationViewPagerFragment()

        listFragment!!.add(illegalArchitecturalContoursFragment)
        listFragment!!.add(approvalInfomationViewPagerFragment)

        listTitle!!.add("违章建筑轮廓")
        listTitle!!.add("审批信息")

        mMyInsureTL.tabMode = TabLayout.MODE_FIXED
        mMyInsureTL.addTab(mMyInsureTL.newTab().setText(listTitle!![0]))
        mMyInsureTL.addTab(mMyInsureTL.newTab().setText(listTitle!![1]))

        fAdapter = Find_tab_Adapter(childFragmentManager, listFragment, listTitle)

        mMyInsureTL.setupWithViewPager(mMyInsureVP)

        mMyInsureVP.currentItem = 0
        mPresenter.search("", "1", "100")

    }

    fun newInstance(): AllInfoFragment {
        val args = Bundle()
        val fragment = AllInfoFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_insure, container, false)
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
