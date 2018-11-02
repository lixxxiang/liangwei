package com.android.lixiang.liangwei.ui.fragment.area2

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.TabLayout
import android.support.v4.view.PagerAdapter
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatTextView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.liangwei.R
import com.android.lixiang.liangwei.presenter.AllInfoEditPresenter
import com.android.lixiang.liangwei.presenter.data.bean.DeleteExamInfoBean
import com.android.lixiang.liangwei.presenter.data.bean.DeleteIllegalInfoBean
import com.android.lixiang.liangwei.presenter.data.bean.ListExamInfoBean
import com.android.lixiang.liangwei.presenter.data.bean.SearchBean
import com.android.lixiang.liangwei.presenter.injection.component.DaggerAllInfoEditFragmentComponent
import com.android.lixiang.liangwei.presenter.injection.module.AllInfoEditModule
import com.android.lixiang.liangwei.presenter.view.AllInfoEditView
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_my_insure_edit.*
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import android.widget.ListView
import com.android.lixiang.base.net.RestClient
import com.android.lixiang.base.utils.view.SwipeRefreshView
import com.android.lixiang.liangwei.ui.adapter.ApprovalInfomationEditViewPagerAdapter
import com.android.lixiang.liangwei.ui.adapter.IllegalArchitecturalContoursEditAdapter
import com.android.lixiang.liangwei.ui.fragment.area1.InfoEditFragment
import com.blankj.utilcode.util.KeyboardUtils
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_result_info.*
import org.jetbrains.anko.find
import java.util.*


class AllInfoEditFragment : BaseMvpFragment<AllInfoEditPresenter>(), View.OnClickListener, AllInfoEditView {
    private var listTitle: MutableList<String>? = mutableListOf()
    private var searchBean: SearchBean? = null
    private var deleteIllegalInfoBean: DeleteIllegalInfoBean? = null
    private var deleteIllegalInfoBean2: DeleteIllegalInfoBean? = null

    private var listExamInfoBean: ListExamInfoBean? = null
    private var isEdit: Boolean? = false
    private var index: Int? = 0
    private var fragment: AllInfoEditFragment? = null
    private var listView1: ListView? = null
    private var listView2: ListView? = null
    private var adapter: IllegalArchitecturalContoursEditAdapter? = null
    private var adapter2: ApprovalInfomationEditViewPagerAdapter? = null
    private var params1: WeakHashMap<String, Any>? = WeakHashMap()
    private var params2: WeakHashMap<String, Any>? = WeakHashMap()
    private var Ls: MutableList<String>? = mutableListOf()
    private var Ss: MutableList<String>? = mutableListOf()
    private var Ls_D: MutableList<Boolean>? = mutableListOf()
    private var Ss_D: MutableList<Boolean>? = mutableListOf()
    private var LIds: String? = ""
    private var SIds: String? = ""
    private var mParams: WeakHashMap<String, Any>? = WeakHashMap()
    private var mSwipeRefreshView: SwipeRefreshView? = null
    private var mSwipeRefreshView2: SwipeRefreshView? = null
    private var mNoDataTV: AppCompatTextView? = null
    private var mNoDataTV2: AppCompatTextView? = null

    override fun returnDeleteIllegalInfo(deleteIllegalInfoBean: DeleteIllegalInfoBean) {
        Logger.d(deleteIllegalInfoBean.message)
        if (deleteIllegalInfoBean.message == "success") {
            mPresenter.search("", "1", "100")
        }
    }

    override fun returnDeleteExamInfo(deleteExamInfoBean: DeleteExamInfoBean) {
        Logger.d(deleteExamInfoBean.message)
        if (deleteExamInfoBean.message == "success") {
            mPresenter.search("", "1", "100")
        }
    }


    override fun returnSearch(searchBean: SearchBean) {
        this.searchBean = searchBean
        for (i in 0 until searchBean.data.list.size) {
            Ls!!.add(searchBean.data.list[i].number)
            Ls_D!!.add(false)
        }

        mPresenter.listExamInfo("1", "100")
    }

