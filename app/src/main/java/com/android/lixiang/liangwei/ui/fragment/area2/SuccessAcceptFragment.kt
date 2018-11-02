package com.android.lixiang.liangwei.ui.fragment.area2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.liangwei.R
import com.android.lixiang.liangwei.ui.fragment.HomeFragment
import kotlinx.android.synthetic.main.fragment_success_accept.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


class SuccessAcceptFragment : SupportFragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v) {
            mViewInfomationRL -> {
                val fragment = ApprovalInformationFragment().newInstance()
                val bundle = Bundle()
                bundle.putString("APPROVAL_ID", arguments!!.getString("APPROVAL_ID"))
//                bundle.putString("APPROVAL_ID", "S701540177751255")
                fragment.arguments = bundle
                start(fragment)
            }
            mDoneBtn -> {
                popTo(findFragment(HomeFragment().javaClass).javaClass, false)
            }
        }
    }

    private fun initViews() {
        if (arguments!!.get("TAG") == "APPROVALINFOMATION"){
            mIdTV.text = String.format("信息ID：%s", arguments!!.getString("APPROVAL_ID"))
        }

        mViewInfomationRL.setOnClickListener(this)
        mDoneBtn.setOnClickListener(this)
    }

    fun newInstance(): SuccessAcceptFragment {
        val args = Bundle()
        val fragment = SuccessAcceptFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_success_accept, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()

    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}
