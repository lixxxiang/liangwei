package com.android.lixiang.liangwei.ui.fragment.area1

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.liangwei.R
import com.blankj.utilcode.util.KeyboardUtils
import com.orhanobut.logger.Logger
import com.smarttop.library.bean.*
import com.smarttop.library.widget.AddressSelector
import com.smarttop.library.widget.OnAddressSelectedListener
import kotlinx.android.synthetic.main.fragment_location_info.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import me.yokeyword.fragmentation.ISupportFragment


class LocationInfoOriginalFragment : SupportFragment(), View.OnClickListener, OnAddressSelectedListener, AddressSelector.OnDialogCloseListener, AddressSelector.onSelectorAreaPositionListener {
    override fun onAddressSelected(province: Province?, city: City?, county: County?, street: Street?) {
        Logger.d("----")
    }

    override fun selectorAreaPosition(provincePosition: Int, cityPosition: Int, countyPosition: Int, streetPosition: Int) {
    }

    private var mLocationInfo: String? = null
    private var flag1: Boolean? = false
    private var flag2: Boolean? = false


    override fun dialogclose() {
    }


    override fun onClick(v: View?) {
        when (v) {
            mLocationConfirmRL -> {
                if(mLocationInfo!= null && mPlantLocationET.text.toString().isNotEmpty()){
                    val bundle = Bundle()
                    bundle.putString("INSURER_ADDRESS", mLocationInfo)
                    bundle.putString("PLANTING_PLACE", mPlantLocationET.text.toString())
                    setFragmentResult(ISupportFragment.RESULT_OK, bundle)
                    KeyboardUtils.hideSoftInput(activity!!)

                    pop()
                }
            }
        }
    }

    override fun onBackPressedSupport(): Boolean {
        val bundle = Bundle()
        bundle.putString("INSURER_ADDRESS", "")
        bundle.putString("PLANTING_PLACE", "")
        setFragmentResult(ISupportFragment.RESULT_OK, bundle)
        return super.onBackPressedSupport()

    }

    private fun initViews() {
        mLocationInfoToolbar.title = "户口住址"
        (activity as AppCompatActivity).setSupportActionBar(mLocationInfoToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mPlantLocationET.addTextChangedListener(mTextWatcher)

        mLocationInfoToolbar.setNavigationOnClickListener {
            KeyboardUtils.hideSoftInput(activity!!)

            pop()
        }
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
            if (s.toString().isNotEmpty()){
                flag2 = true
                editDone(flag1!!, flag2!!)
            }else{
                flag2 = false
                editDone(flag1!!, flag2!!)
            }
        }
    }

    fun newInstance(): LocationInfoOriginalFragment {
        val args = Bundle()
        val fragment = LocationInfoOriginalFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
//        mInfoCollectRL.setOnClickListener(this)
        initLocationPicker()
        mLocationConfirmRL.setOnClickListener(this)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initLocationPicker() {
        val selector = AddressSelector(context)
        selector.onAddressSelectedListener = OnAddressSelectedListener { province, city, county, street ->
            Logger.d(province)
            Logger.d(city)
            Logger.d(county)
            Logger.d(street)
            if (county.name == null)
                county.name = ""
            if (street.name == null)
                street.name = ""
            mLocationInfo = province!!.name + city!!.name + county!!.name + street!!.name
            Logger.d(mLocationInfo)
            if (mLocationInfo != null)
                flag1 = true
            Logger.d(flag1)
            editDone(flag1!!, flag2!!)
        }
        val view = selector.view
//        selector.listView.isVerticalScrollBarEnabled = false
//        selector.setIndicatorBackgroundColor("#D0021B")
//        selector.listView.setOnTouchListener { v, event ->
//            selector.listView.parent.requestDisallowInterceptTouchEvent(true)
//            false
//        }
        mLocationPickerRL.addView(view)
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    private fun editDone(f1: Boolean, f2: Boolean) {
        if (f1 && f2) {
            mLocationConfirmRL.setBackgroundColor(Color.parseColor("#6299FF"))
        } else {
            mLocationConfirmRL.setBackgroundColor(Color.parseColor("#C5C5C5"))
        }
    }
}
