package com.android.lixiang.liangwei.ui.fragment.user

import android.content.Context
import android.content.SharedPreferences
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.NinePatchDrawable
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.ScaleAnimation
import android.widget.Toast
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.base.utils.view.SoftKeyboardListener
import com.android.lixiang.liangwei.R
import com.android.lixiang.liangwei.presenter.LoginPresenter
import com.android.lixiang.liangwei.presenter.data.bean.LoginBean
import com.android.lixiang.liangwei.presenter.injection.component.DaggerLoginFragmentComponent
import com.android.lixiang.liangwei.presenter.injection.component.DaggerRegisterFragmentComponent
import com.android.lixiang.liangwei.presenter.injection.module.LoginModule
import com.android.lixiang.liangwei.presenter.injection.module.RegisterModule
import com.android.lixiang.liangwei.presenter.view.LoginView
import com.android.lixiang.liangwei.ui.fragment.HomeFragment
import com.android.lixiang.liangwei.ui.fragment.area3.bak.OnMultiClickListener
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.RegexUtils
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_login.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator


class LoginFragment : BaseMvpFragment<LoginPresenter>(), View.OnClickListener, LoginView {
    private var flag1: Boolean? = false
    private var flag2: Boolean? = false
    override fun injectComponent() {
        DaggerLoginFragmentComponent.builder().fragmentComponent(fragmentComponent).loginModule(LoginModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun test(string: String) {
    }

    override fun returnLogin(loginBean: LoginBean) {
        Logger.d(loginBean.message)
        if (loginBean.message == "success") {
            val mSharedPreferences2: SharedPreferences? = activity!!.getSharedPreferences("USER", Context.MODE_PRIVATE)
            val editor2 = mSharedPreferences2!!.edit()
            editor2.putString("username", loginBean.data.user.name)
            editor2.putString("pwd", loginBean.data.user.password)
            editor2.putString("tel", loginBean.data.user.phone)
            editor2.apply()
            val mSharedPreferences: SharedPreferences? = activity!!.getSharedPreferences("LOGIN", Context.MODE_PRIVATE)
            val editor = mSharedPreferences!!.edit()
            editor.putString("login", "1")
            editor.apply()
            popTo(findFragment(HomeFragment().javaClass).javaClass, false)
            val snackBar = Snackbar.make(view!!, "登录成功", Snackbar.LENGTH_SHORT)
            snackBar.show()
        } else if (loginBean.message == "密码不正确") {
            Toast.makeText(activity, "密码错误", Toast.LENGTH_LONG).show()
        }
    }

    private var isHide: Boolean? = true
    override fun onClick(v: View?) {
        when (v) {
            mRegisterBtn -> {
                startWithPop(RegisterFragment().newInstance())
            }

            mShowHideRL -> {
                if (isHide!!) {
                    mShowIV.visibility = View.VISIBLE
                    mHideIV.visibility = View.GONE
                    mPwdET.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                    isHide = false
                } else {
                    mShowIV.visibility = View.GONE
                    mHideIV.visibility = View.VISIBLE
                    mPwdET.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                    isHide = true
                }
            }

            mLoginBtn -> {
                mPresenter.login(mAccountET.text.toString(), mPwdET.text.toString())
            }

            mCloseBtn -> {
                KeyboardUtils.hideSoftInput(activity!!)

                pop()
            }
        }
    }

    fun newInstance(): LoginFragment {
        val args = Bundle()
        val fragment = LoginFragment()
        fragment.arguments = args
        return fragment
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        mAppTitle.typeface = Typeface.createFromAsset(activity!!.assets, "fonts/pingfang.ttf")

        mAccountET.addTextChangedListener(mTelTextWatcher)
        mPwdET.addTextChangedListener(mVcodeTextWatcher)

        SoftKeyboardListener.setListener(activity, object : SoftKeyboardListener.OnSoftKeyBoardChangeListener {
            override fun keyBoardShow(height: Int) {
                if (mAppTitleRL != null) {
                    val animationSet = AnimationSet(true)
                    val scaleAnimation = ScaleAnimation(1f, 0f, 1f, 0f,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0f)
                    scaleAnimation.duration = 200
                    animationSet.addAnimation(scaleAnimation)
                    mAppTitleRL.startAnimation(animationSet)
                    animationSet.fillAfter = true
                    Handler().postDelayed({
                        mAppTitleRL.visibility = View.GONE
                    }, 200)
                }
            }


            override fun keyBoardHide(height: Int) {
                if (mAppTitleRL != null) {
                    mAppTitleRL.visibility = View.VISIBLE
                    val animationSet = AnimationSet(true)
                    val scaleAnimation = ScaleAnimation(0f, 1f, 0f, 1f,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0f)
                    scaleAnimation.duration = 200
                    animationSet.addAnimation(scaleAnimation)
                    mAppTitleRL.startAnimation(animationSet)
                    animationSet.fillAfter = true
                }
            }
        })

        mRegisterBtn.setOnClickListener(this)
        mShowHideRL.setOnClickListener(this)
        mLoginBtn.setOnClickListener(this)
        mCloseBtn.setOnClickListener(this)
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultVerticalAnimator()
    }

    private var mTelTextWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // TODO Auto-generated method stub
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                       after: Int) {
            // TODO Auto-generated method stub
        }

        override fun afterTextChanged(s: Editable) {
            // TODO Auto-generated method stub
            flag1 = true
            if (mAccountET.text.toString() == "") {
                mLoginBtn.setBackgroundResource(R.drawable.sign_up_btn_shape)
                flag1 = false
                mLoginBtn.isClickable = false
            }
            if (flag1!! && flag2!!) {
                mLoginBtn.setBackgroundResource(R.drawable.sign_up_btn_shape_dark)
                mLoginBtn.isClickable = true
            }
        }
    }

    private var mVcodeTextWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // TODO Auto-generated method stub
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                       after: Int) {
            // TODO Auto-generated method stub
        }

        override fun afterTextChanged(s: Editable) {
            // TODO Auto-generated method stub
            flag2 = true
            if (mPwdET.text.toString() == "") {
                mLoginBtn.setBackgroundResource(R.drawable.sign_up_btn_shape)
                flag2 = false
                mLoginBtn.isClickable = false
            }

            if (flag1!! && flag2!!) {
                mLoginBtn.setBackgroundResource(R.drawable.sign_up_btn_shape_dark)
                mLoginBtn.isClickable = true
            }
        }
    }
}