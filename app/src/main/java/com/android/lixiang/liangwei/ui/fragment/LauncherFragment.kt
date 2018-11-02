package com.android.lixiang.liangwei.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.liangwei.R
import kotlinx.android.synthetic.main.fragment_launcher.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

class LauncherFragment : SupportFragment(), View.OnClickListener {

    private var handler = Handler()
    override fun onClick(p0: View?) {
        when (p0) {
            mOpenRL -> {
                handler.removeCallbacksAndMessages(null)
                startWithPop(HomeFragment().newInstance())
            }
        }
    }

    fun newInstance(): LauncherFragment {
        val args = Bundle()
        val fragment = LauncherFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_launcher, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        mOpenRL.setOnClickListener(this)
        handler.postDelayed({
            startWithPop(HomeFragment().newInstance())
        }, 5000)
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }
}