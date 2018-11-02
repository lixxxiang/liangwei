package com.android.lixiang.liangwei.ui.fragment.area1

import android.Manifest
import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.base.utils.view.DimenUtil
import com.android.lixiang.base.utils.view.StatusBarUtil
import com.android.lixiang.liangwei.R
import com.android.lixiang.liangwei.ui.NetworkChangeReceiver
import com.blankj.utilcode.util.ImageUtils
import com.mapbox.android.core.location.LocationEngine
import com.mapbox.android.core.location.LocationEngineListener
import com.mapbox.android.core.location.LocationEnginePriority
import com.mapbox.android.core.location.LocationEngineProvider
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.*
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.location.LocationComponent
import com.mapbox.mapboxsdk.location.LocationComponentOptions
import com.mapbox.mapboxsdk.location.OnCameraTrackingChangedListener
import com.mapbox.mapboxsdk.location.OnLocationClickListener
import com.mapbox.mapboxsdk.location.modes.CameraMode
import com.mapbox.mapboxsdk.location.modes.RenderMode
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.activity_mark_land.*
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import org.jetbrains.anko.support.v4.toast
import java.util.ArrayList
import java.util.HashMap

class MarkLandFragment : SupportFragment(), View.OnClickListener, MapboxMap.OnStyleLoadedListener, LocationEngineListener, OnLocationClickListener, OnCameraTrackingChangedListener {
    override fun onLocationChanged(location: Location?) {
        mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location), 12.0))
        locationComponent?.locationEngine?.removeLocationEngineListener(this)
    }

    @SuppressLint("MissingPermission")
    override fun onConnected() {
        locationEngine!!.requestLocationUpdates()
        if (mDialogRL != null)
            mDialogRL.visibility = View.GONE
        val snackBar = Snackbar.make(view!!, "点击绘制闭合区域", Snackbar.LENGTH_LONG)
        snackBar.show()
    }

    override fun onLocationComponentClick() {
    }

    override fun onCameraTrackingChanged(currentMode: Int) {
    }

    override fun onCameraTrackingDismissed() {
    }

    override fun onStyleLoaded(style: String) {
        Logger.d("ddfdddfd")
    }

    private lateinit var mapboxMap: MapboxMap
    //    private var locationEngine: LocationEngine? = null
//    private var locationEngineListener: LocationEngineListener? = null
    private val PERMISSIONS_REQUEST_LOCATION = 100
    private var mPolyline: Polyline? = null
    private var mPolyline2: Polyline? = null
    private val ids = ArrayList<String>()
    private val latlngs = HashMap<String, LatLng>()
    private var marker: Marker? = null
    private var markers: MutableList<Marker> = mutableListOf()
    private var pois2: MutableList<LatLng> = ArrayList()
    private var polygon: Polygon? = null
    private var flag = false
    private var pointCount = 0
    private var f: MarkLandFragment? = null
    private var mSelectDoneFlag = false
    private var area: String? = null
    private var location: MutableList<String> = ArrayList()
    private var LOCATION_POINTS_ARRAY: String? = ""
    private var la: Double = 0.toDouble()
    private var lo: Double = 0.toDouble()
    private var size: Int = 0
    private var TRANSFORM = 0.0015
    private var position: Location? = null
    private var ZOOM_LEVEL: Double = 15.0
    private var TOTAL_DISTANCE: Double = 0.0
    private var TOTAL_DISTANCE_STRING: String? = null
    private var layerFlag: Int? = -1
    private var canBack: Boolean? = false
    private var isNetworkConnected: String? = null
    private var intentFilter: IntentFilter? = null
    private var networkChangeReceiver: NetworkChangeReceiver? = null


    private var mapView: MapView? = null
    private var locationComponent: LocationComponent? = null
    private var locationEngine: LocationEngine? = null
    private var lastLocation: Location? = null
    private val SAVED_STATE_LOCATION = "saved_state_location"


