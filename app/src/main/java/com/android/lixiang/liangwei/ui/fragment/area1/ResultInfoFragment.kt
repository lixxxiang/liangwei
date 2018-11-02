package com.android.lixiang.liangwei.ui.fragment.area1

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.base.net.RestClient
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.base.utils.view.DimenUtil
import com.android.lixiang.base.utils.view.GridSpacingItemDecoration
import com.android.lixiang.base.utils.view.SwipeRefreshView
import com.android.lixiang.liangwei.R
import com.android.lixiang.liangwei.presenter.ResultInfoPresenter
import com.android.lixiang.liangwei.presenter.data.bean.SearchBean
import com.android.lixiang.liangwei.presenter.injection.component.DaggerResultInfoFragmentComponent
import com.android.lixiang.liangwei.presenter.injection.module.ResultInfoModule
import com.android.lixiang.liangwei.presenter.view.ResultInfoView
import com.android.lixiang.liangwei.ui.adapter.AllInfoAdapter
import com.android.lixiang.liangwei.ui.adapter.ResultInfoAdapter
import com.blankj.utilcode.util.KeyboardUtils
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_result_info.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.util.*


class ResultInfoFragment : BaseMvpFragment<ResultInfoPresenter>(), View.OnClickListener, ResultInfoView {
    private var mAdapter: ResultInfoAdapter? = null
    private var mSwipeRefreshView: SwipeRefreshView? = null
    private var searchBean: SearchBean? = null
    private var mParams: WeakHashMap<String, Any>? = WeakHashMap()


    override fun injectComponent() {
        DaggerResultInfoFragmentComponent.builder().fragmentComponent(fragmentComponent).resultInfoModule(ResultInfoModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun returnSearch(sarchBean: SearchBean) {
        if (sarchBean.data.list.isEmpty()) {
            mNoResultTV.visibility = View.VISIBLE
            mAllInfoSRL.visibility = View.GONE
        } else {
            this.searchBean = sarchBean
            mNoResultTV.visibility = View.GONE
            mAllInfoSRL.visibility = View.VISIBLE
            mAdapter = ResultInfoAdapter(searchBean, context)
            mAllInfoRV.adapter = mAdapter

            mAllInfoRV.setOnItemClickListener { adapterView, view, i, l ->
                val bundle = Bundle()
                val fragment = InfoFragment().newInstance()
                bundle.putString("ID", sarchBean.data.list[i].number)
                bundle.putString("TAG", "RESULTINFOFRAGMENT")
                fragment.arguments = bundle
                start(fragment)
            }

            mSwipeRefreshView!!.setColorSchemeColors(Color.parseColor("#6299FF"))
            mSwipeRefreshView!!.setOnRefreshListener {
                Logger.d("LLLL")
                mParams!!["param"] = arguments!!.getString("SEARCHINFO")
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
                                    mAdapter = ResultInfoAdapter(searchBean!!, context)
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
//                mParams!!["param"] = ""
//                mParams!!["pagenum"] = "1"
//                mParams!!["pagesize"] = "100"
//                RestClient().builder()
//                        .url("http://59.110.161.48:8088/search")
//                        .params(mParams!!)
//                        .success(object : ISuccess {
//                            override fun onSuccess(response: String) {
//                                Logger.d(response)
//                                searchBean = Gson().fromJson(response, SearchBean::class.java)
//                                mAdapter = ResultInfoAdapter(searchBean!!, context)
//                                mAllInfoRV.adapter = mAdapter
//                                mAdapter!!.notifyDataSetChanged()
//                                mAllInfoSRL.isRefreshing = false
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
        }
    }

    private fun initViews() {
        mAllInfoToolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(mAllInfoToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mAllInfoToolbar.setNavigationOnClickListener {
            KeyboardUtils.hideSoftInput(activity!!)

            pop()
        }

        Handler().postDelayed({
            mSwipeRefreshView!!.isRefreshing = false
            mPresenter.search(arguments!!.getString("SEARCHINFO"), "1", "100")

        }, 1000)
        mSwipeRefreshView = view!!.findViewById(R.id.mAllInfoSRL)
        mSwipeRefreshView!!.isRefreshing = true
        mSwipeRefreshView!!.setColorSchemeColors(Color.parseColor("#6299FF"))

    }

    fun newInstance(): ResultInfoFragment {
        val args = Bundle()
        val fragment = ResultInfoFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_result_info, container, false)
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
