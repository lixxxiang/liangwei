package com.android.lixiang.liangwei.ui.fragment.area2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import com.android.lixiang.liangwei.R
import com.android.lixiang.liangwei.ui.fragment.area3.bak.Species
import com.android.lixiang.liangwei.ui.adapter.MaterialTypeAdapter
import kotlinx.android.synthetic.main.fragment_material_type.*
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.util.ArrayList


class MaterialTypeFragment : SupportFragment(), View.OnClickListener {
    override fun onClick(p0: View?) {
        when (p0) {
            mDoneBtn -> {
                val bundle = Bundle()
                bundle.putString("MATERIAL_INDEX", index!!.toString())
                setFragmentResult(ISupportFragment.RESULT_OK, bundle)
                pop()
            }
        }
    }

    private var speciesList: MutableList<Species> = ArrayList()
    private var index: Int? = -1

    private fun initspecies() {
        for (i in 0..0) {
            val ziduan1 = Species("土")
            speciesList.add(ziduan1)
            val ziduan2 = Species("砖")
            speciesList.add(ziduan2)
            val ziduan3 = Species("砖混")
            speciesList.add(ziduan3)
            val ziduan4 = Species("其它")
            speciesList.add(ziduan4)
        }
    }


    private fun initViews() {
        mChooseSpeciesRLToolbar.title = "材料类型"
        (activity as AppCompatActivity).setSupportActionBar(mChooseSpeciesRLToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mChooseSpeciesRLToolbar.setNavigationOnClickListener {
            pop()
        }
    }

    fun newInstance(): MaterialTypeFragment {
        val args = Bundle()
        val fragment = MaterialTypeFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_material_type, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        initspecies()

        val adapter = MaterialTypeAdapter(context, R.layout.simple_list_item1, speciesList)
        speciesitems.adapter = adapter
        speciesitems.choiceMode = AbsListView.CHOICE_MODE_SINGLE
        adapter.setSelectedItem(arguments!!.getString("ID_INDEX").toInt())
        adapter.notifyDataSetInvalidated()
        index = arguments!!.getString("ID_INDEX").toInt()
        speciesitems.setOnItemClickListener { adapterView, view, i, l ->
            adapter.setSelectedItem(i)
            adapter.notifyDataSetInvalidated()
            index = i
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
