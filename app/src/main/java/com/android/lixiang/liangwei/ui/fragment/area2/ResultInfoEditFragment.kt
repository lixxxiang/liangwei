package com.android.lixiang.liangwei.ui.fragment.area2

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.base.net.RestClient
import com.android.lixiang.base.ui.activity.BaseMvpActivity
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.base.utils.view.DimenUtil
import com.android.lixiang.base.utils.view.GridSpacingItemDecoration
import com.android.lixiang.base.utils.view.SwipeRefreshView
import com.android.lixiang.liangwei.R
import com.android.lixiang.liangwei.presenter.IllegalArchitecturalContoursEditPresenter
import com.android.lixiang.liangwei.presenter.ResultInfoEditPresenter
import com.android.lixiang.liangwei.presenter.data.bean.DeleteIllegalInfoBean
import com.android.lixiang.liangwei.presenter.data.bean.SearchBean
import com.android.lixiang.liangwei.presenter.injection.component.DaggerResultInfoEditFragmentComponent
import com.android.lixiang.liangwei.presenter.injection.module.ResultInfoEditModule
import com.android.lixiang.liangwei.presenter.view.ResultInfoEditView
import com.android.lixiang.liangwei.ui.adapter.ResultInfoEditAdapter
import com.android.lixiang.liangwei.ui.fragment.area1.InfoEditFragment
import com.android.lixiang.liangwei.ui.fragment.area1.InfoFragment
import com.blankj.utilcode.util.FragmentUtils.pop
import com.blankj.utilcode.util.KeyboardUtils
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_result_info_edit.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.util.*


class ResultInfoEditFragment : BaseMvpFragment<ResultInfoEditPresenter>(), View.OnClickListener, ResultInfoEditView {

    private var mAdapter: ResultInfoEditAdapter? = null
    private var isEdit: Boolean? = false
    private var searchBean: SearchBean? = null
    private var mSwipeRefreshView: SwipeRefreshView? = null
    private var mParams: WeakHashMap<String, Any>? = WeakHashMap()
    private var fragment: ResultInfoEditFragment? = null
    private var Ls_D: MutableList<Boolean>? = mutableListOf()
    private var Ls: MutableList<String>? = mutableListOf()
    private var deleteIllegalInfoBean: DeleteIllegalInfoBean? = null

    private var LIds: String? = ""
    private var mParams2: WeakHashMap<String, Any>? = WeakHashMap()


