package com.android.lixiang.liangwei.ui.fragment.area3.bak

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.liangwei.R
import com.android.lixiang.liangwei.ui.adapter.BankAdapter
import kotlinx.android.synthetic.main.fragment_choose_bankofdeposit.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


class ChooseBankOfDepositFragment : SupportFragment(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v) {
            mBankOfDepositNextStepRL -> {

            }
        }
    }

    private var bankList: MutableList<Bank> = mutableListOf()
    private fun initBanks(){
        for (i in 0..0) {
            val ziduan1 = Bank("字段", R.drawable.img_graynike)
            bankList.add(ziduan1)
            val zhonghang = Bank("吉林省长春市前进大街中国银行", R.drawable.img_graynike)
            bankList.add(zhonghang)
            val ziduan2 = Bank("字段", R.drawable.img_graynike)
            bankList.add(ziduan2)


        }
    }
    private fun initViews() {
        mBankOfDepositToolbar.title = "选择开户行"
        (activity as AppCompatActivity).setSupportActionBar(mBankOfDepositToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    fun newInstance(): ChooseBankOfDepositFragment {
        val args = Bundle()
        val fragment = ChooseBankOfDepositFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_choose_bankofdeposit, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        initBanks()
        val adapter=BankAdapter(context,R.layout.bank_item,bankList)
        bankofdeposititems.adapter=adapter

        bankofdeposititems.setOnItemClickListener { adapterView, view, i, l ->

            adapter.setSelectedItem(i)
            adapter.notifyDataSetInvalidated()

        }

    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()

        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

    }
}
