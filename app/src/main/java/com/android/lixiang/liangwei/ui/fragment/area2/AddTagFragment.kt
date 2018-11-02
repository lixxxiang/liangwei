package com.android.lixiang.liangwei.ui.fragment.area2

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.liangwei.R
import com.android.lixiang.liangwei.presenter.AddTagPresenter
import com.android.lixiang.liangwei.presenter.data.bean.UpdateLabelBean
import com.android.lixiang.liangwei.presenter.injection.component.DaggerAddTagFragmentComponent
import com.android.lixiang.liangwei.presenter.injection.module.AddTagModule
import com.android.lixiang.liangwei.presenter.view.AddTagView
import com.blankj.utilcode.util.KeyboardUtils
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_add_tag.*
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


class AddTagFragment : BaseMvpFragment<AddTagPresenter>(), View.OnClickListener, AddTagView {
    override fun injectComponent() {
        DaggerAddTagFragmentComponent.builder().fragmentComponent(fragmentComponent).addTagModule(AddTagModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun returnUpdateLabel(updateLabelBean: UpdateLabelBean) {
        if (updateLabelBean.message == "success") {
            KeyboardUtils.hideSoftInput(activity)
            val bundle = Bundle()
            bundle.putString("INFO", "TT")
            setFragmentResult(ISupportFragment.RESULT_OK, bundle)
            pop()
        }
    }

    private val MAX_NUM = 20

    override fun onClick(p0: View?) {
        when (p0) {
            mAddTagDoneBtn -> {
                Logger.d(arguments!!.getString("APPROVAL_ID"))
                mPresenter.updateLabel(mAddTagET.text.toString(), arguments!!.getString("APPROVAL_ID"))

            }
        }
    }

    private fun initViews() {
        mAddTagToolbar.title = "添加标签"
        (activity as AppCompatActivity).setSupportActionBar(mAddTagToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mAddTagET.isFocusable = true
        mAddTagET.isFocusableInTouchMode = true
        mAddTagET.requestFocus()
        mAddTagET.addTextChangedListener(mTextWatcher)
        KeyboardUtils.showSoftInput(activity)

        mAddTagDoneBtn.setOnClickListener(this)
        mAddTagToolbar.setNavigationOnClickListener {
            KeyboardUtils.hideSoftInput(activity!!)

            pop()
        }
    }

    fun newInstance(): AddTagFragment {
        val args = Bundle()
        val fragment = AddTagFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_tag, container, false)
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

    private var mTextWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // TODO Auto-generated method stub
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                       after: Int) {
            // TODO Auto-generated method stub
        }

        override fun afterTextChanged(s: Editable) {
            // TODO Auto-generated method stub
            if (s.length > MAX_NUM)
                s.delete(MAX_NUM, s.length)
            val num = MAX_NUM - s.length
            mCounts.text = num.toString() + "/20"
        }
    }


}