    override fun injectComponent() {
        DaggerResultInfoEditFragmentComponent.builder().fragmentComponent(fragmentComponent).resultInfoEditModule(ResultInfoEditModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun returnSearch(sBean: SearchBean) {
        this.searchBean = sBean
        for (i in 0 until sBean.data.list.size) {
            Ls!!.add(sBean.data.list[i].number)
            Ls_D!!.add(false)
        }
        Logger.d(Ls)
        if (sBean.data.list.isEmpty()) {
            mNoResultTV.visibility = View.VISIBLE
            mAllInfoSRL.visibility = View.GONE
            mEditBtn.visibility = View.GONE
        } else {
            mNoResultTV.visibility = View.GONE
            mEditBtn.visibility = View.VISIBLE
            mAllInfoSRL.visibility = View.VISIBLE
            mAdapter = ResultInfoEditAdapter(searchBean, context, fragment)
            mAllInfoRV.adapter = mAdapter

            mAllInfoRV.setOnItemClickListener { adapterView, view, i, l ->
                Logger.d(isEdit)
                if (isEdit!!) {
                    Ls_D!![i] = !Ls_D!![i]
                    mDeleteBtn.visibility = View.VISIBLE
                    mAdapter!!.select(i)
                    mAdapter!!.notifyDataSetChanged()

                    val selectIndexs = mAdapter!!.selectIndexs
                    var count = 0
                    for (i in 0 until selectIndexs.size) {
                        if (selectIndexs[i] == -1)
                            count++
                    }
                    if (count == selectIndexs.size)
                        mDeleteBtn.visibility = View.GONE
                } else {
                    val bundle = Bundle()
                    val fragment = InfoEditFragment().newInstance()
                    bundle.putString("ID", searchBean!!.data.list[i].number)
                    bundle.putString("TAG", "RESULTINFOEDIT")
                    Logger.d(searchBean!!.data.list[i].number)
                    fragment.arguments = bundle
                    start(fragment)
                }
            }

            if (isEdit!!) {
                mAdapter!!.isEdit()
                mAdapter!!.notifyDataSetChanged()
            } else {
                mAdapter!!.notifyDataSetChanged()
            }

            mSwipeRefreshView!!.setColorSchemeColors(Color.parseColor("#6299FF"))
            mSwipeRefreshView!!.setOnRefreshListener {
                Logger.d("LLLL")
                mParams!!["param"] = arguments!!.getString("SEARCHTEXT")
                mParams!!["pagenum"] = "1"
                mParams!!["pagesize"] = "100"

                Handler().postDelayed({
                    RestClient().builder()
                            .url("http://59.110.161.48:8088/search")
                            .params(mParams!!)
                            .success(object : ISuccess {
                                override fun onSuccess(response: String) {
                                    Logger.d(response)
                                    searchBean = Gson().fromJson(response, SearchBean::class.java)
                                    mAdapter = ResultInfoEditAdapter(searchBean, context, fragment)
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

//            mSwipeRefreshView!!.setOnLoadMoreListener {
//
//                mParams!!["param"] = ""
//                mParams!!["pagenum"] = "1"
//                mParams!!["pagesize"] = "10"
//
//                RestClient().builder()
//                        .url("http://59.110.161.48:8088/search")
//                        .params(mParams!!)
//                        .success(object : ISuccess {
//
//                            override fun onSuccess(response: String) {
//                                Logger.d(response)
//                                searchBean = Gson().fromJson(response, SearchBean::class.java)
////                                Logger.d("ddddddd")
////                                mAdapter = ResultInfoEditAdapter(searchBean!!, context, fragment)
////                                mAllInfoRV.adapter = mAdapter
////                                mAdapter!!.notifyDataSetChanged()
////                                mAllInfoSRL.isRefreshing = false
//                                mSwipeRefreshView!!.setLoading(false)
//
//                            }
//                        })
//                        .failure(object : IFailure {
//                            override fun onFailure() {
//
//                            }
//                        })
//                        .error(object : IError {
//                            override fun onError(code: Int, msg: String) {
//
//                            }
//                        })
//                        .build()
//                        .post()
//            }
        }
    }


    override fun onClick(v: View?) {
        when (v) {
//            mIdConfirmRL -> {
//                start(LocationInfoFragment().newInstance())
//            }
            mEditBtn -> {
                if (!isEdit!!) {
                    mEditBtn.text = "完成"
                    isEdit = true
                    mAdapter!!.isEdit()
                    mAdapter!!.notifyDataSetChanged()
                } else {
                    mEditBtn.text = "编辑"
                    isEdit = false
                    mAdapter!!.isNotEdit()
                    mAdapter!!.notifyDataSetChanged()
                    mDeleteBtn.visibility = View.GONE
                }
            }

            mDeleteBtn -> {
                for (i in 0 until Ls_D!!.size) {
                    if (Ls_D!![i])
                        LIds = "" + LIds + Ls!![i] + ","
                }

                mParams2!!["ids"] = LIds
                val builder = AlertDialog.Builder(activity!!)
                builder.setTitle("确认要删除吗？")
                builder.setPositiveButton("确认") { dialog, _ ->

                    RestClient().builder()
                            .url("http://59.110.161.48:8088/deleteillegalinfo")
                            .params(mParams2!!)
                            .success(object : ISuccess {
                                override fun onSuccess(response: String) {
                                    Logger.d(response)
                                    deleteIllegalInfoBean = Gson().fromJson(response, DeleteIllegalInfoBean::class.java)
                                    if (deleteIllegalInfoBean!!.message == "success") {
                                        mPresenter.search(arguments!!.getString("SEARCHTEXT"), "1", "100")
                                    }
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
                    Logger.d(mParams2)
                }
                        .setNegativeButton("取消") { dialogInterface, i ->
                            dialogInterface.dismiss()
                        }

                builder.create().show()
            }
        }
    }

    private fun initViews() {
        mAllInfoToolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(mAllInfoToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mEditBtn.setOnClickListener(this)
        mDeleteBtn.setOnClickListener(this)
        fragment = this

        mAllInfoToolbar.setNavigationOnClickListener {
            KeyboardUtils.hideSoftInput(activity!!)

            pop()
        }

        Handler().postDelayed({
            mSwipeRefreshView!!.isRefreshing = false
            mPresenter.search(arguments!!.getString("SEARCHTEXT"), "1", "100")

        }, 1000)
        mSwipeRefreshView = view!!.findViewById(R.id.mAllInfoSRL)
        mSwipeRefreshView!!.isRefreshing = true
        mSwipeRefreshView!!.setColorSchemeColors(Color.parseColor("#6299FF"))
    }

    fun newInstance(): ResultInfoEditFragment {
        val args = Bundle()
        val fragment = ResultInfoEditFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result_info_edit, container, false)
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
