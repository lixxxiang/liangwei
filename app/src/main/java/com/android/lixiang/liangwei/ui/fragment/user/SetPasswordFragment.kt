package com.android.lixiang.liangwei.ui.fragment.user

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.InputType
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
import com.android.lixiang.liangwei.presenter.ResetPasswordPresenter
import com.android.lixiang.liangwei.presenter.data.bean.ResetPasswordBean
import com.android.lixiang.liangwei.presenter.view.ResetPasswordView
import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.KeyboardUtils
import kotlinx.android.synthetic.main.fragment_set_password.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator

class SetPasswordFragment : SupportFragment(), View.OnClickListener {
    private var flag1: Boolean? = false
    private var flag2: Boolean? = false
    private var newPassword: String? = null
    private var confirmPassword: String? = null

    override fun onClick(p0: View?) {
        when (p0) {
            mSetPasswordConfirmBtn -> {
                val temp = mPwdET.text.toString()
                if (isLetterDigit(temp)) {
                    if (!checkMinLength(temp)) {
                        if (!checkMaxLength(temp)) {
                            newPassword = mPwdET.text.toString()
                            confirmPassword = mConfirmPwdET.text.toString()
                            if (newPassword == confirmPassword) {
                                val fragment = SetUserNameFragment().newInstance()
                                val bundle = Bundle()
                                bundle.putString("TEL", arguments!!.getString("TEL"))
                                bundle.putString("PWD", newPassword)
                                fragment.arguments = bundle
                                start(fragment)
//                                start(SetUserNameFragment().newInstance())
                                KeyboardUtils.hideSoftInput(activity)
                            } else
                                Toast.makeText(activity, getString(R.string.pwd_no_match), Toast.LENGTH_SHORT).show()
                        } else
                            Toast.makeText(activity, getString(R.string.pwd_max), Toast.LENGTH_SHORT).show()
                    } else
                        Toast.makeText(activity, getString(R.string.pwd_min), Toast.LENGTH_SHORT).show()
                } else
                    Toast.makeText(activity, getString(R.string.error_pwd_format), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun newInstance(): SetPasswordFragment {
        val args = Bundle()
        val fragment = SetPasswordFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set_password, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        mSetPasswordAppTitle.typeface = Typeface.createFromAsset(activity!!.assets, "fonts/pingfang.ttf")
        mSetPasswordToolbar.title = "设置登录密码"
        (activity as AppCompatActivity).setSupportActionBar(mSetPasswordToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mSetPasswordToolbar.setNavigationOnClickListener {
            KeyboardUtils.hideSoftInput(activity!!)

            pop()
        }
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
        mSetPasswordConfirmBtn.setOnClickListener(this)
        mSetPasswordConfirmBtn.isClickable = false
        mPwdET.addTextChangedListener(mNewTextWatcher)
        mConfirmPwdET.addTextChangedListener(mConfirmTextWatcher)
        mConfirmPwdET.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        mPwdET.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD

        mPwdET.onFocusChangeListener = View.OnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val temp = mPwdET.text.toString()
                if (isLetterDigit(temp)) {
                    if (!checkMinLength(temp)) {
                        if (!checkMaxLength(temp)) {
                            newPassword = mPwdET.text.toString()
                        } else
                            Toast.makeText(activity, getString(R.string.pwd_max), Toast.LENGTH_SHORT).show()
                    } else
                        Toast.makeText(activity, getString(R.string.pwd_min), Toast.LENGTH_SHORT).show()
                } else
                    Toast.makeText(activity, getString(R.string.error_pwd_format), Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    private var mNewTextWatcher: TextWatcher = object : TextWatcher {
        /**
         * 大于8小于20
         */
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
            if (flag1!! && flag2!!) {
                mSetPasswordConfirmBtn.setBackgroundResource(R.drawable.sign_up_btn_shape_dark)
                mSetPasswordConfirmBtn.isClickable = true

            }
            if (mPwdET.text.toString() == "") {
                mSetPasswordConfirmBtn.setBackgroundResource(R.drawable.sign_up_btn_shape)
                flag1 = false
                mSetPasswordConfirmBtn.isClickable = false
            }
        }
    }

    private var mConfirmTextWatcher: TextWatcher = object : TextWatcher {
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

            if (flag1!! && flag2!!) {
                mSetPasswordConfirmBtn.setBackgroundResource(R.drawable.sign_up_btn_shape_dark)
                mSetPasswordConfirmBtn.isClickable = true
            }

            if (mConfirmPwdET.text.toString() == "") {
                mSetPasswordConfirmBtn.setBackgroundResource(R.drawable.sign_up_btn_shape)
                flag2 = false
                mSetPasswordConfirmBtn.isClickable = false
            }
        }
    }

    private fun isLetterDigit(str: String): Boolean {
        var isDigit = false//定义一个boolean值，用来表示是否包含数字
        var isLetter = false//定义一个boolean值，用来表示是否包含字母
        for (i in 0 until str.length) {
            if (Character.isDigit(str[i])) {   //用char包装类中的判断数字的方法判断每一个字符
                isDigit = true
            }
            if (Character.isLetter(str[i])) {  //用char包装类中的判断字母的方法判断每一个字符
                isLetter = true
            }
        }
        val regex = "^[a-zA-Z0-9]+$"
        return isDigit && isLetter && str.matches(regex.toRegex())
    }

    private fun checkMinLength(string: String): Boolean {
        return string.length < 8
    }

    private fun checkMaxLength(string: String): Boolean {
        return string.length > 20
    }
}