    override fun returnListExamInfo(listExamInfoBean: ListExamInfoBean) {
        this.listExamInfoBean = listExamInfoBean
//        if (index == 1) {
//            mMyInsureVP.currentItem = 1
//        }
        for (i in 0 until listExamInfoBean.data.list.size) {
            Ss!!.add(listExamInfoBean.data.list[i].number)
            Ss_D!!.add(false)
        }
        if (!searchBean!!.data.list.isEmpty())
            adapter = IllegalArchitecturalContoursEditAdapter(searchBean, context, fragment)
        else {
            mNoDataTV!!.visibility = View.VISIBLE
            listView1!!.visibility = View.GONE
        }

        if (!listExamInfoBean.data.list.isEmpty())
            adapter2 = ApprovalInfomationEditViewPagerAdapter(listExamInfoBean, fragment, context)
        else{
            mNoDataTV2!!.visibility = View.VISIBLE
            listView2!!.visibility = View.GONE
        }

        listView1!!.adapter = adapter
        listView2!!.adapter = adapter2

        if (isEdit!!) {
            adapter!!.isEdit()
            adapter!!.notifyDataSetChanged()
            adapter2!!.isEdit()
            adapter2!!.notifyDataSetChanged()
        }

        if (mSwipeRefreshView != null) {
            mSwipeRefreshView!!.isRefreshing = false
        }

        if (mSwipeRefreshView2 != null) {
            mSwipeRefreshView2!!.isRefreshing = false
        }

        if(listExamInfoBean.data.list.isEmpty() && searchBean!!.data.list.isEmpty()){
            mDeleteBtn.visibility = View.GONE
            mRecordDoneBtn.visibility = View.GONE

        }
    }

    fun deleteIllegal(mDeleteIndexs: String?, presenter: AllInfoEditPresenter) {
        presenter.deleteIllegalInfo(mDeleteIndexs!!)
    }

    fun deleteApproval(mDeleteIndexs: String?, presenter: AllInfoEditPresenter) {
        presenter.deleteExamInfo(mDeleteIndexs!!)
    }

