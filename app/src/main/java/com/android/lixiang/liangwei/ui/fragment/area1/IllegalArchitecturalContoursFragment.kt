package com.android.lixiang.liangwei.ui.fragment.area1

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.base.net.RestClient
import com.android.lixiang.base.utils.view.SwipeRefreshView
import com.android.lixiang.liangwei.R
import com.android.lixiang.liangwei.presenter.AllInfoPresenter
import com.android.lixiang.liangwei.presenter.data.api.ApiService
import com.android.lixiang.liangwei.presenter.data.bean.SearchBean
import com.android.lixiang.liangwei.ui.adapter.IllegalArchitecturalContoursAdapter
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_illegal_architectural_contours.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import retrofit2.Retrofit
import java.util.*
import javax.inject.Inject


class IllegalArchitecturalContoursFragment : SupportFragment(), View.OnClickListener {

    private var mAdapter: IllegalArchitecturalContoursAdapter? = null
    private var searchBean: SearchBean? = null
    var handler = Handler()
    private var fragment: IllegalArchitecturalContoursFragment? = null
    private var presenter: AllInfoPresenter? = null
    private var mSwipeRefreshView: SwipeRefreshView? = null
    private var mParams: WeakHashMap<String, Any>? = WeakHashMap()

    override fun onClick(v: View?) {
        when (v) {
        }
    }

    private fun initViews() {
        searchBean = (parentFragment as AllInfoFragment).getSearchData()
        presenter = (parentFragment as AllInfoFragment).getPresenter()
        mSwipeRefreshView = view!!.findViewById(R.id.mAllInfoSRL)
        mSwipeRefreshView!!.isRefreshing = true
        fragment = this
        if (!searchBean!!.data.list.isEmpty())
            mAdapter = IllegalArchitecturalContoursAdapter(searchBean!!, context, fragment)
        else
            mNoResultTV.visibility = View.VISIBLE

        Handler().postDelayed({
            mAllInfoRV.adapter = mAdapter
            mSwipeRefreshView!!.isRefreshing = false
        }, 1000)
        mAllInfoRV.setOnItemClickListener { adapterView, view, i, l ->
            val bundle = Bundle()
            val fragment = InfoFragment().newInstance()
            bundle.putString("ID", searchBean!!.data.list[i].number)
            bundle.putString("TAG", "ILLEGALARCHITECTURAL")
            fragment.arguments = bundle
            (parentFragment as AllInfoFragment).start(fragment)
        }

        mSwipeRefreshView!!.setColorSchemeColors(Color.parseColor("#6299FF"))
        mSwipeRefreshView!!.setOnRefreshListener {
            Logger.d("LLLL")
            mParams!!["param"] = ""
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
                                if (!searchBean!!.data.list.isEmpty()){
                                    mAdapter = IllegalArchitecturalContoursAdapter(searchBean!!, context, fragment)
                                    mAllInfoRV.adapter = mAdapter
                                    mAdapter!!.notifyDataSetChanged()
                                }else{
                                    mNoResultTV.visibility = View.VISIBLE
                                }

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
//            mParams!!["param"] = ""
//            mParams!!["pagenum"] = "1"
//            mParams!!["pagesize"] = "10"
//            RestClient().builder()
//                    .url("http://59.110.161.48:8088/search")
//                    .params(mParams!!)
//                    .success(object : ISuccess {
//                        override fun onSuccess(response: String) {
//                            Logger.d(response)
//                            searchBean = Gson().fromJson(response, SearchBean::class.java)
//                            mAdapter = IllegalArchitecturalContoursAdapter(searchBean!!, context, fragment)
//                            mAllInfoRV.adapter = mAdapter
//                            mAdapter!!.notifyDataSetChanged()
//                            mAllInfoSRL.isRefreshing = false
//                            mSwipeRefreshView!!.setLoading(false)
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

    fun newInstance(): IllegalArchitecturalContoursFragment {
        val args = Bundle()
        val fragment = IllegalArchitecturalContoursFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_illegal_architectural_contours, container, false)
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