//    @SuppressLint("MissingPermission")
//    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
//        super.onSaveInstanceState(outState, outPersistentState)
//        mapView!!.onSaveInstanceState(outState!!);
//        if (locationComponent != null) {
//            outState.putParcelable(SAVED_STATE_LOCATION, locationComponent!!.getLastKnownLocation());
//        }
//    }

    @SuppressLint("MissingPermission")
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView!!.onSaveInstanceState(outState!!);
        if (locationComponent != null) {
            outState.putParcelable(SAVED_STATE_LOCATION, locationComponent!!.getLastKnownLocation());
        }
    }

    override fun onClick(p0: View?) {
        when (p0) {
            mMapToolbarBackBtn -> {
                val bundle = Bundle()
                bundle.putString("LOCATION_POINTS", "")
                bundle.putString("LOCATION_CENTER", "")
                setFragmentResult(ISupportFragment.RESULT_OK, bundle)
//                mapView!!.onDestroy()
                pop()
            }

            mMapCancelIV -> {
                mMapInfoMaskRL.visibility = View.GONE

            }

            mDoneBtn -> {
                if (isNetworkConnected == "true") {

                    if (!mSelectDoneFlag) {
                        if (LOCATION_POINTS_ARRAY!!.isNotEmpty())
                            LOCATION_POINTS_ARRAY = ""
                        if (location.size != 0)
                            location.clear()
                        for (i in 0 until pois2.size) {
                            location.add(pois2[i].latitude.toString())
                            location.add(pois2[i].longitude.toString())
                        }
                        LOCATION_POINTS_ARRAY = location.toString().substring(1, location.toString().length - 1)
                        Logger.d(LOCATION_POINTS_ARRAY)

                        if (pois2.size != 0) {
                            area = (getArea(pois2).toFloat() * TRANSFORM).toString()
                            area = area!!.substring(0, area!!.indexOf(".") + 3)
                            Logger.d(TOTAL_DISTANCE)
                            getTotalDistance(pois2)

                            TOTAL_DISTANCE_STRING = if (TOTAL_DISTANCE.toString().length - TOTAL_DISTANCE.toString().indexOf(".") > 3)
                                TOTAL_DISTANCE.toString().substring(0, TOTAL_DISTANCE.toString().indexOf(".") + 3)
                            else
                                TOTAL_DISTANCE.toString()
                            if (!area!!.contains("E")) {
                                mDoneBtnTitle.text = "下一步"
                                flag = true
                                var l: LatLng? = null
                                la = 0.0
                                lo = 0.0
                                size = ids.size

                                for (i in 0 until size) {
                                    l = latlngs[ids[i]]
                                    la += l!!.latitude
                                    lo += l.longitude
                                }

                                drawLine2()
                                drawPolygon()
                                mProgressBar.visibility = View.VISIBLE
                                mResetRelativeLayout.visibility = View.GONE
                                mResetBtn.visibility = View.GONE
                                Handler().postDelayed({
                                    mPerimeterTextView.text = TOTAL_DISTANCE_STRING
                                    mAreaTextView.text = area
                                    mProgressBar.visibility = View.GONE
                                    mReset2View.visibility = View.VISIBLE
                                    mReset2Btn.visibility = View.VISIBLE
                                    mReset2RL.visibility = View.VISIBLE
                                }, 100)
                                mSelectDoneFlag = true
                            } else {
                                mResetRL.visibility = View.VISIBLE
                                mDoneRelativeLayout.visibility = View.GONE
                                val snackBar = Snackbar.make(mRootView, "无法识别面积，请重新选择区域", Snackbar.LENGTH_SHORT)
                                snackBar.setAction(getString(R.string.confirm_2)) {
                                    snackBar.dismiss()
                                }
                                snackBar.setActionTextColor(Color.parseColor("#F5A623"))
                                snackBar.show()
                            }
                        } else {
                            val snackBar = Snackbar.make(mRootView, "请选择区域", Snackbar.LENGTH_SHORT)
                            snackBar.setAction(getString(R.string.confirm_2)) { snackBar.dismiss() }
                            snackBar.setActionTextColor(Color.parseColor("#F5A623"))
                            snackBar.show()
                        }
                    } else {
                        mSelectDoneFlag = false
//                        mapView.onStop()
//                        mapView.onDestroy()
                        val fragment = InfoEditFragment().newInstance()
                        val bundle = Bundle()
                        bundle.putString("LOCATION_POINTS", LOCATION_POINTS_ARRAY)
                        bundle.putString("LOCATION_AREA", area)
                        bundle.putString("LOCATION_PERIMETER", TOTAL_DISTANCE_STRING)
                        bundle.putString("TAG", "MARKLANDFRAGMENT")
                        fragment.arguments = bundle
                        start(fragment)

//                    bundle.putString("LOCATION_CENTER", "" + GetCenter.getCenterPoint(pois2).latitude + "," + GetCenter.getCenterPoint(pois2).longitude)
//                    setFragmentResult(ISupportFragment.RESULT_OK, bundle)
//                    pop()

                    }
                } else {
                    val snackBar = Snackbar.make(view!!, "请连接网络后操作", Snackbar.LENGTH_SHORT)
                    snackBar.show()
                }
            }

            mResetBtn -> {
                mSelectDoneFlag = false
                if (polygon != null)
                    polygon!!.remove()
                if (mPolyline2 != null)
                    mPolyline2!!.remove()
                if (mPolyline != null)
                    mPolyline!!.remove()
                for (i in 0 until markers.size) {
                    markers[i].remove()
                }
                latlngs.clear()
                ids.clear()
                flag = false
                area = null
                pois2.clear()
            }

            mReset2Btn -> {
                mSelectDoneFlag = false
                if (polygon != null)
                    polygon!!.remove()
                if (mPolyline != null)
                    mPolyline!!.remove()
                if (mPolyline2 != null)
                    mPolyline2!!.remove()
                for (i in 0 until markers.size) {
                    markers[i].remove()
                }
                latlngs.clear()
                ids.clear()
                flag = false
                area = null
                pois2.clear()

                mResetRelativeLayout.visibility = View.VISIBLE
                mResetBtn.visibility = View.VISIBLE

                mReset2View.visibility = View.GONE
                mReset2Btn.visibility = View.GONE
                mReset2RL.visibility = View.GONE
            }

            mResetRL -> {
                mSelectDoneFlag = false

                if (polygon != null)
                    polygon!!.remove()
                if (mPolyline2 != null)
                    mPolyline2!!.remove()
                mPolyline!!.remove()
                for (i in 0 until markers.size) {
                    markers[i].remove()
                }
                latlngs.clear()
                ids.clear()
                flag = false
                area = null
                pois2.clear()

                mResetRL.visibility = View.GONE
                mDoneRelativeLayout.visibility = View.VISIBLE
            }

            mLocateBtn -> {
//                moveToCurrentLocation()
//                enableLocation(true)
                mapView?.getMapAsync {
                    mapboxMap = it
                    locationEngine = LocationEngineProvider(activity).obtainBestLocationEngineAvailable();
                    locationEngine!!.setPriority(LocationEnginePriority.HIGH_ACCURACY);
                    locationEngine!!.setFastestInterval(1000);
                    locationEngine!!.addLocationEngineListener(this);
                    locationEngine!!.activate();

                    val padding: IntArray
                    if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                        padding = intArrayOf(0, 750, 0, 0)
                    } else {
                        padding = intArrayOf(0, 250, 0, 0)
                    }

                    val options = LocationComponentOptions.builder(activity)
                            .padding(padding)
                            .layerBelow("waterway-label")
                            .build()

                    locationComponent = it.getLocationComponent()
                    locationComponent!!.activateLocationComponent(activity!!, locationEngine, options)
                    locationComponent!!.setLocationComponentEnabled(true)
                    locationComponent!!.addOnLocationClickListener(this)
                    locationComponent!!.addOnCameraTrackingChangedListener(this)
                    locationComponent!!.setCameraMode(CameraMode.TRACKING)
                    locationComponent!!.setRenderMode(RenderMode.COMPASS);
                    locationComponent!!.forceLocationUpdate(lastLocation)


                }
            }

            mZoomInBtn -> {
                ++ZOOM_LEVEL
                mapboxMap!!.animateCamera(CameraUpdateFactory.zoomIn())
            }

            mZoomOutBtn -> {
                --ZOOM_LEVEL
                mapboxMap!!.animateCamera(CameraUpdateFactory.zoomOut())
            }

            mLayerBtn -> {
                if (layerFlag == -1) {
                    mapboxMap!!.setStyleUrl("http://202.111.178.10/cgwx/resource/wenchang.json")
                    layerFlag = 0
                } else if (layerFlag == 0) {
                    mapboxMap!!.setStyleUrl("http://202.111.178.10/cgwx/resource/wenchang_plotting.json")
                    layerFlag = 1
                } else if (layerFlag == 1) {
                    mapboxMap!!.setStyleUrl("http://bmob-cdn-20607.b0.upaiyun.com/2018/08/27/981883084077745280e89583ff09ee42.json")
                    layerFlag = -1
                }
            }

            mNorthBtn -> {
//                var cameraUpdateFactory = CameraUpdateFactory()
//    aMap.moveCamera(cameraUpdateFactory.changeBearing(360));
//                mapboxMap.moveCamera(cameraUpdateFactory.bearingTo(360))
                mapboxMap!!.animateCamera(CameraUpdateFactory.bearingTo(360.0))

            }
        }
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

    fun newInstance(): MarkLandFragment {
        val args = Bundle()
        val fragment = MarkLandFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Mapbox.getInstance(activity!!, "pk.eyJ1IjoibGl4eGlhbmciLCJhIjoiY2psbnRndnJpMWhwMjN3bmRzZHZ6ejIzaCJ9.2PN9SeRacMAAI8dhc3-1ew")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews(savedInstanceState)

    }

    private fun checkAccess() {
        intentFilter = IntentFilter()
        intentFilter!!.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        networkChangeReceiver = NetworkChangeReceiver()
        activity!!.registerReceiver(networkChangeReceiver, intentFilter)
    }

    private fun initViews(savedInstanceState: Bundle?) {
        StatusBarUtil.setTranslucentForImageViewInFragment(activity, 0, null)
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

//        mapView.onStart()

//        mMapToolbar.title = ""
//        (activity as AppCompatActivity).setSupportActionBar(mMapToolbar)
        mapView = view!!.findViewById(R.id.mapView)
        mapView?.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            lastLocation = savedInstanceState.getParcelable(SAVED_STATE_LOCATION);
        }
        mapView?.getMapAsync {
            mapboxMap = it
            locationEngine = LocationEngineProvider(activity).obtainBestLocationEngineAvailable();
            locationEngine!!.setPriority(LocationEnginePriority.HIGH_ACCURACY);
            locationEngine!!.setFastestInterval(1000);
            locationEngine!!.addLocationEngineListener(this);
            locationEngine!!.activate();

            val padding: IntArray
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                padding = intArrayOf(0, 750, 0, 0)
            } else {
                padding = intArrayOf(0, 250, 0, 0)
            }

            val options = LocationComponentOptions.builder(activity)
                    .padding(padding)
                    .layerBelow("waterway-label")
                    .build()

            locationComponent = it.getLocationComponent()
            locationComponent!!.activateLocationComponent(activity!!, locationEngine, options)
            locationComponent!!.setLocationComponentEnabled(true)
            locationComponent!!.addOnLocationClickListener(this)
            locationComponent!!.addOnCameraTrackingChangedListener(this)
            locationComponent!!.setCameraMode(CameraMode.TRACKING)
            locationComponent!!.setRenderMode(RenderMode.COMPASS);
            locationComponent!!.forceLocationUpdate(lastLocation)

            mapboxMap = it
            val uiSettings = mapboxMap!!.uiSettings
            uiSettings.isCompassEnabled = false//隐藏指南针
            uiSettings.isLogoEnabled = false//隐藏logo
            uiSettings.isTiltGesturesEnabled = true//设置是否可以调整地图倾斜角
            uiSettings.isRotateGesturesEnabled = true//设置是否可以旋转地图
            uiSettings.isAttributionEnabled = false//设置是否显示那个提示按钮
            enableLocation(true)



            mapboxMap!!.setOnMapClickListener {
                Logger.d("CHECKAREA" + checkArea(it, pois2))

                pointCount++
                if (pointCount > 2) {
                    mDoneRelativeLayout.visibility = View.VISIBLE
                }


                if (!flag) {
                    addMarker(it.latitude, it.longitude, mapboxMap)
                    drawLine()
                } else {
                    if (checkArea(it, pois2))
                        mMapInfoMaskRL.visibility = View.VISIBLE
                }
            }
        }
        checkAccess()
        activity!!.registerReceiver(broadcastReceiver, IntentFilter("NO_ACCESS"))
        activity!!.registerReceiver(broadcastReceiver2, IntentFilter("ACCESS"))