    fun getPresenter(): AllInfoEditPresenter {
        return mPresenter
    }

    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle) {
        super.onFragmentResult(requestCode, resultCode, data)
        Logger.d(data)
        if (requestCode == 0x009 && resultCode == ISupportFragment.RESULT_OK) {
            if (data.getString("INFO") == "TT") {
                mPresenter.search("", "1", "100")
                index = 0
            }
        } else if (requestCode == 0x008 && resultCode == ISupportFragment.RESULT_OK) {
            if (data.getString("INFO") == "TT") {
                mPresenter.search("", "1", "100")
                index = 1
            }
        }
    }

    override fun injectComponent() {
        DaggerAllInfoEditFragmentComponent.builder().fragmentComponent(fragmentComponent).allInfoEditModule(AllInfoEditModule()).build().inject(this)
        mPresenter.mView = this
    }

    fun getEditState(): Boolean? {
        return isEdit
    }


    override fun onClick(v: View?) {
        when (v) {
            mRecordDoneBtn -> {
                if (!isEdit!!) {
                    mRecordDoneBtn.text = "完成"
                    isEdit = true
                    adapter!!.isEdit()
                    adapter2!!.isEdit()

                    adapter!!.notifyDataSetChanged()
                    adapter2!!.notifyDataSetChanged()
                } else {
                    mRecordDoneBtn.text = "编辑"
                    isEdit = false
                    adapter!!.isNotEdit()
                    adapter2!!.isNotEdit()
                    adapter!!.notifyDataSetChanged()
                    adapter2!!.notifyDataSetChanged()
                    mDeleteBtn.visibility = View.GONE
                }
            }

            mDeleteBtn -> {
                for (i in 0 until Ls_D!!.size) {
                    if (Ls_D!![i])
                        LIds = "" + LIds + Ls!![i] + ","
                }

                for (i in 0 until Ss_D!!.size) {
                    if (Ss_D!![i])
                        SIds = "" + SIds + Ss!![i] + ","
                }

                params1!!["ids"] = LIds
                params2!!["ids"] = SIds
                val builder = AlertDialog.Builder(activity!!)
                builder.setTitle("确认要删除吗？")
                builder.setPositiveButton("确认") { dialog, _ ->
                    when (mMyInsureVP.currentItem) {
                        0 -> {
                            RestClient().builder()
                                    .url("http://59.110.161.48:8088/deleteillegalinfo")
                                    .params(params1!!)
                                    .success(object : ISuccess {
                                        override fun onSuccess(response: String) {
                                            Logger.d(response)
                                            deleteIllegalInfoBean = Gson().fromJson(response, DeleteIllegalInfoBean::class.java)
                                            if (deleteIllegalInfoBean!!.message == "success") {
                                                mPresenter.search("", "1", "100")
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
                        }

                        1 -> {
                            RestClient().builder()
                                    .url("http://59.110.161.48:8088/deleteexaminfo")
                                    .params(params2!!)
                                    .success(object : ISuccess {
                                        override fun onSuccess(response: String) {
                                            Logger.d(response)
                                            deleteIllegalInfoBean2 = Gson().fromJson(response, DeleteIllegalInfoBean::class.java)
                                            if (deleteIllegalInfoBean2!!.message == "success") {
                                                mPresenter.search("", "1", "100")
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
                        }
                    }

                    Logger.d(params1)

                }.setNegativeButton("取消") { dialogInterface, i ->
                    dialogInterface.dismiss()
                }

                builder.create().show()
            }
        }
    }

    fun getSearchData(): SearchBean? {
        return if (searchBean != null)
            searchBean!!
        else
            null
    }

    fun getListExamData(): ListExamInfoBean? {
        return if (listExamInfoBean != null)
            listExamInfoBean!!
        else
            null
    }

    private fun initViews() {
        mMyInsureToolbar.title = "全部信息"
        (activity as AppCompatActivity).setSupportActionBar(mMyInsureToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        fragment = this
        mRecordDoneBtn.setOnClickListener(this)
        mDeleteBtn.setOnClickListener(this)
        mMyInsureToolbar.setNavigationOnClickListener {
            KeyboardUtils.hideSoftInput(activity!!)

            pop()
        }
//        mSwipeRefreshView = view!!.findViewById(R.id.mSRV)

        listTitle!!.add("违章建筑轮廓")
        listTitle!!.add("审批信息")
        mMyInsureTL.tabMode = TabLayout.MODE_FIXED
        mMyInsureTL.addTab(mMyInsureTL.newTab().setText(listTitle!![0]))
        mMyInsureTL.addTab(mMyInsureTL.newTab().setText(listTitle!![1]))
        mMyInsureVP.offscreenPageLimit = 2
        mMyInsureVP.currentItem = 0
        val viewList = ArrayList<View>()
        val view1 = layoutInflater.inflate(R.layout.listview, null)
        mSwipeRefreshView = view1.findViewById(R.id.mSRV)

        mSwipeRefreshView!!.setColorSchemeColors(Color.parseColor("#6299FF"))
        mSwipeRefreshView!!.isRefreshing = true
        Handler().postDelayed({
            mSwipeRefreshView!!.isRefreshing = false
            mPresenter.search("", "1", "100")

        }, 1000)
        val view2 = layoutInflater.inflate(R.layout.listview, null)
        mSwipeRefreshView2 = view2.findViewById(R.id.mSRV)
        mSwipeRefreshView2!!.setColorSchemeColors(Color.parseColor("#6299FF"))

        viewList.add(view1)
        viewList.add(view2)
        mMyInsureVP.adapter = object : PagerAdapter() {
            override fun instantiateItem(container: ViewGroup, position: Int): Any {
                mMyInsureVP.addView(viewList.get(position))
                return viewList[position]
            }

            override fun isViewFromObject(view: View, `object`: Any): Boolean {
                return view == `object`
            }

            override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
                mMyInsureVP.removeView(viewList.get(position))
            }

            override fun getCount(): Int {
                return viewList.size
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return listTitle!!.get(position)
            }
        }
        listView1 = view1.findViewById(R.id.listview) as ListView
        listView2 = view2.findViewById(R.id.listview) as ListView
        mNoDataTV = view1.findViewById(R.id.mNoResultTV) as AppCompatTextView
        mNoDataTV2 = view2.findViewById(R.id.mNoResultTV) as AppCompatTextView

        mMyInsureTL.setupWithViewPager(mMyInsureVP)
        listView1!!.setOnItemClickListener { adapterView, view, i, l ->
            if (isEdit!!) {
                Ls_D!![i] = !Ls_D!![i]
                mDeleteBtn.visibility = View.VISIBLE
                adapter!!.select(i)
                adapter!!.notifyDataSetChanged()
                val selectIndexs = adapter!!.selectIndexs
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
                bundle.putString("TAG", "ILLEGALARCHITECTURALCONTOURSEDITFRAGMENT")
                fragment.arguments = bundle
                start(fragment)
            }
        }

        listView2!!.setOnItemClickListener { adapterView, view, i, l ->
            if (isEdit!!) {
                Ss_D!![i] = !Ss_D!![i]
                mDeleteBtn.visibility = View.VISIBLE
                adapter2!!.select(i)
                adapter2!!.notifyDataSetChanged()
                val selectIndexs = adapter2!!.selectIndexs
                var count = 0
                for (i in 0 until selectIndexs.size) {
                    if (selectIndexs[i] == -1)
                        count++
                }
                if (count == selectIndexs.size)
                    mDeleteBtn.visibility = View.GONE
            } else {
                val bundle = Bundle()
                val fragment = ApprovalInformationEditFragment().newInstance()
                bundle.putString("TAG", "ApprovalInfomationEditViewPagerFragment")
                bundle.putString("ID", listExamInfoBean!!.data.list[i].number)
                fragment.arguments = bundle
                start(fragment)
            }
        }

        mSwipeRefreshView!!.setOnRefreshListener {
            //                    Logger.d("LLLL")
//                    mParams!!["param"] = ""
//                    mParams!!["pagenum"] = "1"
//                    mParams!!["pagesize"] = "10"
//                    Handler().postDelayed({
//                        RestClient().builder()
//                                .url("http://59.110.161.48:8088/search")
//                                .params(mParams!!)
//                                .success(object : ISuccess {
//                                    override fun onSuccess(response: String) {
//                                        Logger.d(response)
//                                        searchBean = Gson().fromJson(response, SearchBean::class.java)
//                                        mSwipeRefreshView!!.isRefreshing = false
//                                    }
//                                })
//                                .failure(object : IFailure {
//                                    override fun onFailure() {
//
//                                    }
//                                })
//                                .error(object : IError {
//                                    override fun onError(code: Int, msg: String) {
//                                        mSwipeRefreshView!!.isRefreshing = false
//                                    }
//                                })
//                                .build()
//                                .post()
//
//
//                    }, 2000)
            mPresenter.search("", "1", "100")

        }

        mSwipeRefreshView2!!.setOnRefreshListener {
            mPresenter.search("", "1", "100")
        }

//        mSwipeRefreshView!!.setOnLoadMoreListener {
//            when (mMyInsureVP.currentItem) {
//                0 -> {
//                    mParams!!["param"] = ""
//                    mParams!!["pagenum"] = "1"
//                    mParams!!["pagesize"] = "100"
//                    RestClient().builder()
//                            .url("http://59.110.161.48:8088/search")
//                            .params(mParams!!)
//                            .success(object : ISuccess {
//                                override fun onSuccess(response: String) {
//                                    Logger.d(response)
//                                    searchBean = Gson().fromJson(response, SearchBean::class.java)
////                                    mSwipeRefreshView!!.setLoading(false)
//                                    adapter = IllegalArchitecturalContoursEditAdapter(searchBean, context, fragment)
//                                    adapter!!.notifyDataSetChanged()
//
//                                }
//                            })
//                            .failure(object : IFailure {
//                                override fun onFailure() {
//
//                                }
//                            })
//                            .error(object : IError {
//                                override fun onError(code: Int, msg: String) {
//
//                                }
//                            })
//                            .build()
//                            .post()
//                }
//            }
//        }
    }

    fun newInstance(): AllInfoEditFragment {
        val args = Bundle()
        val fragment = AllInfoEditFragment()
        fragment.arguments = args
        return fragment
    }

    fun test(s: String) {
        Logger.d(s)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_insure_edit, container, false)
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
