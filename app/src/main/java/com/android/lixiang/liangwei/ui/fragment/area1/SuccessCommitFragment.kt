package com.android.lixiang.liangwei.ui.fragment.area1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.liangwei.R
import com.android.lixiang.liangwei.ui.fragment.area2.MapInfoFragment
import kotlinx.android.synthetic.main.fragment_success_commit.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


class SuccessCommitFragment : SupportFragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v) {
            mCompleteRL ->{
                popTo(DataCollectionFragment::class.java, false)
            }

            mCheckInfoBtn -> {
                val fragment: InfoFragment = InfoFragment().newInstance()
                val bundle = Bundle()
                bundle.putString("ID", arguments!!.getString("ID"))
                bundle.putString("TAG", "SUCCESSCOMMIT")

                fragment.arguments = bundle
                start(fragment)
            }
        }
    }

    private fun initViews() {
        mCheckInfoRL.setOnClickListener(this)
        mCompleteRL.setOnClickListener(this)
        mCheckInfoBtn.setOnClickListener(this)
        mIdTV.text = String.format("信息ID：%s",arguments!!.getString("ID"))
    }

    fun newInstance(): SuccessCommitFragment {
        val args = Bundle()
        val fragment = SuccessCommitFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_success_commit, container, false)
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

    override fun onBackPressedSupport(): Boolean {
        popTo(DataCollectionFragment::class.java, false)
        return super.onBackPressedSupport()

    }
}
