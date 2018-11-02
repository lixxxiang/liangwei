package com.android.lixiang.liangwei.ui.fragment.user

import android.app.Fragment
import android.graphics.*
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.text.Editable
import android.text.TextWatcher
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
import com.android.lixiang.liangwei.presenter.InfoCollectEntryPresenter
import com.android.lixiang.liangwei.presenter.RegisterPresenter
import com.android.lixiang.liangwei.presenter.data.bean.SendBean
import com.android.lixiang.liangwei.presenter.data.bean.VerifyBean
import com.android.lixiang.liangwei.presenter.injection.component.DaggerRegisterFragmentComponent
import com.android.lixiang.liangwei.presenter.injection.module.RegisterModule
import com.android.lixiang.liangwei.presenter.view.InfoCollectEntryView
import com.android.lixiang.liangwei.presenter.view.RegisterView
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.RegexUtils
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_register.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.util.*


class RegisterFragment : BaseMvpFragment<RegisterPresenter>(), View.OnClickListener, RegisterView {
    private var telephone: String? = null
    private var vCode: String? = null
    private var flag1: Boolean? = false
    private var flag2: Boolean? = false
    override fun returnVerify(verifyBean: VerifyBean) {
        Logger.d(verifyBean.message)
        when {
            verifyBean.message == "success" -> {
                val fragment = SetPasswordFragment().newInstance()
                val bundle = Bundle()
                bundle.putString("TEL", telephone)
                fragment.arguments = bundle
                start(fragment)
                KeyboardUtils.hideSoftInput(activity!!)
            }
            verifyBean.message == "验证码错误或失效" -> Toast.makeText(activity!!, "验证码不正确", Toast.LENGTH_SHORT).show()
            else -> Toast.makeText(activity, "???", Toast.LENGTH_SHORT).show()
        }
    }

    override fun returnSend(sendBean: SendBean) {
        Logger.d(sendBean.message)
        when {
            sendBean.message == "success" -> {
                mCountDownTV.start()
                mVcodeTV.text = String.format("已向手机%s发送验证码", hideTel(telephone!!))
                mVcodeTV.visibility = View.VISIBLE
            }
            sendBean.message == "用户已注册" -> Toast.makeText(activity!!, "该手机号码已注册，请直接登录", Toast.LENGTH_SHORT).show()
            else -> {
                mCountDownTV.start()
                Toast.makeText(activity, "验证码获取失败", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun injectComponent() {
        DaggerRegisterFragmentComponent.builder().fragmentComponent(fragmentComponent).registerModule(RegisterModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun test(string: String) {
        Logger.d(string)
    }

    override fun onClick(v: View?) {
        when (v) {
            mNextStepBtn -> {
                telephone = mTelET.text.toString()
                vCode = mVcodeET.text.toString()
                if (RegexUtils.isMobileExact(telephone)) {
                    mPresenter.verify(telephone!!, vCode!!)
                } else
                    Toast.makeText(activity, "请输入正确的手机号码", Toast.LENGTH_SHORT).show()
            }

            mCloseBtn -> {
                KeyboardUtils.hideSoftInput(activity!!)

                pop()
            }

            mLoginBtn -> {
                startWithPop(LoginFragment().newInstance())
            }

            mCountDownTV -> {
                telephone = mTelET.text.toString()
                if (telephone!!.isEmpty()) {
                    Toast.makeText(activity, "手机号码不能为空", Toast.LENGTH_SHORT).show()
                } else {
                    if (RegexUtils.isMobileExact(telephone)) {
                        mPresenter.send(telephone!!)
                    } else {
                        Toast.makeText(activity, "请输入正确的手机号码", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    fun newInstance(): RegisterFragment {
        val args = Bundle()
        val fragment = RegisterFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        sign_up_title_tv.typeface = Typeface.createFromAsset(activity!!.assets, "fonts/pingfang.ttf")
        mCountDownTV.setCountDownMillis(60000)
        SoftKeyboardListener.setListener(activity, object : SoftKeyboardListener.OnSoftKeyBoardChangeListener {
            override fun keyBoardShow(height: Int) {
                if (sign_up_title_rl != null) {
                    val animationSet = AnimationSet(true)
                    val scaleAnimation = ScaleAnimation(1f, 0f, 1f, 0f,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0f)
                    scaleAnimation.duration = 200
                    animationSet.addAnimation(scaleAnimation)
                    sign_up_title_rl.startAnimation(animationSet)
                    animationSet.fillAfter = true
                    Handler().postDelayed({
                        sign_up_title_rl.visibility = View.GONE
                    }, 200)
                }
            }


            override fun keyBoardHide(height: Int) {
                if (sign_up_title_rl != null) {
                    sign_up_title_rl.visibility = View.VISIBLE
                    val animationSet = AnimationSet(true)
                    val scaleAnimation = ScaleAnimation(0f, 1f, 0f, 1f,
                            Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0f)
                    scaleAnimation.duration = 200
                    animationSet.addAnimation(scaleAnimation)
                    sign_up_title_rl.startAnimation(animationSet)
                    animationSet.fillAfter = true
                }
            }
        })
        mNextStepBtn.setOnClickListener(this)
        mCloseBtn.setOnClickListener(this)
        mLoginBtn.setOnClickListener(this)
        mCountDownTV.setOnClickListener(this)
        mNextStepBtn.isClickable = false
        mTelET.addTextChangedListener(mTelTextWatcher)
        mVcodeET.addTextChangedListener(mVcodeTextWatcher)
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultVerticalAnimator()
    }

    private fun hideTel(tel: String): String {
        return String.format("%s****%s", tel.substring(0, 4), tel.substring(7, 11))
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
            if (mTelET.text.toString() == "") {
                mNextStepBtn.setBackgroundResource(R.drawable.sign_up_btn_shape)
                flag1 = false
                mNextStepBtn.isClickable = false
            }
            if (flag1!! && flag2!!) {
                mNextStepBtn.setBackgroundResource(R.drawable.sign_up_btn_shape_dark)
                mNextStepBtn.isClickable = true
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
            if (mVcodeET.text.toString() == "") {
                mNextStepBtn.setBackgroundResource(R.drawable.sign_up_btn_shape)
                flag2 = false
                mNextStepBtn.isClickable = false
            }

            if (flag1!! && flag2!!) {
                mNextStepBtn.setBackgroundResource(R.drawable.sign_up_btn_shape_dark)
                mNextStepBtn.isClickable = true
            }
        }
    }
}