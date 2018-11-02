package com.android.lixiang.liangwei.ui.fragment.area2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.liangwei.R
import com.android.lixiang.liangwei.presenter.SelectPropertiesPresenter
import com.android.lixiang.liangwei.presenter.data.bean.UpdateStatusBean
import com.android.lixiang.liangwei.presenter.injection.component.DaggerSelectPropertiesFragmentComponent
import com.android.lixiang.liangwei.presenter.injection.module.SelectPropertiesModule
import com.android.lixiang.liangwei.presenter.view.SelectPropertiesView
import com.android.lixiang.liangwei.ui.fragment.area3.bak.Species
import com.android.lixiang.liangwei.ui.adapter.SelectPropertiesAdapter
import com.blankj.utilcode.util.KeyboardUtils
import kotlinx.android.synthetic.main.fragment_material_type.*
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.util.ArrayList


class SelectPropertiesFragment : BaseMvpFragment<SelectPropertiesPresenter>(), View.OnClickListener, SelectPropertiesView {
    override fun returnUpdateStatus(updateStatusBean: UpdateStatusBean) {
        if(updateStatusBean.message == "success"){
//            popTo(AllInfoEditFragment::class.java, false)
            val bundle = Bundle()
            bundle.putString("INFO", "TT")
            setFragmentResult(ISupportFragment.RESULT_OK, bundle)
            KeyboardUtils.hideSoftInput(activity!!)

            pop()
        }
    }

    override fun onBackPressedSupport(): Boolean {
        val bundle = Bundle()
        bundle.putString("INFO", "T")
        setFragmentResult(ISupportFragment.RESULT_OK, bundle)
        return super.onBackPressedSupport()
    }

    override fun injectComponent() {
        DaggerSelectPropertiesFragmentComponent.builder().fragmentComponent(fragmentComponent).selectPropertiesModule(SelectPropertiesModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onClick(p0: View?) {
        when (p0) {
            mDoneBtn -> {
                mPresenter.updateLabel(index.toString(), arguments!!.getString("APPROVAL_ID"))
            }
        }
    }

    private var speciesList: MutableList<Species> = ArrayList()
    private var index: Int? = 0

    private fun initspecies() {
        for (i in 0..0) {
            val ziduan1 = Species("未整改")
            speciesList.add(ziduan1)
            val ziduan2 = Species("责令后整改")
            speciesList.add(ziduan2)
            val ziduan3 = Species("正在整改")
            speciesList.add(ziduan3)
            val ziduan4 = Species("已拆除")
            speciesList.add(ziduan4)
            val ziduan5 = Species("未整改且继续加盖")
            speciesList.add(ziduan5)
            val ziduan6 = Species("其它")
            speciesList.add(ziduan6)
        }
    }


    private fun initViews() {
        mChooseSpeciesRLToolbar.title = "选择属性"
        (activity as AppCompatActivity).setSupportActionBar(mChooseSpeciesRLToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mChooseSpeciesRLToolbar.setNavigationOnClickListener {
            pop()
        }
    }

    fun newInstance(): SelectPropertiesFragment {
        val args = Bundle()
        val fragment = SelectPropertiesFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_properties, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        initspecies()

        val adapter = SelectPropertiesAdapter(context, R.layout.simple_list_item1, speciesList)
        speciesitems.adapter = adapter
        speciesitems.choiceMode = AbsListView.CHOICE_MODE_SINGLE
        speciesitems.setOnItemClickListener { adapterView, view, i, l ->
            index = i + 1
            adapter.setSelectedItem(i)
            adapter.notifyDataSetInvalidated()
        }

        mDoneBtn.setOnClickListener(this)
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }


}
