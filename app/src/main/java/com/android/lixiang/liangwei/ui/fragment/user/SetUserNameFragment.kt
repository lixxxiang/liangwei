package com.android.lixiang.liangwei.ui.fragment.user

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.InputFilter
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation
import android.widget.EditText
import android.widget.Toast
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.base.utils.view.SoftKeyboardListener
import com.android.lixiang.liangwei.R
import com.android.lixiang.liangwei.presenter.ResetPasswordPresenter
import com.android.lixiang.liangwei.presenter.SetUserNamePresenter
import com.android.lixiang.liangwei.presenter.data.bean.RegBean
import com.android.lixiang.liangwei.presenter.data.bean.ResetPasswordBean
import com.android.lixiang.liangwei.presenter.injection.component.DaggerSetUserNameFragmentComponent
import com.android.lixiang.liangwei.presenter.injection.module.SetUserNameModule
import com.android.lixiang.liangwei.presenter.view.ResetPasswordView
import com.android.lixiang.liangwei.presenter.view.SetUserNameView
import com.android.lixiang.liangwei.ui.fragment.HomeFragment
import kotlinx.android.synthetic.main.fragment_set_password.*
import kotlinx.android.synthetic.main.fragment_set_username.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.util.regex.Pattern

class SetUserNameFragment : BaseMvpFragment<SetUserNamePresenter>(), View.OnClickListener, SetUserNameView {
    var flag = false

    override fun returnReg(regBean: RegBean) {
        if (regBean.message == "success") {
            popTo(findFragment(HomeFragment().javaClass).javaClass, false)
            val snackBar = Snackbar.make(view!!, "登录成功", Snackbar.LENGTH_SHORT)
            snackBar.show()
        }
    }

    override fun test(string: String) {
    }

    override fun injectComponent() {
        DaggerSetUserNameFragmentComponent.builder().fragmentComponent(fragmentComponent).setUserNameModule(SetUserNameModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onClick(p0: View?) {
        when (p0) {
            mSetUserNameCommitBtn -> {
                val mSharedPreferences: SharedPreferences? = activity!!.getSharedPreferences("USER", Context.MODE_PRIVATE)
                val editor = mSharedPreferences!!.edit()
                editor.putString("username", set_nickname_tel_et.text.toString())
                editor.putString("pwd", arguments!!.getString("PWD"))
                editor.putString("tel", arguments!!.getString("TEL"))
                editor.apply()
                mPresenter.reg(arguments!!.getString("PWD"), set_nickname_tel_et.text.toString(), arguments!!.getString("TEL"))
//                popTo(findFragment(HomeFragment().javaClass).javaClass, false)
//                val snackBar = Snackbar.make(view!!, "登录成功", Snackbar.LENGTH_SHORT)
//                snackBar.show()
            }
        }
    }

    fun newInstance(): SetUserNameFragment {
        val args = Bundle()
        val fragment = SetUserNameFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set_username, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    fun setEditTextInhibitInputSpeChat(editText: EditText) {

        val filter = InputFilter { source, start, end, dest, dstart, dend ->
            val speChat = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]"
            val pattern = Pattern.compile(speChat)
            val matcher = pattern.matcher(source.toString())
            if (matcher.find())
                ""
            else
                null
        }
        editText.filters = arrayOf(filter)
    }

    private fun initViews() {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        set_nickname_title_tv.typeface = Typeface.createFromAsset(activity!!.assets, "fonts/pingfang.ttf")
        set_user_name_toolbar.title = "设置用户名"
        (activity as AppCompatActivity).setSupportActionBar(set_user_name_toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        setEditTextInhibitInputSpeChat(set_nickname_tel_et)
        set_nickname_tel_et.addTextChangedListener(mTextWatcher)
        SoftKeyboardListener.setListener(activity, object : SoftKeyboardListener.OnSoftKeyBoardChangeListener {
            override fun keyBoardShow(height: Int) {
                if (mSetPasswordAppTitleRL != null) {
                    val animationSet = AnimationSet(true)
                    val scaleAnimation = ScaleAnimation(1f, 0f, 1f, 0f,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0f)
                    scaleAnimation.duration = 200
                    animationSet.addAnimation(scaleAnimation)
                    mSetPasswordAppTitleRL.startAnimation(animationSet)
                    animationSet.fillAfter = true
                    Handler().postDelayed({
                        mSetPasswordAppTitleRL.visibility = View.GONE
                    }, 200)
                }
            }

            override fun keyBoardHide(height: Int) {
                if (mSetPasswordAppTitleRL != null) {
                    mSetPasswordAppTitleRL.visibility = View.VISIBLE
                    val animationSet = AnimationSet(true)
                    val scaleAnimation = ScaleAnimation(0f, 1f, 0f, 1f,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0f)
                    scaleAnimation.duration = 200
                    animationSet.addAnimation(scaleAnimation)
                    mSetPasswordAppTitleRL.startAnimation(animationSet)
                    animationSet.fillAfter = true
                }

            }
        })

        mSetUserNameCommitBtn.setOnClickListener(this)
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
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
            flag = true
            if (set_nickname_tel_et.text.toString() == "") {
                mSetUserNameCommitBtn.setBackgroundResource(R.drawable.sign_up_btn_shape)
                flag = false
                mSetUserNameCommitBtn.isClickable = false
            }
            if (flag) {
                mSetUserNameCommitBtn.setBackgroundResource(R.drawable.sign_up_btn_shape_success)
                mSetUserNameCommitBtn.isClickable = true
            }
        }
    }
}