package com.android.lixiang.liangwei.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.liangwei.R
import com.android.lixiang.liangwei.ui.fragment.area1.InfoCollectEntryFragment
import com.android.lixiang.liangwei.ui.fragment.user.LoginFragment
import kotlinx.android.synthetic.main.fragment_home.*
import me.yokeyword.fragmentation.SupportFragment
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import android.widget.Toast
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.LocationSource
import com.android.lixiang.liangwei.ui.fragment.area2.InfomationMaintenanceFragment
import com.android.lixiang.liangwei.ui.fragment.area3.SupportSystemsForLawEnforcementFunctionsFragment
import com.android.lixiang.liangwei.ui.fragment.user.RegisterFragment
import com.donkingliang.imageselector.ClipImageActivity
import com.donkingliang.imageselector.utils.ImageSelector
import com.orhanobut.logger.Logger
import java.io.File
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationClient
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.LatLng
import com.android.lixiang.liangwei.ui.NetworkChangeReceiver
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.DefaultNoAnimator
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.text.SimpleDateFormat
import java.util.*


class HomeFragment : SupportFragment(), View.OnClickListener, LocationSource, AMapLocationListener {

    private var isLogedinFlag = false
    private var mListener: LocationSource.OnLocationChangedListener? = null
    private var mLocationClient: AMapLocationClient? = null
    var mLocationOption: AMapLocationClientOption? = null
    private var isFirstLoc = true
    private var mLatlng: String? = null
    private var mAddress: String? = null
    private var letSharedPreferencesOnlyDoOnceFlag: Boolean? = false
    private var isLogin: String? = null
    private var isNetworkConnected: String? = null
    private var intentFilter: IntentFilter? = null
    private var networkChangeReceiver: NetworkChangeReceiver? = null

    override fun deactivate() {
        mListener = null
    }

    override fun activate(p0: LocationSource.OnLocationChangedListener?) {
        mListener = p0
    }

    private fun location() {
        mLocationClient = AMapLocationClient(activity)
        mLocationClient!!.setLocationListener(this)
        mLocationOption = AMapLocationClientOption()
        mLocationOption!!.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
        mLocationOption!!.isNeedAddress = true
        mLocationOption!!.isOnceLocation = false
        mLocationOption!!.isWifiActiveScan = true
        mLocationOption!!.isMockEnable = false
        mLocationOption!!.interval = 2000
        mLocationClient!!.setLocationOption(mLocationOption)
        mLocationClient!!.startLocation()
    }

    override fun onLocationChanged(p0: AMapLocation?) {
        if (p0 != null) {
            if (p0.errorCode == 0) {
                p0.locationType
                p0.latitude
                p0.longitude
                p0.accuracy
                var df = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                var date = Date(p0.time)
                df.format(date)
                p0.address
                p0.country
                p0.province
                p0.city
                p0.district
                p0.street
                p0.streetNum
                p0.cityCode
                p0.adCode

                mAddress = String.format("%s%s%s", p0.province, p0.city, p0.district)

                mLatlng = if (p0.latitude > 0 && p0.longitude > 0) {
                    String.format("东经%s   北纬%s", p0.longitude.toString().substring(0, p0.longitude.toString().indexOf(".") + 3).replace(".", "°") + "'", p0.latitude.toString().substring(0, p0.latitude.toString().indexOf(".") + 3).replace(".", "°") + "'")
                } else if (p0.latitude > 0 && p0.longitude < 0) {
                    String.format("西经%s   北纬%s", p0.longitude.toString().substring(0, p0.longitude.toString().indexOf(".") + 3).replace(".", "°") + "'", p0.latitude.toString().substring(0, p0.latitude.toString().indexOf(".") + 3).replace(".", "°") + "'")
                } else if (p0.latitude < 0 && p0.longitude < 0) {
                    String.format("西经%s   南纬%s", p0.longitude.toString().substring(0, p0.longitude.toString().indexOf(".") + 3).replace(".", "°") + "'", p0.latitude.toString().substring(0, p0.latitude.toString().indexOf(".") + 3).replace(".", "°") + "'")
                } else {
                    String.format("东经%s   南纬%s", p0.longitude.toString().substring(0, p0.longitude.toString().indexOf(".") + 3).replace(".", "°") + "'", p0.latitude.toString().substring(0, p0.latitude.toString().indexOf(".") + 3).replace(".", "°") + "'")
                }

                mLatlngTV.text = mLatlng
                mInfoCollectTitleTV.text = mAddress
                mLoadingRL.visibility = View.GONE
                if (!letSharedPreferencesOnlyDoOnceFlag!!) {
                    val mSharedPreferences: SharedPreferences? = activity!!.getSharedPreferences("ADDRESS", Context.MODE_PRIVATE)
                    val editor = mSharedPreferences!!.edit()
                    editor.putString("latlng", mLatlng)
                    editor.putString("address", mAddress)
                    editor.apply()

                    letSharedPreferencesOnlyDoOnceFlag = true
                }

                if (isFirstLoc) {
                    mListener!!.onLocationChanged(p0)
                    var buffer = StringBuffer()
                    buffer.append(p0.country + ""
                            + p0.province + ""
                            + p0.city + ""
                            + p0.province + ""
                            + p0.district + ""
                            + p0.street + ""
                            + p0.streetNum)
                    Toast.makeText(activity!!, buffer.toString(), Toast.LENGTH_LONG).show()
                    Logger.d(buffer)
                    isFirstLoc = false
                }


            } else {
//                Logger.d("AmapError", "location Error, ErrCode:"
//                        + p0.errorCode + ", errInfo:"
//                        + p0.errorInfo)
//                Toast.makeText(activity!!, "定位失败", Toast.LENGTH_LONG).show()
            }
        }
    }

