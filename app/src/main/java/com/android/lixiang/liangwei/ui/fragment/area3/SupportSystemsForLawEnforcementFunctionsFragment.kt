package com.android.lixiang.liangwei.ui.fragment.area3

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.base.utils.view.StatusBarUtil
import com.android.lixiang.liangwei.R
import kotlinx.android.synthetic.main.fragment_support_systems_for_law_enforcement_functions.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


class SupportSystemsForLawEnforcementFunctionsFragment : SupportFragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v) {
            mLawEnforcementSelfSupervisionCV -> {
                start(LawEnforcementTrajectoryFragment().newInstance())
            }
        }
    }

    private fun initViews() {
        mSupportSystemsForLawEnforcementFunctionsToolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(mSupportSystemsForLawEnforcementFunctionsToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mSupportSystemsForLawEnforcementFunctionsToolbar.setNavigationOnClickListener {
            pop()
        }
    }

    fun newInstance(): SupportSystemsForLawEnforcementFunctionsFragment {
        val args = Bundle()
        val fragment = SupportSystemsForLawEnforcementFunctionsFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_support_systems_for_law_enforcement_functions, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        StatusBarUtil.setTranslucentForImageViewInFragment(activity, 0, null)
        initViews()
        mLawEnforcementSelfSupervisionCV.setOnClickListener(this)
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }
}
