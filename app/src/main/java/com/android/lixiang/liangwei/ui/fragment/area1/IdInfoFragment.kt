package com.android.lixiang.liangwei.ui.fragment.area1

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.liangwei.R
import kotlinx.android.synthetic.main.fragment_id_info.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import io.card.payment.CardIOActivity
import android.content.Intent
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.StringUtils
import com.orhanobut.logger.Logger
import io.card.payment.CreditCard
import me.yokeyword.fragmentation.ISupportFragment
import java.util.regex.Pattern


class IdInfoFragment : SupportFragment(), View.OnClickListener {
    private var mChooseFlag = false
    private var mClickFlag = false
    private var mIdTypeList: MutableList<String> = mutableListOf("身份证", "组织机构代码", "统一社会信用代码", "其他")
    private var index = -1
    private var mIdNumber: String? = ""
    private var isId: Boolean? = false
    private var isOrganizationCertificate: Boolean? = false
    private var isSocial: Boolean? = false

    override fun onClick(v: View?) {
        when (v) {
            mIdConfirmRL -> {
                if (mClickFlag) {
                    val bundle = Bundle()
                    bundle.putString("ID_INFO", "$index $mIdNumber")
                    setFragmentResult(ISupportFragment.RESULT_OK, bundle)
                    KeyboardUtils.hideSoftInput(activity!!)

                    pop()
                }
            }

            mScanIV -> {
                val scanIntent = Intent(activity, CardIOActivity::class.java)
                scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, false) // default: false
                scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false) // default: false
                scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false) // default: false
                scanIntent.putExtra(CardIOActivity.EXTRA_GUIDE_COLOR, Color.RED)
                scanIntent.putExtra(CardIOActivity.EXTRA_HIDE_CARDIO_LOGO, true)
                scanIntent.putExtra(CardIOActivity.ACTIVITY_SERVICE, false)
                scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, true)
                scanIntent.putExtra(CardIOActivity.EXTRA_LANGUAGE_OR_LOCALE, "zh-Hans")
                scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_CONFIRMATION, true)
                scanIntent.putExtra(CardIOActivity.EXTRA_HIDE_CARDIO_LOGO, true)
                scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE, false)
                scanIntent.putExtra(CardIOActivity.EXTRA_USE_PAYPAL_ACTIONBAR_ICON, false)
                scanIntent.putExtra(CardIOActivity.EXTRA_KEEP_APPLICATION_THEME, true)
                scanIntent.putExtra(CardIOActivity.EXTRA_SCAN_INSTRUCTIONS, "1233")
                scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE, false)
                scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE, false)
                startActivityForResult(scanIntent, 3)
            }

            mArea1 -> {
                index = 0
                mArea1.setBackgroundResource(R.drawable.round_relativelayout_blue)
                mArea2.setBackgroundColor(Color.parseColor("#FFFFFF"))
                mArea3.setBackgroundColor(Color.parseColor("#FFFFFF"))
                mArea4.setBackgroundColor(Color.parseColor("#FFFFFF"))
                mChooseFlag = true
                checkEditText()
            }

            mArea2 -> {
                index = 1
                mArea2.setBackgroundResource(R.drawable.round_relativelayout_blue)
                mArea1.setBackgroundColor(Color.parseColor("#FFFFFF"))
                mArea3.setBackgroundColor(Color.parseColor("#FFFFFF"))
                mArea4.setBackgroundColor(Color.parseColor("#FFFFFF"))
                mChooseFlag = true
                checkEditText()
            }

            mArea3 -> {
                index = 2
                mArea3.setBackgroundResource(R.drawable.round_relativelayout_blue)
                mArea1.setBackgroundColor(Color.parseColor("#FFFFFF"))
                mArea2.setBackgroundColor(Color.parseColor("#FFFFFF"))
                mArea4.setBackgroundColor(Color.parseColor("#FFFFFF"))
                mChooseFlag = true
                checkEditText()
            }

            mArea4 -> {
                index = 3
                mArea4.setBackgroundResource(R.drawable.round_relativelayout_blue)
                mArea2.setBackgroundColor(Color.parseColor("#FFFFFF"))
                mArea3.setBackgroundColor(Color.parseColor("#FFFFFF"))
                mArea1.setBackgroundColor(Color.parseColor("#FFFFFF"))
                mChooseFlag = true
                checkEditText()
            }
        }
    }

    private fun checkEditText() {
        if (!mIdET.text.toString().isEmpty()) {
            mIdConfirmRL.setBackgroundColor(Color.parseColor("#6299FF"))
            mClickFlag = true
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 3) {
            var resultDisplayStr: String
            if (data != null && data.hasExtra(CardIOActivity.EXTRA_SCAN_RESULT)) {
                val scanResult = data.getParcelableExtra<CreditCard>(CardIOActivity.EXTRA_SCAN_RESULT)

                // Never log a raw card number. Avoid displaying it, but if necessary use getFormattedCardNumber()
                resultDisplayStr = "Card Number: " + scanResult.redactedCardNumber + "\n"

                // Do something with the raw number, e.g.:
                // myService.setCardNumber( scanResult.cardNumber );

                if (scanResult.isExpiryValid) {
                    resultDisplayStr += "Expiration Date: " + scanResult.expiryMonth + "/" + scanResult.expiryYear + "\n"
                }

                if (scanResult.cvv != null) {
                    // Never log or display a CVV
                    resultDisplayStr += "CVV has " + scanResult.cvv.length + " digits.\n"
                }

                if (scanResult.postalCode != null) {
                    resultDisplayStr += "Postal Code: " + scanResult.postalCode + "\n"
                }
            } else {
                resultDisplayStr = "Scan was canceled."
            }
            // do something with resultDisplayStr, maybe display it in a textView
            Logger.d(resultDisplayStr)
        }
        // else handle other activity results
    }

    /**
     *  scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_EXPIRY, false) // default: false
    scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_CVV, false) // default: false
    scanIntent.putExtra(CardIOActivity.EXTRA_REQUIRE_POSTAL_CODE, false) // default: false
    scanIntent.putExtra(CardIOActivity.EXTRA_GUIDE_COLOR, Color.RED)
    scanIntent.putExtra(CardIOActivity.EXTRA_HIDE_CARDIO_LOGO, true)
    scanIntent.putExtra(CardIOActivity.ACTIVITY_SERVICE,false)
    scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_MANUAL_ENTRY, true)
    scanIntent.putExtra(CardIOActivity.EXTRA_LANGUAGE_OR_LOCALE, "zh-Hans")
    scanIntent.putExtra(CardIOActivity.EXTRA_SUPPRESS_CONFIRMATION, true)
    scanIntent.putExtra(CardIOActivity.EXTRA_HIDE_CARDIO_LOGO, true)
    scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE, false)
    scanIntent.putExtra(CardIOActivity.EXTRA_USE_PAYPAL_ACTIONBAR_ICON, false)
    scanIntent.putExtra(CardIOActivity.EXTRA_KEEP_APPLICATION_THEME, true)
    scanIntent.putExtra(CardIOActivity.EXTRA_SCAN_INSTRUCTIONS, "1233")
    scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE, false)
    scanIntent.putExtra(CardIOActivity.EXTRA_CAPTURED_CARD_IMAGE, false)

     */
    private fun initViews() {
        mIdInfoToolbar.title = "证件信息"
        (activity as AppCompatActivity).setSupportActionBar(mIdInfoToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mIdInfoToolbar.setNavigationOnClickListener {
            KeyboardUtils.hideSoftInput(activity!!)

            pop()
        }
        mIdET.addTextChangedListener(mIdETTextWatcher)
        mIdTypeList.add("")
        Logger.d(arguments!!.getString("ID_INDEX") + arguments!!.getString("ID_NUMBER"))
        if (arguments!!.getString("ID_INDEX") != null && arguments!!.getString("ID_NUMBER") != null) {
            index = arguments!!.getString("ID_INDEX").toInt()
            mIdNumber = arguments!!.getString("ID_NUMBER")
            when (index) {
                0 -> mArea1.setBackgroundResource(R.drawable.round_relativelayout_blue)
                1 -> mArea2.setBackgroundResource(R.drawable.round_relativelayout_blue)
                2 -> mArea3.setBackgroundResource(R.drawable.round_relativelayout_blue)
                3 -> mArea4.setBackgroundResource(R.drawable.round_relativelayout_blue)
            }
            mIdET.setText(mIdNumber)
            mChooseFlag = true
            checkEditText()
        }
    }

    private var mIdETTextWatcher: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                       after: Int) {
        }

        override fun afterTextChanged(s: Editable) {
            if (mChooseFlag && !mIdET.text.toString().isEmpty()) {
                mIdConfirmRL.setBackgroundColor(Color.parseColor("#6299FF"))
                mIdNumber = mIdET.text.toString()
                mClickFlag = true
            } else {
                mIdConfirmRL.setBackgroundColor(Color.parseColor("#C5C5C5"))
                mClickFlag = false
            }
        }
    }

    fun newInstance(): IdInfoFragment {
        val args = Bundle()
        val fragment = IdInfoFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_id_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        mIdConfirmRL.setOnClickListener(this)
        mScanIV.setOnClickListener(this)
        mArea1.setOnClickListener(this)
        mArea2.setOnClickListener(this)
        mArea3.setOnClickListener(this)
        mArea4.setOnClickListener(this)

    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR

    }

    override fun onBackPressedSupport(): Boolean {
        val bundle = Bundle()
        bundle.putString("ID_INFO", "-1 -1")
        setFragmentResult(ISupportFragment.RESULT_OK, bundle)
        return super.onBackPressedSupport()
    }


    private fun isId(id: String): Int {
        val str = ("[1-9]{2}[0-9]{4}(19|20)[0-9]{2}"
                + "((0[1-9]{1})|(1[1-2]{1}))((0[1-9]{1})|([1-2]{1}[0-9]{1}|(3[0-1]{1})))"
                + "[0-9]{3}[0-9x]{1}")
        val pattern = Pattern.compile(str)
        return if (pattern.matcher(id).matches()) 0 else 1
    }


    private fun cheakOrgCode(str: String): Boolean {
        val codeNo = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z")
        val staVal = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35")
        val map = HashMap<String, String>()
        for (i in codeNo.indices) {
            map.put(codeNo[i], staVal[i])
        }
        val wi = intArrayOf(3, 7, 9, 10, 5, 8, 4, 2)
        val pat = Pattern.compile("^[0-9A-Z]{8}-[0-9X]$")
        val matcher = pat.matcher(str)
        if (!matcher.matches()) {
            println("你的表达式非法")
        }
        val all = str.split("-".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val values = all[0].toCharArray()
        var parity = 0
        for (i in values.indices) {
            val `val` = Character.toString(values[i])
            parity += wi[i] * Integer.parseInt(map.get(`val`).toString())
        }
        val cheak = if (11 - parity % 11 == 10) "X" else Integer.toString(11 - parity % 11)
        return cheak == all[1]
    }

    fun isSocial18(license: String): Boolean {
        if (StringUtils.isEmpty(license)) {
            return false
        }
        if (license.length != 18) {
            return false
        }

        val regex = "^([159Y]{1})([1239]{1})([0-9ABCDEFGHJKLMNPQRTUWXY]{6})([0-9ABCDEFGHJKLMNPQRTUWXY]{9})([0-90-9ABCDEFGHJKLMNPQRTUWXY])$"
        if (!license.matches(regex.toRegex())) {
            return false
        }
        val str = "0123456789ABCDEFGHJKLMNPQRTUWXY"
        val ws = intArrayOf(1, 3, 9, 27, 19, 26, 16, 17, 20, 29, 25, 13, 8, 24, 10, 30, 28)
        val codes = arrayOfNulls<String>(2)
        codes[0] = license.substring(0, license.length - 1)
        codes[1] = license.substring(license.length - 1, license.length)
        var sum = 0
        for (i in 0..16) {
            sum += str.indexOf(codes[0]!![i]) * ws[i]
        }
        var c18 = 31 - sum % 31
        if (c18 == 31) {
            c18 = 'Y'.toInt()
        } else if (c18 == 30) {
            c18 = '0'.toInt()
        }
        return str[c18] == codes[1]!!.get(0)
    }


    fun isSocial15(license: String): Boolean {
        if (StringUtils.isEmpty(license)) {
            return false
        }
        if (license.length != 15) {
            return false
        }

        val businesslicensePrex14 = license.substring(0, 14)// 获取营业执照注册号前14位数字用来计算校验码
        val businesslicense15 = license.substring(14, license.length)// 获取营业执照号的校验码
        val chars = businesslicensePrex14.toCharArray()
        val ints = IntArray(chars.size)
        for (i in chars.indices) {
            ints[i] = Integer.parseInt(chars[i].toString())
        }
        getCheckCode(ints)
        return businesslicense15 == "" + getCheckCode(ints) + ""
    }

    private fun getCheckCode(ints: IntArray?): Int {
        if (null != ints && ints.size > 1) {
            var ti = 0
            var si = 0// pi|11+ti
            var cj = 0// （si||10==0？10：si||10）*2
            var pj = 10// pj=cj|11==0?10:cj|11
            for (i in ints.indices) {
                ti = ints[i]
                pj = if (cj % 11 == 0) 10 else cj % 11
                si = pj + ti
                cj = (if (0 == si % 10) 10 else si % 10) * 2
                if (i == ints.size - 1) {
                    pj = if (cj % 11 == 0) 10 else cj % 11
                    return if (pj == 1) 1 else 11 - pj
                }
            }
        }// end if
        return -1
    }
}
