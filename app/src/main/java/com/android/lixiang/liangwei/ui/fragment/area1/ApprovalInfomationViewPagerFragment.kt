package com.android.lixiang.liangwei.ui.fragment.area1

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.base.net.RestClient
import com.android.lixiang.base.utils.view.SwipeRefreshView
import com.android.lixiang.liangwei.R
import com.android.lixiang.liangwei.presenter.AllInfoPresenter
import com.android.lixiang.liangwei.presenter.data.bean.ListExamInfoBean
import com.android.lixiang.liangwei.presenter.data.bean.SearchBean
import com.android.lixiang.liangwei.ui.adapter.ApprovalInfomationViewPagerAdapter
import com.android.lixiang.liangwei.ui.adapter.IllegalArchitecturalContoursAdapter
import com.android.lixiang.liangwei.ui.fragment.area2.ApprovalInformationEditFragment
import com.android.lixiang.liangwei.ui.fragment.area2.ApprovalInformationFragment
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_illegal_architectural_contours.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


class ApprovalInfomationViewPagerFragment : SupportFragment(), View.OnClickListener {

    private var mAdapter: ApprovalInfomationViewPagerAdapter? = null
    private var listExamInfoBean: ListExamInfoBean? = null
    var handler = Handler()
    private var fragment: ApprovalInfomationViewPagerFragment? = null
    private var presenter: AllInfoPresenter? = null
    private var mSwipeRefreshView: SwipeRefreshView? = null

    override fun onClick(v: View?) {
        when (v) {
        }
    }

    private fun initViews() {
//        handler.postDelayed(runnable, 0)
        listExamInfoBean = (parentFragment as AllInfoFragment).getListExamData()
        presenter = (parentFragment as AllInfoFragment).getPresenter()
        mSwipeRefreshView = view!!.findViewById(R.id.mAllInfoSRL)
        mSwipeRefreshView!!.isRefreshing = true
        fragment = this
        if (!listExamInfoBean!!.data.list.isEmpty())
            mAdapter = ApprovalInfomationViewPagerAdapter(listExamInfoBean!!, context, fragment)
        else{
            mNoResultTV.visibility = View.VISIBLE
        }
        Handler().postDelayed({
            mAllInfoRV.adapter = mAdapter
            mSwipeRefreshView!!.isRefreshing = false
        }, 1000)
//        mAllInfoRV.adapter = mAdapter
        mAllInfoRV.setOnItemClickListener { adapterView, view, i, l ->
            val fragment = ApprovalInformationFragment().newInstance()
            val bundle = Bundle()
            bundle.putString("APPROVAL_ID", listExamInfoBean!!.data.list[i].number)
            bundle.putString("TAG", "ApprovalInfomationViewPagerFragment")
            fragment.arguments = bundle
            (parentFragment as AllInfoFragment).start(fragment)
        }

        mSwipeRefreshView!!.setColorSchemeColors(Color.parseColor("#6299FF"))
        mSwipeRefreshView!!.setOnRefreshListener {
            Logger.d("LLLL")
            Handler().postDelayed({
                RestClient().builder()
                        .url("http://59.110.161.48:8088/listexaminfo")
                        .success(object : ISuccess {
                            override fun onSuccess(response: String) {
                                Logger.d(response)
                                listExamInfoBean = Gson().fromJson(response, ListExamInfoBean::class.java)
                                mAdapter = ApprovalInfomationViewPagerAdapter(listExamInfoBean!!, context, fragment!!)
                                mAllInfoRV.adapter = mAdapter
                                mAdapter!!.notifyDataSetChanged()
                                mAllInfoSRL.isRefreshing = false
                            }
                        })
                        .failure(object : IFailure {
                            override fun onFailure() {

                            }
                        })
                        .error(object : IError {
                            override fun onError(code: Int, msg: String) {
                                Logger.d(msg)
                                mAllInfoSRL.isRefreshing = false

                            }
                        })
                        .build()
                        .post()


            }, 2000)
        }

//        mSwipeRefreshView!!.setOnLoadMoreListener {
//            RestClient().builder()
//                    .url("http://59.110.161.48:8088/listexaminfo")
//                    .success(object : ISuccess {
//                        override fun onSuccess(response: String) {
//                            Logger.d(response)
//                            listExamInfoBean = Gson().fromJson(response, ListExamInfoBean::class.java)
//                            if (!listExamInfoBean!!.data.list.isEmpty()){
//                                mAdapter = ApprovalInfomationViewPagerAdapter(listExamInfoBean!!, context, fragment)
//                                mAllInfoRV.adapter = mAdapter
//                                mAdapter!!.notifyDataSetChanged()
//                            }else{
//                                mNoResultTV.visibility = View.VISIBLE
//                            }
//                            mAllInfoSRL.isRefreshing = false
//                            mSwipeRefreshView!!.setLoading(false)
//
//                        }
//                    })
//                    .failure(object : IFailure {
//                        override fun onFailure() {
//
//                        }
//                    })
//                    .error(object : IError {
//                        override fun onError(code: Int, msg: String) {
//
//                        }
//                    })
//                    .build()
//                    .post()
//        }
    }

    fun newInstance(): ApprovalInfomationViewPagerFragment {
        val args = Bundle()
        val fragment = ApprovalInfomationViewPagerFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_approval_information_view_pager, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
//        Logger.d(listExamInfoBean!!.message)
//        mAdapter = ApprovalInfomationViewPagerAdapter(listExamInfoBean!!, context)
//        mAllInfoRV.adapter = mAdapter


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
