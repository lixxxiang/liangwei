package com.android.lixiang.liangwei.ui.fragment.area2

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.base.net.RestClient
import com.android.lixiang.base.utils.view.SwipeRefreshView
import com.android.lixiang.liangwei.R
import com.android.lixiang.liangwei.presenter.AllInfoEditPresenter
import com.android.lixiang.liangwei.presenter.data.bean.ListExamInfoBean
import com.android.lixiang.liangwei.ui.adapter.ApprovalInfomationEditViewPagerAdapter
import com.android.lixiang.liangwei.ui.adapter.ApprovalInfomationViewPagerAdapter
import com.android.lixiang.liangwei.ui.fragment.area1.InfoFragment
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_approval_information_edit_view_pager.*
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


class ApprovalInfomationEditViewPagerFragment : SupportFragment(), View.OnClickListener {

    var mAdapter: ApprovalInfomationEditViewPagerAdapter? = null
    private var listExamInfoBean: ListExamInfoBean? = null
    private var fragment: ApprovalInfomationEditViewPagerFragment? = null
    private var isEdit: Boolean? = false
    private var mDeleteIndexs: String? = ""
    private var presenter: AllInfoEditPresenter? = null
    private var mSwipeRefreshView: SwipeRefreshView? = null


    override fun onClick(v: View?) {
        when (v) {
            mDeleteBtn -> {
                val builder = AlertDialog.Builder(activity!!)
                builder.setTitle("确认要删除吗？")
                builder.setPositiveButton("确认") { dialog, _ ->
                    AllInfoEditFragment().deleteApproval(mDeleteIndexs, presenter!!)
                    mDeleteIndexs = ""

                }.setNegativeButton("取消") { dialogInterface, i ->
                    dialogInterface.dismiss()
                }

                builder.create().show()
            }
        }
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle?) {
        super.onFragmentResult(requestCode, resultCode, data)
        if (requestCode == 0x001 && resultCode == ISupportFragment.RESULT_OK) {
            Logger.d(data!!.getString("TAG_INFO"))
        }
    }

    private fun initViews() {
        listExamInfoBean = (parentFragment as AllInfoEditFragment).getListExamData()
        presenter = (parentFragment as AllInfoEditFragment).getPresenter()
        mSwipeRefreshView = view!!.findViewById(R.id.mAllInfoSRL)
        mSwipeRefreshView!!.isRefreshing = true
        fragment = this
        mAdapter = ApprovalInfomationEditViewPagerAdapter(listExamInfoBean!!, fragment, context)
        Handler().postDelayed({
            mAllInfoRV.adapter = mAdapter
            mSwipeRefreshView!!.isRefreshing = false
        }, 1000)
        isEdit = (parentFragment as AllInfoEditFragment).getEditState()!!

        if (isEdit!!) {
            mAdapter!!.isEdit()
            mAdapter!!.notifyDataSetChanged()
        } else {
            mAdapter!!.notifyDataSetChanged()
        }

        mDeleteBtn.setOnClickListener(this)

        mAllInfoRV.setOnItemClickListener { adapterView, view, i, l ->
            if (isEdit!!) {
                mDeleteIndexs = mDeleteIndexs!! + listExamInfoBean!!.data.list[i].number + ","
                mDeleteBtn.visibility = View.VISIBLE
                mAdapter!!.select(i)
                mAdapter!!.notifyDataSetChanged()
            } else {
                val bundle = Bundle()
                val fragment = ApprovalInformationEditFragment().newInstance()
                bundle.putString("TAG", "ApprovalInfomationEditViewPagerFragment")
                bundle.putString("ID", listExamInfoBean!!.data.list[i].number)
                fragment.arguments = bundle
                (parentFragment as AllInfoEditFragment).start(fragment)
            }
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
                                mAdapter = ApprovalInfomationEditViewPagerAdapter(listExamInfoBean!!, fragment!!, context)
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

        mSwipeRefreshView!!.setOnLoadMoreListener {
            RestClient().builder()
                    .url("http://59.110.161.48:8088/listexaminfo")
                    .success(object : ISuccess {
                        override fun onSuccess(response: String) {
                            Logger.d(response)
                            listExamInfoBean = Gson().fromJson(response, ListExamInfoBean::class.java)
                            mAdapter = ApprovalInfomationEditViewPagerAdapter(listExamInfoBean!!, fragment, context)
                            mAllInfoRV.adapter = mAdapter
                            mAdapter!!.notifyDataSetChanged()
                            mAllInfoSRL.isRefreshing = false
                            mSwipeRefreshView!!.setLoading(false)

                        }
                    })
                    .failure(object : IFailure {
                        override fun onFailure() {

                        }
                    })
                    .error(object : IError {
                        override fun onError(code: Int, msg: String) {

                        }
                    })
                    .build()
                    .post()
        }
    }


    fun newInstance(): ApprovalInfomationEditViewPagerFragment {
        val args = Bundle()
        val fragment = ApprovalInfomationEditViewPagerFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_approval_information_edit_view_pager, container, false)
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