    /**
     * todo 1登陆 2用户名显示 3分界线
     */


    @RequiresApi(Build.VERSION_CODES.M)
    private fun initViews() {
        handlePermisson()
        location()
        val sp = activity!!.getSharedPreferences("LOGIN", Context.MODE_PRIVATE)
        isLogin = sp.getString("login", "")
        mLogOutTV.setOnClickListener(this)

        checkAccess()
        activity!!.registerReceiver(broadcastReceiver, IntentFilter("NO_ACCESS"))
        activity!!.registerReceiver(broadcastReceiver2, IntentFilter("ACCESS"))
    }

    private var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            isNetworkConnected = "false"
        }
    }

    private var broadcastReceiver2: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            isNetworkConnected = "true"
        }
    }


    override fun onClick(v: View?) {
        when (v) {
            mLogOutTV -> {
                val builder = AlertDialog.Builder(activity!!)
                builder.setTitle("确认要退出登录吗？")
                builder.setPositiveButton("确认") { dialog, _ ->
                    val sp = activity!!.getSharedPreferences("USER", Context.MODE_PRIVATE)
                    sp.edit().clear().apply()
                    mUserNameRL.isClickable = true
                    mNameTextView.text = "登录"
                    mLogOutTV.visibility = View.GONE
                }.setNegativeButton("取消") { dialogInterface, i ->
                    dialogInterface.dismiss()
                }

                builder.create().show()

            }
            mUserNameRL -> {
//                if (!isLogedinFlag) {
//                    start(LoginFragment().newInstance())


                if (isNetworkConnected == "true") {
                    start(RegisterFragment().newInstance())
                } else {
                    val snackBar = Snackbar.make(view!!, "请连接网络后操作", Snackbar.LENGTH_SHORT)
                    snackBar.show()
                }
//                Logger.d(isNetworkConnected)


//                    isLogedinFlag = true
//                } else {
//                    selectAvatar()
//                    isLogedinFlag = false
//                }
            }

            mInfoCollectRL -> {
                if (isNetworkConnected == "true") {
//                    if (isLogin == "1") {
                    start(InfoCollectEntryFragment().newInstance())
//                    } else {
//                        val snackBar = Snackbar.make(view!!, "请登陆后操作", Snackbar.LENGTH_SHORT)
//                        snackBar.show()
//                    }
                } else {
                    val snackBar = Snackbar.make(view!!, "请连接网络后操作", Snackbar.LENGTH_SHORT)
                    snackBar.show()
                }

            }
            mFastInsureRL -> {
                if (isNetworkConnected == "true") {
//                    if (isLogin == "1") {
                    start(InfomationMaintenanceFragment().newInstance())
//                    } else {
//                        val snackBar = Snackbar.make(view!!, "请登陆后操作", Snackbar.LENGTH_SHORT)
//                        snackBar.show()
//                    }
                } else {
                    val snackBar = Snackbar.make(view!!, "请连接网络后操作", Snackbar.LENGTH_SHORT)
                    snackBar.show()
                }
            }

            mAssistSurveyRL -> {
                if (isNetworkConnected == "true") {
//                    if (isLogin == "1") {
                    start(SupportSystemsForLawEnforcementFunctionsFragment().newInstance())
//                    } else {
//                        val snackBar = Snackbar.make(view!!, "请登陆后操作", Snackbar.LENGTH_SHORT)
//                        snackBar.show()
//                    }
                } else {
                    val snackBar = Snackbar.make(view!!, "请连接网络后操作", Snackbar.LENGTH_SHORT)
                    snackBar.show()
                }
            }
        }
    }

    private fun checkAccess() {
        intentFilter = IntentFilter()
        intentFilter!!.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        networkChangeReceiver = NetworkChangeReceiver()
        activity!!.registerReceiver(networkChangeReceiver, intentFilter)
    }

    private fun selectAvatar() {
        val intent = Intent(activity, ClipImageActivity::class.java)
        intent.putExtra(ImageSelector.MAX_SELECT_COUNT, 1)
        intent.putExtra(ImageSelector.IS_SINGLE, true)
        intent.putExtra(ImageSelector.USE_CAMERA, true)
        startActivityForResult(intent, 2)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 2 && data != null) {
            val images = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT)
            Logger.d(images)
            mAvatarImageView.setImageURI(Uri.fromFile(File(images[0])))
        }
    }

    fun newInstance(): HomeFragment {
        val args = Bundle()
        val fragment = HomeFragment()
        fragment.arguments = args
        return fragment
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun handlePermisson() {
        val permission = Manifest.permission.ACCESS_COARSE_LOCATION
        val checkSelfPermission = ActivityCompat.checkSelfPermission(activity!!, permission)
        if (checkSelfPermission == PackageManager.PERMISSION_GRANTED) {
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!, permission)) {
            } else {
                myRequestPermission()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun myRequestPermission() {
        val permissions = arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
        requestPermissions(permissions, 1)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            location()
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViews()

        mUserNameRL.setOnClickListener(this)
        mInfoCollectRL.setOnClickListener(this)
        mFastInsureRL.setOnClickListener(this)
        mAssistSurveyRL.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onSupportVisible() {
        super.onSupportVisible()
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        val sp2 = activity!!.getSharedPreferences("USER", Context.MODE_PRIVATE)
        Logger.d(sp2.getString("username", ""))
        if (sp2.getString("username", "") != "") {
            mNameTextView.text = "HI," + sp2.getString("username", "")
            mLogOutTV.visibility = View.VISIBLE
            mUserNameRL.isClickable = false
        }
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onBackPressedSupport(): Boolean {
        activity!!.unregisterReceiver(networkChangeReceiver);
        return super.onBackPressedSupport()
    }
}
