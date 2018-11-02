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
import com.android.lixiang.liangwei.presenter.data.bean.SearchBean
import com.android.lixiang.liangwei.ui.adapter.IllegalArchitecturalContoursAdapter
import com.android.lixiang.liangwei.ui.adapter.IllegalArchitecturalContoursEditAdapter
import com.android.lixiang.liangwei.ui.fragment.area1.InfoEditFragment
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_illegal_architectural_contours_info.*
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.util.*

class IllegalArchitecturalContoursEditFragment : SupportFragment(), View.OnClickListener {

    private var mAdapter: IllegalArchitecturalContoursEditAdapter? = null
    private var searchBean: SearchBean? = null
    private var presenter: AllInfoEditPresenter? = null
    var handler = Handler()
    private var fragment: IllegalArchitecturalContoursEditFragment? = null
    private var isEdit: Boolean? = false
    private var mDeleteIndexs: String? = ""
    private var mSwipeRefreshView: SwipeRefreshView? = null
    private var mParams: WeakHashMap<String, Any>? = WeakHashMap()


    override fun onClick(v: View?) {
        when (v) {
            mDeleteBtn -> {
                val builder = AlertDialog.Builder(activity!!)
                builder.setTitle("确认要删除吗？")
                builder.setPositiveButton("确认") { dialog, _ ->
                    AllInfoEditFragment().deleteIllegal(mDeleteIndexs, presenter!!)
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
        if (requestCode == 0x009 && resultCode == ISupportFragment.RESULT_OK) {
            Logger.d(data!!.getString("INFO"))
        }
    }



    private fun initViews() {
        searchBean = (parentFragment as AllInfoEditFragment).getSearchData()
        presenter = (parentFragment as AllInfoEditFragment).getPresenter()
        isEdit = (parentFragment as AllInfoEditFragment).getEditState()!!
        mSwipeRefreshView = view!!.findViewById(R.id.mAllInfoSRL)
        fragment = this
        mAdapter = IllegalArchitecturalContoursEditAdapter(searchBean!!, context, fragment)
        mSwipeRefreshView!!.setColorSchemeColors(Color.parseColor("#6299FF"))


        /**
         * 非编辑状态
         */
        if(!isEdit!!){
            mSwipeRefreshView!!.isRefreshing = true
            Handler().postDelayed({
                mAllInfoLV.adapter = mAdapter
                mSwipeRefreshView!!.isRefreshing = false
            }, 1000)
        }



        if (isEdit!!) {
            mAllInfoLV.adapter = mAdapter
            mAdapter!!.isEdit()
            mAdapter!!.notifyDataSetChanged()
        } else {
            mAdapter!!.notifyDataSetChanged()
        }

        mDeleteBtn.setOnClickListener(this)

        mAllInfoLV.setOnItemClickListener { adapterView, view, i, l ->
            if (isEdit!!) {
                mDeleteIndexs = mDeleteIndexs!! + searchBean!!.data.list[i].number + ","
                mDeleteBtn.visibility = View.VISIBLE
                mAdapter!!.select(i)
                mAdapter!!.notifyDataSetChanged()

            } else {
                val bundle = Bundle()
                val fragment = InfoEditFragment().newInstance()
                bundle.putString("ID", searchBean!!.data.list[i].number)
                bundle.putString("TAG", "ILLEGALARCHITECTURALCONTOURSEDITFRAGMENT")
                fragment.arguments = bundle
                (parentFragment as AllInfoEditFragment).start(fragment)
            }
        }

        mSwipeRefreshView!!.setOnRefreshListener {
            Logger.d("LLLL")
            mParams!!["param"] = ""
            mParams!!["pagenum"] = "1"
            mParams!!["pagesize"] = "10"
            Handler().postDelayed({
                RestClient().builder()
                        .url("http://59.110.161.48:8088/search")
                        .params(mParams!!)
                        .success(object : ISuccess {
                            override fun onSuccess(response: String) {
                                Logger.d(response)
                                searchBean = Gson().fromJson(response, SearchBean::class.java)
                                mAdapter = IllegalArchitecturalContoursEditAdapter(searchBean!!, context, fragment)
                                mAllInfoLV.adapter = mAdapter
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
            mParams!!["param"] = ""
            mParams!!["pagenum"] = "1"
            mParams!!["pagesize"] = "10"
            RestClient().builder()
                    .url("http://59.110.161.48:8088/search")
                    .params(mParams!!)
                    .success(object : ISuccess {
                        override fun onSuccess(response: String) {
                            Logger.d(response)
                            searchBean = Gson().fromJson(response, SearchBean::class.java)
                            mAdapter = IllegalArchitecturalContoursEditAdapter(searchBean!!, context, fragment)
                            mAllInfoLV.adapter = mAdapter
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

    fun newInstance(): IllegalArchitecturalContoursEditFragment {
        val args = Bundle()
        val fragment = IllegalArchitecturalContoursEditFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_illegal_architectural_contours_info, container, false)
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