//        locationEngine = LocationEngineProvider(activity).obtainBestLocationEngineAvailable()
//        locationEngine!!.activate()
        mapView!!.getMapAsync {

        }
//        if (!moveToCurrentLocation()) {
//            ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), PERMISSIONS_REQUEST_LOCATION)
//        }

        mapView!!.onCreate(savedInstanceState)

//        dialogShow()

        Handler().postDelayed({
            canBack = true
        }, 3000)

        mMapToolbarBackBtn.setOnClickListener(this)
        mDoneBtn.setOnClickListener(this)
        mResetBtn.setOnClickListener(this)
        mReset2Btn.setOnClickListener(this)
        mLocateBtn.setOnClickListener(this)
        mZoomInBtn.setOnClickListener(this)
        mZoomOutBtn.setOnClickListener(this)
        mMapCancelIV.setOnClickListener(this)
        mResetRL.setOnClickListener(this)
        mMapCancelIV.setOnClickListener(this)
        mLayerBtn.setOnClickListener(this)
        mNorthBtn.setOnClickListener(this)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_mark_land, container, false)
    }

    @SuppressLint("MissingPermission")
    override fun onStart() {
        super.onStart()
        mapView!!.onStart()
//        if (locationEngine != null && locationEngineListener != null) {
//            locationEngine!!.activate()
//            locationEngine!!.requestLocationUpdates()
//            locationEngine!!.addLocationEngineListener(locationEngineListener)
//        }

        if (locationEngine != null) {
            locationEngine!!.addLocationEngineListener(this);
            if (locationEngine!!.isConnected()) {
                locationEngine!!.requestLocationUpdates();
            } else {
                locationEngine!!.activate();
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSIONS_REQUEST_LOCATION ->
                if (grantResults.isNotEmpty()) {
//                    moveToCurrentLocation()
                } else {
                    toast("您拒绝了定位权限,无法更新到您当前位置")
                }
        }
    }


    override fun onResume() {
        super.onResume()
        Logger.d("onResume")
        mapView!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        Logger.d("onPause")

        mapView!!.onPause()
    }

    override fun onStop() {
        super.onStop()
        Logger.d("onStop")

        mapView!!.onStop()
//        if (locationEngine != null && locationEngineListener != null) {
//            locationEngine!!.removeLocationEngineListener(locationEngineListener)
//            locationEngine!!.removeLocationUpdates()
//            locationEngine!!.deactivate()
//        }

        if (locationEngine != null) {
            locationEngine!!.removeLocationEngineListener(this);
            locationEngine!!.removeLocationUpdates();
        }
    }


    override fun onLowMemory() {
        Logger.d("onLow")
        super.onLowMemory()
        if (mapView != null)

            mapView!!.onLowMemory()
    }

    override fun onDestroyView() {
        Logger.d("onDestroy")
        super.onDestroyView()
        mapView!!.onDestroy()
//        if (locationEngineListener != null) {
//            locationEngine!!.removeLocationEngineListener(locationEngineListener)
//        }
        removeFromSuperview(mapView!!)

        if (locationEngine != null) {
            locationEngine!!.deactivate();
        }
    }

    fun removeFromSuperview(view: View) {
        val parent = view.parent
        if (parent != null && parent is ViewGroup) {
            parent.removeView(view)
        }
    }

    override fun onSupportInvisible() {
        super.onSupportInvisible()
    }


    @SuppressLint("MissingPermission")
    private fun enableLocation(enable: Boolean) {
//        if (enable) {
//            //获取上次定位参数,如果存在先直接使用
//            val lastlocation = locationEngine!!.lastLocation
//            if (lastlocation != null) {
//                mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(lastlocation), 15.0), 1000)
//                mapboxMap!!.addMarker(MarkerOptions()
//                        .position(LatLng(lastlocation))
//                        .icon(IconFactory.getInstance(activity!!).fromBitmap(ImageUtils.scale(BitmapFactory.decodeResource(resources, R.drawable.img_location), DimenUtil().dip2px(activity!!, 18F), DimenUtil().dip2px(activity!!, 24F)))))
//                Logger.d("?????=====?")
//
//                val snackBar = Snackbar.make(view!!, "点击绘制闭合区域", Snackbar.LENGTH_LONG)
//                snackBar.show()
//                println("-------->>>$lastlocation")
//            }
//            locationEngineListener = object : LocationEngineListener {
//                override fun onConnected() {
//                    //连接到定位服务，不需要操作
//                }
//
//                override fun onLocationChanged(location: Location?) {
//                    println("--------<<<<$lastlocation")
//
//                    if (location != null) {
//                        Log.d("TAG", location.latitude.toString())
//                        mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location), 15.0), 1000)
//                        mapboxMap!!.addMarker(MarkerOptions()
//                                .position(LatLng(location))
//                                .icon(IconFactory.getInstance(activity!!).fromBitmap(ImageUtils.scale(BitmapFactory.decodeResource(resources, R.drawable.img_location), DimenUtil().dip2px(activity!!, 18F), DimenUtil().dip2px(activity!!, 24F)))))
//                        Logger.d("??????")
//
//                        val snackBar = Snackbar.make(view!!, "点击绘制闭合区域", Snackbar.LENGTH_LONG)
//                        snackBar.show()
//                        locationEngine!!.removeLocationEngineListener(this)
//                    }
//                }
//            }
//
//
//            //设置监听器
//            locationEngine!!.addLocationEngineListener(locationEngineListener)
//            if (mDialogRL != null)
//                mDialogRL.visibility = View.GONE
//        } else {
//        }
        //添加或移除定位图层
    }

    private fun getArea(pts: List<LatLng>): String {
        var totalArea = 0.0// 初始化总面积
        var LowX = 0.0
        var LowY = 0.0
        var MiddleX = 0.0
        var MiddleY = 0.0
        var HighX = 0.0
        var HighY = 0.0
        var AM = 0.0
        var BM = 0.0
        var CM = 0.0
        var AL = 0.0
        var BL = 0.0
        var CL = 0.0
        var AH = 0.0
        var BH = 0.0
        var CH = 0.0
        var CoefficientL = 0.0
        var CoefficientH = 0.0
        var ALtangent = 0.0
        var BLtangent = 0.0
        var CLtangent = 0.0
        var AHtangent = 0.0
        var BHtangent = 0.0
        var CHtangent = 0.0
        var ANormalLine = 0.0
        var BNormalLine = 0.0
        var CNormalLine = 0.0
        var OrientationValue = 0.0
        var AngleCos = 0.0
        var Sum1 = 0.0
        var Sum2 = 0.0
        var Count2 = 0.0
        var Count1 = 0.0
        var Sum = 0.0
        val Radius = 6378137.0// WGS84椭球半径
        val Count = pts.size
        //最少3个点
        if (Count < 3) {
            return ""
        }
        for (i in 0 until Count) {
            if (i == 0) {
                LowX = pts[Count - 1].longitude * Math.PI / 180
                LowY = pts[Count - 1].latitude * Math.PI / 180
                MiddleX = pts[0].longitude * Math.PI / 180
                MiddleY = pts[0].latitude * Math.PI / 180
                HighX = pts[1].longitude * Math.PI / 180
                HighY = pts[1].latitude * Math.PI / 180
            } else if (i == Count - 1) {
                LowX = pts[Count - 2].longitude * Math.PI / 180
                LowY = pts[Count - 2].latitude * Math.PI / 180
                MiddleX = pts[Count - 1].longitude * Math.PI / 180
                MiddleY = pts[Count - 1].latitude * Math.PI / 180
                HighX = pts[0].longitude * Math.PI / 180
                HighY = pts[0].latitude * Math.PI / 180
            } else {
                LowX = pts[i - 1].longitude * Math.PI / 180
                LowY = pts[i - 1].latitude * Math.PI / 180
                MiddleX = pts[i].longitude * Math.PI / 180
                MiddleY = pts[i].latitude * Math.PI / 180
                HighX = pts[i + 1].longitude * Math.PI / 180
                HighY = pts[i + 1].latitude * Math.PI / 180
            }
            AM = Math.cos(MiddleY) * Math.cos(MiddleX)
            BM = Math.cos(MiddleY) * Math.sin(MiddleX)
            CM = Math.sin(MiddleY)
            AL = Math.cos(LowY) * Math.cos(LowX)
            BL = Math.cos(LowY) * Math.sin(LowX)
            CL = Math.sin(LowY)
            AH = Math.cos(HighY) * Math.cos(HighX)
            BH = Math.cos(HighY) * Math.sin(HighX)
            CH = Math.sin(HighY)
            CoefficientL = (AM * AM + BM * BM + CM * CM) / (AM * AL + BM * BL + CM * CL)
            CoefficientH = (AM * AM + BM * BM + CM * CM) / (AM * AH + BM * BH + CM * CH)
            ALtangent = CoefficientL * AL - AM
            BLtangent = CoefficientL * BL - BM
            CLtangent = CoefficientL * CL - CM
            AHtangent = CoefficientH * AH - AM
            BHtangent = CoefficientH * BH - BM
            CHtangent = CoefficientH * CH - CM
            AngleCos = (AHtangent * ALtangent + BHtangent * BLtangent + CHtangent * CLtangent) / (Math.sqrt(AHtangent * AHtangent + BHtangent * BHtangent

                    + CHtangent * CHtangent) * Math.sqrt(ALtangent * ALtangent + BLtangent * BLtangent + CLtangent * CLtangent))
            AngleCos = Math.acos(AngleCos)
            ANormalLine = BHtangent * CLtangent - CHtangent * BLtangent
            BNormalLine = 0 - (AHtangent * CLtangent - CHtangent * ALtangent)
            CNormalLine = AHtangent * BLtangent - BHtangent * ALtangent
            if (AM != 0.0)
                OrientationValue = ANormalLine / AM
            else if (BM != 0.0)
                OrientationValue = BNormalLine / BM
            else
                OrientationValue = CNormalLine / CM
            if (OrientationValue > 0) {
                Sum1 += AngleCos
                Count1++
            } else {
                Sum2 += AngleCos
                Count2++
            }
        }

        val tempSum1: Double
        val tempSum2: Double
        tempSum1 = Sum1 + (2.0 * Math.PI * Count2 - Sum2)
        tempSum2 = 2.0 * Math.PI * Count1 - Sum1 + Sum2
        if (Sum1 > Sum2) {
            if (tempSum1 - (Count - 2) * Math.PI < 1)
                Sum = tempSum1
            else
                Sum = tempSum2
        } else {
            if (tempSum2 - (Count - 2) * Math.PI < 1)
                Sum = tempSum2
            else
                Sum = tempSum1
        }
        totalArea = (Sum - (Count - 2) * Math.PI) * Radius * Radius

        return if (Math.floor(totalArea) > 0)
            Math.floor(totalArea).toString() // 返回总面积
        else "0.00"
    }

    private fun drawLine() {
        if (mPolyline != null) {
            mPolyline!!.remove()
        }
        val points = ArrayList<LatLng>()
        var l: LatLng? = null
        for (i in ids.indices) {
            l = latlngs[ids[i]]
            points.add(l!!)
        }
        val ooPolyline = PolylineOptions().width(2F)
                .color(Color.parseColor("#EF6677")).addAll(points)
        mPolyline = mapboxMap!!.addPolyline(ooPolyline)
    }

    private fun drawLine2() {
        val points = ArrayList<LatLng>()
        var l: LatLng? = null
        l = latlngs[ids[0]]
        points.add(l!!)

        var l2: LatLng? = null
        l2 = latlngs[ids[ids.size - 1]]
        points.add(l2!!)

        val ooPolyline = PolylineOptions().width(2F)
                .color(Color.parseColor("#EF6677")).addAll(points)
        mPolyline2 = mapboxMap!!.addPolyline(ooPolyline)
    }

    fun drawPolygon() {
        var ll: LatLng? = null
        val pts = ArrayList<LatLng>()
        for (i in ids.indices) {
            val s = ids[i]
            Log.e("aaa", "key= " + s + " and value= " + latlngs[s].toString())
            ll = latlngs[s]
            pts.add(ll!!)
        }
        val polygonOption = PolygonOptions()
                .addAll(pts)
                .fillColor(Color.parseColor("#8CEF6677"))
        polygon = mapboxMap!!.addPolygon(polygonOption) as Polygon
    }

    private fun addMarker(latitude: Double, longitude: Double, mapboxMap: MapboxMap?) {
        val point = LatLng(latitude, longitude)
        val option = MarkerOptions()
                .position(point)
                .icon(IconFactory.getInstance(activity!!).fromResource(R.drawable.ic_point))
        marker = mapboxMap!!.addMarker(option)
        markers.add(marker!!)
        val id = marker!!.id.toString()
        latlngs[id] = LatLng(latitude, longitude)
        pois2.add(LatLng(latitude, longitude))
        ids.add(id)
    }

    private fun initArea(mLocationPoints: String?, string: String) {
        if (mLocationPoints != null) {
            preDraw(mLocationPoints, string)
        }
    }

    private fun preDraw(string: String, string3: String) {
        Logger.d("PREDRAW")
        mDoneRelativeLayout.visibility = View.VISIBLE
        mResetRelativeLayout.visibility = View.GONE
        mResetBtn.visibility = View.GONE
        mAreaTextView.text = arguments!!.getString("LOCATION_AREA")
        mProgressBar.visibility = View.GONE
        mReset2View.visibility = View.VISIBLE
        mReset2Btn.visibility = View.VISIBLE
        mReset2RL.visibility = View.VISIBLE
        mSelectDoneFlag = true


        var locationArray = string.split(",")
        val pts = ArrayList<LatLng>()

        for (i in 0 until locationArray.size step 2) {
            var tempLocation: LatLng? = null
            tempLocation = LatLng(locationArray[i].toDouble(), locationArray[i + 1].toDouble())
            pts.add(tempLocation)
        }

        /**
         * polygon
         */
        val polygonOption = PolygonOptions()
                .addAll(pts)
                .fillColor(Color.parseColor("#8CEF6677"))
        polygon = mapboxMap!!.addPolygon(polygonOption) as Polygon

        /**
         * line
         */

        val ooPolyline = PolylineOptions().width(2F)
                .color(Color.parseColor("#EF6677")).addAll(pts)
        mPolyline = mapboxMap!!.addPolyline(ooPolyline)

        val points = ArrayList<LatLng>()
        var l: LatLng? = null
        l = pts[0]
        points.add(l!!)

        var l2: LatLng? = null
        l2 = pts[pts.size - 1]
        points.add(l2!!)

        val ooPolyline2 = PolylineOptions().width(2F)
                .color(Color.parseColor("#EF6677")).addAll(points)
        mPolyline2 = mapboxMap!!.addPolyline(ooPolyline2)

        pois2 = pts
        flag = true
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onBackPressedSupport(): Boolean {
        if (canBack!!) {
            val bundle = Bundle()
            bundle.putString("LOCATION_POINTS", "")
            bundle.putString("LOCATION_CENTER", "")
            setFragmentResult(ISupportFragment.RESULT_OK, bundle)
//            mapView!!.()
            canBack = false

            return super.onBackPressedSupport()
        } else
            return true
    }

    private fun isPolygonContainsPoint(var0: List<LatLng>?, var1: LatLng?): Boolean {
        if (var0 != null && var0.size != 0 && var1 != null) {
            var var2: Int
            var2 = 0
            while (var2 < var0.size) {
                if (var1.longitude == var0[var2].longitude && var1.latitude == var0[var2].latitude) {
                    return true
                }
                ++var2
            }

            var2 = 0
            val var3 = false
            var var4: LatLng? = null
            var var5: LatLng? = null
            var var6 = 0.0
            val var8 = var0.size

            for (var9 in 0 until var8) {
                var4 = var0[var9]
                var5 = var0[(var9 + 1) % var8]
                if (var4.latitude != var5.latitude && var1.latitude >= Math.min(var4.latitude, var5.latitude) && var1.latitude <= Math.max(var4.latitude, var5.latitude)) {
                    var6 = (var1.latitude - var4.latitude) * (var5.longitude - var4.longitude) / (var5.latitude - var4.latitude) + var4.longitude
                    if (var6 == var1.longitude) {
                        return true
                    }

                    if (var6 < var1.longitude) {
                        ++var2
                    }
                }
            }

            return var2 % 2 == 1
        } else {
            return false
        }
    }

    private fun checkArea(p0: LatLng, pois: MutableList<LatLng>): Boolean {
        return isPolygonContainsPoint(pois, p0)

    }

    fun getDistance(start: LatLng, end: LatLng): Double {

        val lon1 = Math.PI / 180 * start.longitude
        val lon2 = Math.PI / 180 * end.longitude
        val lat1 = Math.PI / 180 * start.latitude
        val lat2 = Math.PI / 180 * end.latitude

        // 地球半径
        val R = 6371.0

        // 两点间距离 km，如果想要米的话，结果*1000就可以了

        return Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1)) * R
    }

    fun getTotalDistance(pts: List<LatLng>): Double {
        TOTAL_DISTANCE = 0.0
        for (i in 0 until pts.size - 1) {
            TOTAL_DISTANCE += getDistance(pts[i], pts[i + 1])
        }
        TOTAL_DISTANCE += getDistance(pts[0], pts[pts.size - 1])
        return TOTAL_DISTANCE
    }
}