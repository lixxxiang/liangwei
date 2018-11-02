package com.android.lixiang.liangwei.ui.fragment.area3

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.lixiang.liangwei.R
import com.android.lixiang.liangwei.ui.adapter.LawEnforcementTrajectoryAdapter
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_law_enforcement_trajectory.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import org.jetbrains.anko.backgroundColor
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class LawEnforcementTrajectoryFragment : SupportFragment(), View.OnClickListener {
    var ca = Calendar.getInstance()
    var mYear = ca.get(Calendar.YEAR)
    var mMonth = ca.get(Calendar.MONTH)
    var mDay = ca.get(Calendar.DAY_OF_MONTH)
    private var mDateAndTime: String? = null
    private var mDateAndTime2: String? = null

    private var mHour: String? = null
    private var mMinute: String? = null
    private var tempString: String? = null
    private var mDetailList: MutableList<String>? = mutableListOf("", "")
    private var mCompareList: MutableList<String>? = mutableListOf("", "")
    private var mCompareDateList: MutableList<String>? = mutableListOf("", "")
    private var mTitleList: MutableList<String>? = mutableListOf("选择起始时间", "选择终止时间")
    private var adapter: LawEnforcementTrajectoryAdapter? = null
    private var flag = false
    override fun onClick(v: View?) {
        when (v) {
            mDoneBtn -> {
                if (flag) {
                    start(LawEnforcementTrajectoryDetailFragment().newInstance())
                } else {
                    Toast.makeText(activity, "time error", Toast.LENGTH_LONG).show()
                }

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_law_enforcement_trajectory, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
    }

    private fun initViews() {
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        mLawEnforcementTrajectoryToolbar.title = "执法轨迹"


        (activity as AppCompatActivity).setSupportActionBar(mLawEnforcementTrajectoryToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        mLawEnforcementTrajectoryToolbar.setNavigationOnClickListener {
            pop()
        }
        adapter = LawEnforcementTrajectoryAdapter(mTitleList, mDetailList, activity)
        mLawEnforcementTrajectoryLV.adapter = adapter
        mLawEnforcementTrajectoryLV.setOnItemClickListener { adapterView, view, position, id ->
            when (position) {
                0 -> {
                    DatePickerDialog(activity, R.style.MyDatePickerDialogTheme, onDateSetListener, mYear, mMonth, mDay).show()
                }
                1 -> {
                    DatePickerDialog(activity, R.style.MyDatePickerDialogTheme, onDateSetListener2, mYear, mMonth, mDay).show()
                }
            }
        }
        mDoneBtn.setOnClickListener(this)
    }

    fun newInstance(): LawEnforcementTrajectoryFragment {
        val args = Bundle()
        val fragment = LawEnforcementTrajectoryFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    private val onDateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        mYear = year
        mMonth = monthOfYear
        mDay = dayOfMonth
        val days: String = if (mMonth + 1 < 10) {
            StringBuffer().append(mYear).append("/").append(mMonth + 1).append("/").append(mDay).toString()
        } else {
            StringBuffer().append(mYear).append("/").append(mMonth + 1).append("/").append(mDay).toString()
        }

        val days2: String = if (mMonth + 1 < 10) {
            if (mDay < 10)
                StringBuffer().append(mYear).append("-").append("0").append(mMonth + 1).append("-").append("0").append(mDay).toString()
            else
                StringBuffer().append(mYear).append("-").append("0").append(mMonth + 1).append("-").append(mDay).toString()
        } else {
            if (mDay < 10)
                StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append("0").append(mDay).toString()
            else
                StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).toString()
        }

        mDateAndTime = "" + days + getWeek(days)
        mDateAndTime2 = "" + days
        TimePickerDialog(activity, R.style.MyDatePickerDialogTheme, onTimeSetListener, 0, 0, true).show()
    }

    private val onTimeSetListener = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
        mHour = if (hourOfDay == 0)
            "00"
        else
            hourOfDay.toString()

        mMinute = when {
            minute == 0 -> "00"
            minute < 10 -> "0" + minute.toString()
            else -> minute.toString()
        }
        mDateAndTime += " $mHour:$mMinute"
        mDateAndTime2 += " $mHour:$mMinute"

        tempString = mDateAndTime!!.substring(2, mDateAndTime!!.length)
        mDetailList!![0] = tempString!!
        mCompareList!![0] = mDateAndTime2!!

        adapter!!.notifyDataSetChanged()
        checkDone()
    }

    private val onDateSetListener2 = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        mYear = year
        mMonth = monthOfYear
        mDay = dayOfMonth
        val days: String = if (mMonth + 1 < 10) {
            StringBuffer().append(mYear).append("/").append(mMonth + 1).append("/").append(mDay).toString()
        } else {
            StringBuffer().append(mYear).append("/").append(mMonth + 1).append("/").append(mDay).toString()
        }

        val days2: String = if (mMonth + 1 < 10) {
            if (mDay < 10)
                StringBuffer().append(mYear).append("-").append("0").append(mMonth + 1).append("-").append("0").append(mDay).toString()
            else
                StringBuffer().append(mYear).append("-").append("0").append(mMonth + 1).append("-").append(mDay).toString()
        } else {
            if (mDay < 10)
                StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append("0").append(mDay).toString()
            else
                StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).toString()
        }
        mDateAndTime = "" + days + getWeek(days)
        mDateAndTime2 = "" + days

        TimePickerDialog(activity, R.style.MyDatePickerDialogTheme, onTimeSetListener2, 0, 0, true).show()
    }

    private val onTimeSetListener2 = TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
        mHour = if (hourOfDay == 0)
            "00"
        else
            hourOfDay.toString()

        mMinute = when {
            minute == 0 -> "00"
            minute < 10 -> "0" + minute.toString()
            else -> minute.toString()
        }
        mDateAndTime += " $mHour:$mMinute"
        mDateAndTime2 += " $mHour:$mMinute"

        tempString = mDateAndTime!!.substring(2, mDateAndTime!!.length)
        mDetailList!![1] = tempString!!
        mCompareList!![1] = mDateAndTime2!!

        adapter!!.notifyDataSetChanged()
        checkDone()
    }

    @SuppressLint("SimpleDateFormat")
    private fun getWeek(time: String): String {
        var Week = ""
        val format = SimpleDateFormat("yyyy/MM/dd")
        val c = Calendar.getInstance()
        try {
            c.time = format.parse(time)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        val wek = c.get(Calendar.DAY_OF_WEEK)

        if (wek == 1) {
            Week += "周日"
        }
        if (wek == 2) {
            Week += "周一"
        }
        if (wek == 3) {
            Week += "周二"
        }
        if (wek == 4) {
            Week += "周三"
        }
        if (wek == 5) {
            Week += "周四"
        }
        if (wek == 6) {
            Week += "周五"
        }
        if (wek == 7) {
            Week += "周六"
        }
        return Week
    }

    private fun checkDone() {
        if (mDetailList!![0] != "" && mDetailList!![1] != "" && compare_date(mCompareList!![0], mCompareList!![1]) == -1) {
            mDoneBtn.backgroundColor = Color.parseColor("#6299FF")
            flag = true
        } else {
            mDoneBtn.backgroundColor = Color.parseColor("#C5C5C5")
            flag = false
        }
    }


    @SuppressLint("SimpleDateFormat")
    private fun compare_date(DATE1: String, DATE2: String): Int {
        if (DATE1 != "" && DATE2 != "") {
            val df = SimpleDateFormat("yyyy/MM/dd hh:mm")
            try {
                val dt1 = df.parse(DATE1)
                val dt2 = df.parse(DATE2)
                Logger.d(String.format("%s %s", dt1.toString(), dt2.toString()))

                return when {
                    dt1.time > dt2.time -> {
                        1
                    }
                    dt1.time < dt2.time -> {
                        -1
                    }
                    else -> 0
                }
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
        }
        return 0
    }
}
