package com.android.lixiang.liangwei.ui.fragment.area2

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.base.utils.view.DimenUtil
import com.android.lixiang.base.utils.view.StatusBarUtil
import com.android.lixiang.liangwei.R
import com.android.lixiang.liangwei.presenter.ShowLandPresenter
import com.android.lixiang.liangwei.presenter.data.bean.ListLineBean
import com.android.lixiang.liangwei.presenter.injection.component.DaggershowLandFragmentComponent
import com.android.lixiang.liangwei.presenter.injection.module.ShowLandModule
import com.android.lixiang.liangwei.presenter.view.ShowLandView
import com.android.lixiang.liangwei.ui.fragment.area1.GetCenter
import com.android.lixiang.liangwei.ui.fragment.area1.InfoEditFragment
import com.android.lixiang.liangwei.ui.fragment.area1.InfoFragment
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
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_show_land.*
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.DefaultVerticalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import org.jetbrains.anko.find
import org.jetbrains.anko.support.v4.toast
import java.util.ArrayList

class ShowLandFragment : BaseMvpFragment<ShowLandPresenter>(), View.OnClickListener, ShowLandView,MapboxMap.OnStyleLoadedListener, LocationEngineListener, OnLocationClickListener, OnCameraTrackingChangedListener {
    override fun onLocationChanged(location: Location?) {
        mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location), 12.0))
        locationComponent?.locationEngine?.removeLocationEngineListener(this)
    }

    @SuppressLint("MissingPermission")
    override fun onConnected() {
        locationEngine!!.requestLocationUpdates()
        if (mDialogRL != null)
            mDialogRL.visibility = View.GONE
//        val snackBar = Snackbar.make(view!!, "点击绘制闭合区域", Snackbar.LENGTH_LONG)
//        snackBar.show()
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
    private var pois2: MutableList<LatLng> = ArrayList()
    private var polygon: Polygon? = null
    private var flag = false
    private var mSelectDoneFlag = false
    private var position: Location? = null
    private var ZOOM_LEVEL: Double = 15.0
    private var canBack: Boolean? = false
    //    private var tempPoints: MutableList<LatLng> = ArrayList()
    private var squaresString: MutableList<MutableList<String>> = ArrayList()
    private var squares: MutableList<LatLng> = ArrayList()
    private var counts: Int = 0//重合了几次
    private var coincideList: MutableList<Int> = mutableListOf()
    private var choosenIndex: Int? = -1
    private var numbers: MutableList<String>? = mutableListOf()
    private var squaresLatlng: MutableList<MutableList<LatLng>> = ArrayList()
    private var theIndex: Int = -1
    private var CENTER_LATITUDE: Double? = 0.0
    private var CENTER_LONGITUDE: Double? = 0.0
    private var mMapView: MapView? = null
    private var layerFlag: Int? = -1
    private var locationComponent: LocationComponent? = null
    private var locationEngine: LocationEngine? = null
    private var lastLocation: Location? = null
    private val SAVED_STATE_LOCATION = "saved_state_location"

    private fun initViews(savedInstanceState: Bundle?) {
        StatusBarUtil.setTranslucentForImageViewInFragment(activity, 0, null)
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
//        locationEngine = LocationEngineProvider(activity).obtainBestLocationEngineAvailable()

        mMapView = view!!.findViewById(R.id.mapView)
        mMapView?.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            lastLocation = savedInstanceState.getParcelable(SAVED_STATE_LOCATION);
        }
//        locationEngine!!.activate()
        mMapView!!.getMapAsync { it ->
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
            val uiSettings = mapboxMap!!.uiSettings
            uiSettings.isCompassEnabled = false//隐藏指南针
            uiSettings.isLogoEnabled = false//隐藏logo
            uiSettings.isTiltGesturesEnabled = true//设置是否可以调整地图倾斜角
            uiSettings.isRotateGesturesEnabled = true//设置是否可以旋转地图
            uiSettings.isAttributionEnabled = false//设置是否显示那个提示按钮
            enableLocation(true)

            if (arguments!!.getString("TAG") == "INFOEDIT") {
                preDraw(arguments!!.getString("LOCATION_POINTS"))
                var temp: MutableList<LatLng>? = mutableListOf()
                var tempArray = arguments!!.getString("LOCATION_POINTS").split(",")
                for (i in 0 until tempArray.size step 2) {
                    var latLng = LatLng(tempArray[i].toDouble(), tempArray[i + 1].toDouble())
                    temp!!.add(latLng)
                }
                CENTER_LATITUDE = GetCenter.getCenterPoint(temp).latitude
                CENTER_LONGITUDE = GetCenter.getCenterPoint(temp).longitude
                mMapView!!.getMapAsync {
                    it.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(CENTER_LATITUDE!!, CENTER_LONGITUDE!!), 15.0), null)
                }

            } else {
                mPresenter.listLine()
                mapboxMap.setOnMapClickListener {
                    if (arguments!!.getString("TAG") != "INFOFRAGMENT" && arguments!!.getString("TAG") != "APPROVAL_INFOMATION" && arguments!!.getString("TAG") != "ApprovalInformationEdit") {

                        for (i in 0 until squaresLatlng.size) {
                            if (checkArea(it, squaresLatlng[i])) {
                                ++counts
                                coincideList[i] = -1
                            }
                        }
                        Logger.d(coincideList)
                        if (counts > 1) {
                            Logger.d(counts)
                            val snackBar = Snackbar.make(view!!, "地块间包含重合区域，为区分地块请点击非重合区域", Snackbar.LENGTH_SHORT)
                            snackBar.show()
                            counts = 0
                        } else {
                            for (i in 0 until squaresLatlng.size) {
                                resetColor(squaresLatlng[i])
                            }
                            choosenIndex = coincideList.indexOf(-1)
                            changeColor(squaresLatlng[coincideList.indexOf(-1)])
                            counts = 0
                            coincideList.clear()
                            for (i in 0 until squaresLatlng.size) {
                                coincideList.add(0)
                            }
                        }
                    }
                }
            }

        }
        mMapView!!.onCreate(savedInstanceState)
        Handler().postDelayed({
            canBack = true
        }, 1000)

        mMapToolbarBackBtn.setOnClickListener(this)
        mLocateBtn.setOnClickListener(this)
        mZoomInBtn.setOnClickListener(this)
        mZoomOutBtn.setOnClickListener(this)
        mLayerBtn.setOnClickListener(this)
        mNorthBtn.setOnClickListener(this)
        mCheckInfoBtn.setOnClickListener(this)

    }

    override fun injectComponent() {
        DaggershowLandFragmentComponent.builder().fragmentComponent(fragmentComponent).showLandModule(ShowLandModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun returnListLand(listLineBean: ListLineBean) {
        for (i in 0 until listLineBean.data.lines.size) {
            numbers!!.add(listLineBean.data.lines[i].number)
            squaresString.add(listLineBean.data.lines[i].line.split(",") as MutableList<String>)
            coincideList.add(0)
        }

        for (i in 0 until squaresString.size) {
            val squares: MutableList<LatLng> = ArrayList()
            for (j in 0 until squaresString[i].size step 2)
                squares.add(LatLng(squaresString[i][j].toDouble(), squaresString[i][j + 1].toDouble()))
            squaresLatlng.add(squares)
            Logger.d(squaresLatlng)
        }

        for (i in 0 until squaresLatlng.size) {
            preDrawTest(squaresLatlng[i])
        }

        Logger.d(arguments!!.getString("TAG"))
        if (arguments!!.getString("TAG") == "INFOFRAGMENT") {
            Logger.d(numbers)
            Logger.d(arguments!!.getString("ID"))
            for (i in 0 until numbers!!.size) {
                if (numbers!![i] == arguments!!.getString("ID"))
                    theIndex = i
            }
            Logger.d(theIndex)
            Logger.d(squaresLatlng)
            changeColor(squaresLatlng[theIndex])

            CENTER_LATITUDE = GetCenter.getCenterPoint(squaresLatlng[theIndex]).latitude
            CENTER_LONGITUDE = GetCenter.getCenterPoint(squaresLatlng[theIndex]).longitude
            mMapView!!.getMapAsync {
                it.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(CENTER_LATITUDE!!, CENTER_LONGITUDE!!), 15.0), null)
            }
            mCheckInfoBtn.visibility = View.GONE
        } else if (arguments!!.getString("TAG") == "ApprovalInformationEdit" || arguments!!.getString("TAG") == "APPROVAL_INFOMATION") {
            Logger.d(numbers)
            Logger.d(arguments!!.getString("ID"))
            for (i in 0 until numbers!!.size) {
                if (numbers!![i] == arguments!!.getString("ID"))
                    theIndex = i
            }
            Logger.d(theIndex)
            Logger.d(squaresLatlng)
            changeColor(squaresLatlng[theIndex])

            CENTER_LATITUDE = GetCenter.getCenterPoint(squaresLatlng[theIndex]).latitude
            CENTER_LONGITUDE = GetCenter.getCenterPoint(squaresLatlng[theIndex]).longitude
            mMapView!!.getMapAsync {
                it.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(CENTER_LATITUDE!!, CENTER_LONGITUDE!!), 15.0), null)
            }
            mCheckInfoBtn.visibility = View.GONE
        }
    }

    override fun onClick(p0: View?) {
        when (p0) {
            mMapToolbarBackBtn -> {
                val bundle = Bundle()
                bundle.putString("LOCATION_POINTS", "")
                bundle.putString("LOCATION_CENTER", "")
                setFragmentResult(ISupportFragment.RESULT_OK, bundle)
//                mMapView!!.onDestroy()
                pop()
            }

            mLocateBtn -> {
                mMapView?.getMapAsync {
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
                mapboxMap.animateCamera(CameraUpdateFactory.zoomIn())
            }

            mZoomOutBtn -> {
                --ZOOM_LEVEL
                mapboxMap.animateCamera(CameraUpdateFactory.zoomOut())
            }

            mLayerBtn -> {
                /**
                 * http://202.111.178.10/cgwx/resource/wenchang.json
                http://202.111.178.10/cgwx/resource/wenchang_plotting.json
                 */
                if(layerFlag == -1){
                    mapboxMap!!.setStyleUrl("http://202.111.178.10/cgwx/resource/wenchang.json")
                    layerFlag = 0
                }else if (layerFlag == 0){
                    mapboxMap!!.setStyleUrl("http://202.111.178.10/cgwx/resource/wenchang_plotting.json")
                    layerFlag = 1
                }else if (layerFlag == 1){
                    mapboxMap!!.setStyleUrl("http://bmob-cdn-20607.b0.upaiyun.com/2018/08/27/981883084077745280e89583ff09ee42.json")
                    layerFlag = -1
                }
            }

            mNorthBtn -> {
                mapboxMap.animateCamera(CameraUpdateFactory.bearingTo(360.0))
            }

            mCheckInfoBtn -> {
                val fragment = InfoFragment().newInstance()
                val bundle = Bundle()
                bundle.putString("ID", numbers!![choosenIndex!!])
                fragment.arguments = bundle
                start(fragment)
            }
        }
    }

    fun newInstance(): ShowLandFragment {
        val args = Bundle()
        val fragment = ShowLandFragment()
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


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_show_land, container, false)
    }

    @SuppressLint("MissingPermission")
    override fun onStart() {
        super.onStart()
        Logger.d("onStart")
        mMapView!!.onStart()
//        if (locationEngine != null && locationEngineListener != null) {
//            locationEngine!!.activate()
//            locationEngine!!.requestLocationUpdates()
//            locationEngine!!.addLocationEngineListener(locationEngineListener)
//        }
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

        if (mMapView != null)
            mMapView!!.onResume()
    }

    override fun onPause() {
        super.onPause()
        Logger.d("onPause")

        if (mMapView != null)
            mMapView!!.onPause()
    }

    override fun onStop() {
        super.onStop()
        Logger.d("onStop")

        if (mMapView != null)
            mMapView!!.onStop()
//        if (locationEngine != null && locationEngineListener != null) {
//            locationEngine!!.removeLocationEngineListener(locationEngineListener)
//            locationEngine!!.removeLocationUpdates()
//            locationEngine!!.deactivate()
//        }
    }

    @SuppressLint("MissingPermission")
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView!!.onSaveInstanceState(outState!!);
        if (locationComponent != null) {
            outState.putParcelable(SAVED_STATE_LOCATION, locationComponent!!.getLastKnownLocation());
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        if (mMapView != null)

            mMapView!!.onLowMemory()
    }

    override fun onDestroyView() {
        Logger.d("onDestroy")

        super.onDestroyView()
        if (mMapView != null)
            mMapView!!.onDestroy()
//        if (locationEngineListener != null) {
//            locationEngine!!.removeLocationEngineListener(locationEngineListener)
//        }
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
//
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
//
//                        locationEngine!!.removeLocationEngineListener(this)
//                    }
//                }
//            }
//            //设置监听器
//            locationEngine!!.addLocationEngineListener(locationEngineListener)
//            if (mDialogRL != null)
//                mDialogRL.visibility = View.GONE
//        } else {
//        }
        //添加或移除定位图层
    }

    private fun initArea(mLocationPoints: String?, string: String) {
        if (mLocationPoints != null) {
            preDraw(mLocationPoints)
        }
    }

    private fun preDraw(string: String) {
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

    private fun preDrawTest(tempPoints: MutableList<LatLng>) {
        mSelectDoneFlag = true
        val pts = tempPoints

        /**
         * polygon
         */
        val polygonOption = PolygonOptions()
                .addAll(pts)
                .fillColor(Color.parseColor("#8CEF6677"))
        polygon = mapboxMap.addPolygon(polygonOption) as Polygon

        /**
         * line
         */

        val ooPolyline = PolylineOptions().width(2F)
                .color(Color.parseColor("#EF6677")).addAll(pts)
        mPolyline = mapboxMap.addPolyline(ooPolyline)

        val points = ArrayList<LatLng>()
        var l: LatLng? = null
        l = pts[0]
        points.add(l)

        var l2: LatLng? = null
        l2 = pts[pts.size - 1]
        points.add(l2)

        val ooPolyline2 = PolylineOptions().width(2F)
                .color(Color.parseColor("#EF6677")).addAll(points)
        mPolyline2 = mapboxMap.addPolyline(ooPolyline2)

        pois2 = pts
        flag = true
    }

    private fun changeColor(tempPoints: MutableList<LatLng>) {
//        mSelectDoneFlag = true
//        if (polygon != null)
//            polygon!!.remove()
//        if (mPolyline != null)
//            mPolyline!!.remove()
//        if (mPolyline2 != null)
//            mPolyline2!!.remove()

        val pts = tempPoints

        /**
         * polygon
         */
        val polygonOption = PolygonOptions()
                .addAll(pts)
                .fillColor(Color.parseColor("#8CF8E71C"))
        polygon = mapboxMap.addPolygon(polygonOption) as Polygon

        /**
         * line
         */

        val ooPolyline = PolylineOptions().width(2F)
                .color(Color.parseColor("#F8E71C")).addAll(pts)
        mPolyline = mapboxMap.addPolyline(ooPolyline)

        val points = ArrayList<LatLng>()
        var l: LatLng? = null
        l = pts[0]
        points.add(l)

        var l2: LatLng? = null
        l2 = pts[pts.size - 1]
        points.add(l2)

        val ooPolyline2 = PolylineOptions().width(2F)
                .color(Color.parseColor("#F8E71C")).addAll(points)
        mPolyline2 = mapboxMap.addPolyline(ooPolyline2)

        pois2 = pts
        flag = true
        mCheckInfoBtn.visibility = View.VISIBLE

    }

    private fun resetColor(tempPoints: MutableList<LatLng>) {
        mSelectDoneFlag = true
        if (polygon != null)
            polygon!!.remove()
        if (mPolyline != null)
            mPolyline!!.remove()
        if (mPolyline2 != null)
            mPolyline2!!.remove()

        val pts = tempPoints

        /**
         * polygon
         */
        val polygonOption = PolygonOptions()
                .addAll(pts)
                .fillColor(Color.parseColor("#8CEF6677"))
        polygon = mapboxMap.addPolygon(polygonOption) as Polygon

        /**
         * line
         */

        val ooPolyline = PolylineOptions().width(2F)
                .color(Color.parseColor("#EF6677")).addAll(pts)
        mPolyline = mapboxMap.addPolyline(ooPolyline)

        val points = ArrayList<LatLng>()
        var l: LatLng? = null
        l = pts[0]
        points.add(l)

        var l2: LatLng? = null
        l2 = pts[pts.size - 1]
        points.add(l2)

        val ooPolyline2 = PolylineOptions().width(2F)
                .color(Color.parseColor("#EF6677")).addAll(points)
        mPolyline2 = mapboxMap.addPolyline(ooPolyline2)

        pois2 = pts
        flag = true
        mCheckInfoBtn.visibility = View.GONE

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
//            if (mMapView != null)
//                mMapView!!.onDestroy()
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

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}