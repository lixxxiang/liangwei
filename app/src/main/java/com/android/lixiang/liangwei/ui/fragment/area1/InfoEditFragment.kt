package com.android.lixiang.liangwei.ui.fragment.area1

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.*
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.android.lixiang.base.net.RestClient
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.base.utils.view.StatusBarUtil
import com.android.lixiang.liangwei.GlideSimpleLoader
import com.android.lixiang.liangwei.R
import com.android.lixiang.liangwei.presenter.InfoEditPresenter
import com.android.lixiang.liangwei.presenter.data.bean.*
import com.android.lixiang.liangwei.presenter.injection.component.DaggerInfoEditFragmentComponent
import com.android.lixiang.liangwei.presenter.injection.module.InfoEditModule
import com.android.lixiang.liangwei.presenter.view.InfoEditView
import com.android.lixiang.liangwei.ui.NetworkChangeReceiver
import com.android.lixiang.liangwei.ui.activity.ShowImageActivity
import com.android.lixiang.liangwei.ui.adapter.IllegalArchitecturalContoursAdapter
import com.android.lixiang.liangwei.ui.fragment.area2.MaterialTypeFragment
import com.android.lixiang.liangwei.ui.fragment.area2.ShowLandFragment
import com.android.lixiang.liangwei.ui.fragment.area2.bak.InsureCountFragment
import com.android.lixiang.liangwei.ui.fragment.area2.SuccessAcceptFragment
import com.android.lixiang.liangwei.ui.fragment.area3.bak.FillSurveyInfoFragment
import com.blankj.utilcode.util.KeyboardUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.donkingliang.imageselector.ImageSelectorActivity
import com.donkingliang.imageselector.utils.ImageSelector
import com.example.emall_core.net.callback.IError
import com.example.emall_core.net.callback.IFailure
import com.example.emall_core.net.callback.ISuccess
import com.github.ielse.imagewatcher.ImageWatcherHelper
import com.google.gson.Gson
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_all_info.*
import kotlinx.android.synthetic.main.fragment_info_edit.*
import kotlinx.android.synthetic.main.show_image_fragment.*
import me.yokeyword.fragmentation.ISupportFragment
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class InfoEditFragment : BaseMvpFragment<InfoEditPresenter>(), View.OnClickListener, InfoEditView {

    private val REQUEST_CODE = 0x00000011
    private val OPTIONS = RequestOptions().centerCrop()
    private var IMAGE2_FLAG = false
    private var IMAGE3_FLAG = false
    private var IMAGE4_FLAG = false
    private var mImagesArray: MutableList<String>? = mutableListOf()
    private var mImages: Array<String>? = arrayOf()
    private var mSharedPreferences: SharedPreferences? = null
    private var MAX_IMAGE = 4
    private var ca = Calendar.getInstance()
    private var mYear = ca.get(Calendar.YEAR)
    private var mMonth = ca.get(Calendar.MONTH)
    private var mDay = ca.get(Calendar.DAY_OF_MONTH)
    private var mIdIndex = -1
    private var mIdNumber: String? = ""
    private var mIdTypeList: MutableList<String> = mutableListOf("身份证", "组织机构代码", "统一社会信用代码", "其它")
    private var mMaterialList: MutableList<String> = mutableListOf("土", "砖", "砖混", "其它")
    private var mYesOrNoList: MutableList<String> = mutableListOf("是", "否")
    private var identityType: String = ""
    private var idNumber: String = ""
    private var insurerAddress: String = ""
    private var plantingPlace: String = ""
    private var mDate: String = ""
    private var mMaterial: String = "0"
    private var mDangerousIndex: Int? = 0
    private var mLiangWeiIndex: Int? = 0
    private var name: String? = null
    private var phone: String? = null
    private var work: String? = null
    private var iid: String? = null
    private var address: String? = null
    private var area: String? = null
    private var perimeter: String? = null
    private var flag: String? = null
    private var floor: String? = null
    private var type: String? = null
    private var time: String? = null
    private var isdanger: String? = null
    private var isliangwei: String? = null
    private var info: String? = null
    private var url: String? = null
    private var status: String? = null
    private var line: String? = null
    private var PAGE_FROM: String? = null
    private var parts: MutableList<MultipartBody.Part>? = mutableListOf()
    private var mFileArray: Array<File>? = null
    private var LOCATION_POINTS: String? = null
    private var ID: String? = null
    private var AREA: String? = null
    private var PERIMETER: String? = null
    private var imageCounts: Int? = 0
    private var URL: String? = null

    private var iwHelper: ImageWatcherHelper? = null
    private val pictureList = ArrayList<Uri>()
    private var mapping = SparseArray<ImageView>()

    private var isNetworkConnected: String? = null
    private var intentFilter: IntentFilter? = null
    private var networkChangeReceiver: NetworkChangeReceiver? = null

    override fun returnUpload(uploadBean: UploadBean) {
        if (uploadBean.message == "success") {
            if (mTelET.text.toString().isEmpty() ||
                    mNameET.text.toString().isEmpty() ||
                    mLayerET.text.toString().isEmpty() ||
                    mFourET.text.toString().isEmpty() ||
                    mWorkET.text.toString().isEmpty() ||
                    mIdInfoTV.text.toString().isEmpty() ||
                    mRegisterTV.text.toString().isEmpty() ||
                    mMaterialTypeTV.text.toString().isEmpty() ||
                    mDateTV.text.toString().isEmpty() ||
                    mDangerousTV.text.toString().isEmpty() ||
                    mLiangweiTV.text.toString().isEmpty()
            ) {
                Toast.makeText(activity!!, "未录入全部信息", Toast.LENGTH_SHORT).show()
            } else {
                name = mNameET.text.toString()
                phone = mTelET.text.toString()
                work = mWorkET.text.toString()
                iid = mIdInfoTV.text.toString()
                address = mRegisterTV.text.toString()
                area = arguments!!.getString("LOCATION_AREA")
                perimeter = arguments!!.getString("LOCATION_PERIMETER")
                flag = "1"
                floor = mLayerET.text.toString()
                type = mMaterial
                time = mDateTV.text.toString()
                isdanger = mDangerousIndex.toString()
                isliangwei = mLiangWeiIndex.toString()
                url = uploadBean.data.url
                line = arguments!!.getString("LOCATION_POINTS")
                status = "0"
                info = mFourET.text.toString()
                Logger.d(name!!,
                        phone!!,
                        work!!,
                        iid!!,
                        address!!,
                        area!!,
                        perimeter!!,
                        flag!!,
                        floor!!,
                        type!!,
                        time!!,
                        isdanger!!,
                        isliangwei!!,
                        info!!,
                        url!!,
                        status!!,
                        line!!)
                mPresenter.illegalInfo(name!!,
                        phone!!,
                        work!!,
                        iid!!,
                        address!!,
                        area!!,
                        perimeter!!,
                        flag!!,
                        floor!!,
                        type!!,
                        time!!,
                        isdanger!!,
                        isliangwei!!,
                        info!!,
                        url!!,
                        status!!,
                        line!!)
            }
        }
    }

    override fun returnUpdateIllegalInfo(updateIllegalInfoBean: UpdateIllegalInfoBean) {
        if (updateIllegalInfoBean.message == "success") {
            val snackBar = Snackbar.make(view!!, "保存成功", Snackbar.LENGTH_SHORT)
            snackBar.show()
        }
    }

    override fun returnSelectIllegal(selectIllegalBean: SelectIllegalBean) {
        mAcceptInsuranceToolbar.title = String.format("信息ID：%s", selectIllegalBean.data.info.number)
        ID = selectIllegalBean.data.info.number
        AREA = selectIllegalBean.data.info.area
        PERIMETER = selectIllegalBean.data.info.perimeter
        Logger.d(selectIllegalBean.data.info.number)
        mNameET.setText(selectIllegalBean.data.info.name)
        LOCATION_POINTS = selectIllegalBean.data.info.line
        mTelET.setText(selectIllegalBean.data.info.phone)
        mWorkET.setText(selectIllegalBean.data.info.work)
        mIdInfoTV.text = selectIllegalBean.data.info.iid
        mRegisterTV.text = selectIllegalBean.data.info.address
        mAreaTV.text = selectIllegalBean.data.info.area
        mPerimeterTV.text = selectIllegalBean.data.info.perimeter
        Logger.d(selectIllegalBean.data.info.floor)
        mLayerET.setText(selectIllegalBean.data.info.floor.toString())
        mMaterialTypeTV.text = mMaterialList[selectIllegalBean.data.info.type]
        mDateTV.text = stampToDate(selectIllegalBean.data.info.time.toString())
        mDangerousTV.text = mYesOrNoList[selectIllegalBean.data.info.isdanger]
        mLiangweiTV.text = mYesOrNoList[selectIllegalBean.data.info.isliangwei]
        mFourET.setText(selectIllegalBean.data.info.info)
        URL = selectIllegalBean.data.info.url
        var size = selectIllegalBean.data.info.url.split(",").size
        val urls = selectIllegalBean.data.info.url.split(",")
        when (size) {
            1 -> {
                mAIImage1.visibility = View.VISIBLE
                mAIImage2.visibility = View.GONE
                mAIImage3.visibility = View.GONE
                mAIImage4.visibility = View.GONE
                Glide.with(this).load("http://59.110.162.194:8085/2weiPic/" + urls!![0]).apply(OPTIONS).into(mAIImage1)
                pictureList.add(Uri.parse("http://59.110.162.194:8085/2weiPic/" + urls!![0]))
                mapping.put(0, view!!.findViewById<View>(R.id.mAIImage1) as ImageView)
            }
            2 -> {
                mAIImage1.visibility = View.VISIBLE
                Glide.with(this).load("http://59.110.162.194:8085/2weiPic/" + urls!![0]).apply(OPTIONS).into(mAIImage1)
                mAIImage2.visibility = View.VISIBLE
                mAIImage3.visibility = View.GONE
                mAIImage4.visibility = View.GONE
                Glide.with(this).load("http://59.110.162.194:8085/2weiPic/" + urls!![1]).apply(OPTIONS).into(mAIImage2)
                pictureList.add(Uri.parse("http://59.110.162.194:8085/2weiPic/" + urls!![0]))
                pictureList.add(Uri.parse("http://59.110.162.194:8085/2weiPic/" + urls!![1]))
                mapping.put(0, view!!.findViewById<View>(R.id.mAIImage1) as ImageView)
                mapping.put(1, view!!.findViewById<View>(R.id.mAIImage2) as ImageView)
            }
            3 -> {
                mAIImage1.visibility = View.VISIBLE
                Glide.with(this).load("http://59.110.162.194:8085/2weiPic/" + urls!![0]).apply(OPTIONS).into(mAIImage1)
                mAIImage2.visibility = View.VISIBLE
                Glide.with(this).load("http://59.110.162.194:8085/2weiPic/" + urls!![1]).apply(OPTIONS).into(mAIImage2)
                mAIImage3.visibility = View.VISIBLE
                mAIImage4.visibility = View.GONE

                Glide.with(this).load("http://59.110.162.194:8085/2weiPic/" + urls!![2]).apply(OPTIONS).into(mAIImage3)
                pictureList.add(Uri.parse("http://59.110.162.194:8085/2weiPic/" + urls!![0]))
                pictureList.add(Uri.parse("http://59.110.162.194:8085/2weiPic/" + urls!![1]))
                pictureList.add(Uri.parse("http://59.110.162.194:8085/2weiPic/" + urls!![2]))
                mapping.put(0, view!!.findViewById<View>(R.id.mAIImage1) as ImageView)
                mapping.put(1, view!!.findViewById<View>(R.id.mAIImage2) as ImageView)
                mapping.put(2, view!!.findViewById<View>(R.id.mAIImage3) as ImageView)


            }
            4 -> {
                mAIImage1.visibility = View.VISIBLE
                Glide.with(this).load("http://59.110.162.194:8085/2weiPic/" + urls!![0]).apply(OPTIONS).into(mAIImage1)
                mAIImage2.visibility = View.VISIBLE
                Glide.with(this).load("http://59.110.162.194:8085/2weiPic/" + urls!![1]).apply(OPTIONS).into(mAIImage2)
                mAIImage3.visibility = View.VISIBLE
                Glide.with(this).load("http://59.110.162.194:8085/2weiPic/" + urls!![2]).apply(OPTIONS).into(mAIImage3)
                mAIImage4.visibility = View.VISIBLE
                Glide.with(this).load("http://59.110.162.194:8085/2weiPic/" + urls!![3]).apply(OPTIONS).into(mAIImage4)
                pictureList.add(Uri.parse("http://59.110.162.194:8085/2weiPic/" + urls!![0]))
                pictureList.add(Uri.parse("http://59.110.162.194:8085/2weiPic/" + urls!![1]))
                pictureList.add(Uri.parse("http://59.110.162.194:8085/2weiPic/" + urls!![2]))
                pictureList.add(Uri.parse("http://59.110.162.194:8085/2weiPic/" + urls!![3]))
                mapping.put(0, view!!.findViewById<View>(R.id.mAIImage1) as ImageView)
                mapping.put(1, view!!.findViewById<View>(R.id.mAIImage2) as ImageView)
                mapping.put(2, view!!.findViewById<View>(R.id.mAIImage3) as ImageView)
                mapping.put(3, view!!.findViewById<View>(R.id.mAIImage4) as ImageView)
            }
        }
    }

    private fun timeFormat(s: String): String? {
        var sdf = SimpleDateFormat("yyyy-MM-dd")
        var sd = sdf.format(Date(s.toLong()))
        return sd
    }


    override fun injectComponent() {
        DaggerInfoEditFragmentComponent.builder().fragmentComponent(fragmentComponent).infoEditModule(InfoEditModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun test(string: String) {
    }

    override fun returnIllegalInfo(illegalInfoBean: IllegalInfoBean) {
        Logger.d(illegalInfoBean.data)
        if (illegalInfoBean.message == "success") {
            val fragment: SuccessCommitFragment = SuccessCommitFragment().newInstance()
            val bundle = Bundle()
            bundle.putString("ID", illegalInfoBean.data.number)
            fragment.arguments = bundle
            start(fragment)
        }
    }

    fun checkImage(): Boolean {
        return imageCounts != 0
    }

    override fun onClick(v: View?) {
        when (v) {
//            mAcceptInsuranceIdInfoRL -> {
//                start(IdInfoFragment().newInstance())
//            }

            mAcceptInsuranceAddressRl -> {
//                start(IdInfoFragment().newInstance())
                if (isNetworkConnected == "true") {
                    KeyboardUtils.hideSoftInput(activity)
                    val fragment: IdInfoFragment = IdInfoFragment().newInstance()
                    if (mIdIndex != -1 && !mIdNumber!!.isEmpty()) {
                        val bundle = Bundle()
                        bundle.putString("ID_INDEX", mIdIndex.toString())
                        bundle.putString("ID_NUMBER", mIdNumber)
                        fragment.arguments = bundle
                    }
                    startForResult(fragment, 0x001)
                } else {
                    val snackBar = Snackbar.make(view!!, "请连接网络后操作", Snackbar.LENGTH_SHORT)
                    snackBar.show()
                }

            }
            mBankRL -> {
                KeyboardUtils.hideSoftInput(activity)
//                startForResult(LocationInfoFragment().newInstance(), 0x003)
                startForResult(LocationInfoOriginalFragment().newInstance(), 0x003)

            }
//            mInsureCountRL -> {
//                start(InsureCountFragment().newInstance())
//            }

            mCommitRL -> {
                if (isNetworkConnected == "true") {

                    if (mCommitBtn.text == "提交信息") {
                        if (mTelET.text.toString().isEmpty() ||
                                mNameET.text.toString().isEmpty() ||
                                mLayerET.text.toString().isEmpty() ||
                                mFourET.text.toString().isEmpty() ||
                                mWorkET.text.toString().isEmpty() ||
                                mIdInfoTV.text.toString().isEmpty() ||
                                mRegisterTV.text.toString().isEmpty() ||
                                mMaterialTypeTV.text.toString().isEmpty() ||
                                mDateTV.text.toString().isEmpty() ||
                                mDangerousTV.text.toString().isEmpty() ||
                                mLiangweiTV.text.toString().isEmpty() || !checkImage()
                        ) {
                            Toast.makeText(activity!!, "未录入全部信息", Toast.LENGTH_SHORT).show()
                        } else {
                            uploadImages()
                            mCommitBtn.visibility = View.GONE
                            mProgressbar.visibility = View.VISIBLE
                            mCommitRL.isClickable = false
                        }

                    } else if (mCommitBtn.text == "保存修改") {
                        if (mTelET.text.toString().isEmpty() ||
                                mNameET.text.toString().isEmpty() ||
                                mLayerET.text.toString().isEmpty() ||
                                mFourET.text.toString().isEmpty() ||
                                mWorkET.text.toString().isEmpty() ||
                                mIdInfoTV.text.toString().isEmpty() ||
                                mRegisterTV.text.toString().isEmpty() ||
                                mMaterialTypeTV.text.toString().isEmpty() ||
                                mDateTV.text.toString().isEmpty() ||
                                mDangerousTV.text.toString().isEmpty() ||
                                mLiangweiTV.text.toString().isEmpty()
                        ) {
                            Toast.makeText(activity!!, "未录入全部信息", Toast.LENGTH_SHORT).show()
                        } else {
                            name = mNameET.text.toString()
                            phone = mTelET.text.toString()
                            work = mWorkET.text.toString()
                            iid = mIdInfoTV.text.toString()
                            address = mRegisterTV.text.toString()
                            area = AREA
                            perimeter = PERIMETER
                            flag = "1"
                            floor = mLayerET.text.toString()
                            type = mMaterial
                            time = mDateTV.text.toString()
                            isdanger = mDangerousIndex.toString()
                            isliangwei = mLiangWeiIndex.toString()
                            url = URL
                            line = LOCATION_POINTS
                            status = "0"
                            info = mFourET.text.toString()
                            mPresenter.updateIllegalInfo(name!!,
                                    phone!!,
                                    work!!,
                                    iid!!,
                                    address!!,
                                    area!!,
                                    perimeter!!,
                                    flag!!,
                                    floor!!,
                                    type!!,
                                    time!!,
                                    isdanger!!,
                                    isliangwei!!,
                                    info!!,
                                    url!!,
                                    status!!,
                                    line!!, ID!!)
                        }
                    }
                } else {
                    val snackBar = Snackbar.make(view!!, "请连接网络后操作", Snackbar.LENGTH_SHORT)
                    snackBar.show()
                }
            }
            mSpeciesNameRL -> {
                KeyboardUtils.hideSoftInput(activity!!)
                val fragment: MaterialTypeFragment = MaterialTypeFragment().newInstance()
                val bundle = Bundle()
                bundle.putString("ID_INDEX", mMaterial)
                fragment.arguments = bundle
                startForResult(fragment, 0x004)
            }

            mAIUploadImagesIV -> {
                selectImage()
            }
            mAIImage1 -> {
                if (arguments!!.getString("TAG") == "ILLEGALARCHITECTURALCONTOURSEDITFRAGMENT" || arguments!!.getString("TAG") == "RESULTINFOEDIT") {
                    if (iwHelper != null) {
                        Logger.d(pictureList)
                        iwHelper!!.show(mAIImage1, mapping, pictureList)
                    }
                } else {
                    startFullScreenActivity(0, mAIImage1)
                }
                Logger.d(mAIImage1)
            }
            mAIImage2 -> {
                if (arguments!!.getString("TAG") == "ILLEGALARCHITECTURALCONTOURSEDITFRAGMENT" || arguments!!.getString("TAG") == "RESULTINFOEDIT") {
                    if (iwHelper != null) {
                        Logger.d(pictureList)
                        iwHelper!!.show(mAIImage2, mapping, pictureList)
                    }
                } else {
                    if (!IMAGE2_FLAG) {
                        selectImage()
                    } else {
                        startFullScreenActivity(1, mAIImage2)
                    }
                }

            }
            mAIImage3 -> {
                if (arguments!!.getString("TAG") == "ILLEGALARCHITECTURALCONTOURSEDITFRAGMENT" || arguments!!.getString("TAG") == "RESULTINFOEDIT") {
                    if (iwHelper != null) {
                        Logger.d(pictureList)
                        iwHelper!!.show(mAIImage3, mapping, pictureList)
                    }
                } else {
                    if (!IMAGE3_FLAG) {
                        selectImage()
                    } else {
                        startFullScreenActivity(2, mAIImage3)
                    }
                }
            }

            mAIImage4 -> {
                if (arguments!!.getString("TAG") == "ILLEGALARCHITECTURALCONTOURSEDITFRAGMENT" || arguments!!.getString("TAG") == "RESULTINFOEDIT") {
                    if (iwHelper != null) {
                        Logger.d(pictureList)
                        iwHelper!!.show(mAIImage4, mapping, pictureList)
                    }
                } else {
                    if (!IMAGE4_FLAG) {
                        selectImage()
                    } else {
                        startFullScreenActivity(3, mAIImage4)
                    }
                }
            }

            mBuildingRL -> {
                KeyboardUtils.hideSoftInput(activity!!)

                //                start(MarkLandFragment().newInstance())
                val fragment: ShowLandFragment = ShowLandFragment().newInstance()
                val bundle = Bundle()
                Logger.d(arguments!!.getString("LOCATION_POINTS"))

                if (arguments!!.getString("LOCATION_POINTS") == null) {
                    Logger.d(LOCATION_POINTS)
                    bundle.putString("LOCATION_POINTS", LOCATION_POINTS)
                } else
                    bundle.putString("LOCATION_POINTS", arguments!!.getString("LOCATION_POINTS"))
                bundle.putString("TAG", "INFOEDIT")
                fragment.arguments = bundle
                startForResult(fragment, 0x004)
            }

            mPlantLocationRL -> {
                DatePickerDialog(activity!!, R.style.MyDatePickerDialogTheme, onDateSetListener, mYear, mMonth, mDay).show()
            }

            mDangerRL -> {
                setDialogBroadcast("是否危房", mDangerousIndex!!)
            }

            mliangwei -> {
                setDialogBroadcast2("是否两违", mLiangWeiIndex!!)
            }
        }
    }

    private fun uploadImages() {
        var size = imageCounts
        Logger.d(size)
        if (size == 1) {
            var requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), mFileArray!![0])
            var body = MultipartBody.Part.createFormData("files", mFileArray!![0].name, requestFile)
            parts!!.add(body)
        }
        if (size == 2) {
            var requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), mFileArray!![0])
            var body = MultipartBody.Part.createFormData("files", mFileArray!![0].name, requestFile)
            var requestFile2 = RequestBody.create(MediaType.parse("multipart/form-data"), mFileArray!![1])
            var body2 = MultipartBody.Part.createFormData("files", mFileArray!![1].name, requestFile2)
            parts!!.add(body)
            parts!!.add(body2)
        }

        if (size == 3) {
            var requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), mFileArray!![0])
            var body = MultipartBody.Part.createFormData("files", mFileArray!![0].name, requestFile)

            var requestFile2 = RequestBody.create(MediaType.parse("multipart/form-data"), mFileArray!![1])
            var body2 = MultipartBody.Part.createFormData("files", mFileArray!![1].name, requestFile2)

            var requestFile3 = RequestBody.create(MediaType.parse("multipart/form-data"), mFileArray!![2])
            var body3 = MultipartBody.Part.createFormData("files", mFileArray!![2].name, requestFile3)

            parts!!.add(body)
            parts!!.add(body2)
            parts!!.add(body3)
        }
        if (size == 4) {
            var requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), mFileArray!![0])
            var body = MultipartBody.Part.createFormData("files", mFileArray!![0].name, requestFile)

            var requestFile2 = RequestBody.create(MediaType.parse("multipart/form-data"), mFileArray!![1])
            var body2 = MultipartBody.Part.createFormData("files", mFileArray!![1].name, requestFile2)

            var requestFile3 = RequestBody.create(MediaType.parse("multipart/form-data"), mFileArray!![2])
            var body3 = MultipartBody.Part.createFormData("files", mFileArray!![2].name, requestFile3)

            var requestFile4 = RequestBody.create(MediaType.parse("multipart/form-data"), mFileArray!![3])
            var body4 = MultipartBody.Part.createFormData("files", mFileArray!![3].name, requestFile4)

            parts!!.add(body)
            parts!!.add(body2)
            parts!!.add(body3)
            parts!!.add(body4)
        }

        mPresenter.upload2(parts!!.toList())
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

    private fun checkAccess() {
        intentFilter = IntentFilter()
        intentFilter!!.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        networkChangeReceiver = NetworkChangeReceiver()
        activity!!.registerReceiver(networkChangeReceiver, intentFilter)
    }


    private fun initViews() {
        StatusBarUtil.setTranslucentForImageViewInFragment(activity, 0, null)
        mAcceptInsuranceToolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(mAcceptInsuranceToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mAcceptInsuranceToolbar.setNavigationOnClickListener {
            pop()
            KeyboardUtils.hideSoftInput(activity!!)

        }

        mapping = SparseArray<ImageView>()

        if (activity is ImageWatcherHelper.Provider) {
            iwHelper = ImageWatcherHelper.with(activity, GlideSimpleLoader())
        }

        mFileArray = arrayOf(File(""), File(""), File(""), File(""))

        checkAccess()
        activity!!.registerReceiver(broadcastReceiver, IntentFilter("NO_ACCESS"))
        activity!!.registerReceiver(broadcastReceiver2, IntentFilter("ACCESS"))

        mSharedPreferences = activity!!.getSharedPreferences("UPLOAD_IMAGES", Context.MODE_PRIVATE)
        PAGE_FROM = arguments!!.getString("TAG")
        if (PAGE_FROM == "MARKLANDFRAGMENT") {
            val sp = activity!!.getSharedPreferences("ADDRESS", Context.MODE_PRIVATE)
            mTitleTV.text = sp.getString("address", "")
            mLatlngTV.text = sp.getString("latlng", "")
            mAreaTV.text = String.format("%s亩", arguments!!.getString("LOCATION_AREA"))
            mPerimeterTV.text = String.format("%s千米", arguments!!.getString("LOCATION_PERIMETER"))
        } else if (PAGE_FROM == "ILLEGALARCHITECTURALCONTOURSEDITFRAGMENT" || PAGE_FROM == "RESULTINFOEDIT") {
            Logger.d("<<<<<<")
            mCommitBtn.text = "保存修改"
            mCommitRL.setBackgroundColor(Color.parseColor("#6299FF"))
            mAIUploadImagesIV.visibility = View.GONE
            Logger.d(arguments!!.getString("ID"))
            val sp = activity!!.getSharedPreferences("ADDRESS", Context.MODE_PRIVATE)
            mLatlngTV.text = sp.getString("latlng", "")
            mTitleTV.text = sp.getString("address", "")
            mPresenter.selectIllegal(arguments!!.getString("ID"))
        }
//        if (arguments!!.getString("fromMyInsure") != null) {
//            if (arguments!!.getString("fromMyInsure").equals("MyInsure")) {
//                mCommitRL.visibility = View.INVISIBLE
//            }
//        }


        mAIUploadImagesIV.setOnClickListener(this)
        mAIImage1.setOnClickListener(this)
        mAIImage2.setOnClickListener(this)
        mAIImage3.setOnClickListener(this)
        mAIImage4.setOnClickListener(this)
        mAcceptInsuranceIdInfoRL.setOnClickListener(this)
        mAcceptInsuranceAddressRl.setOnClickListener(this)
//        mInsureCountRL.setOnClickListener(this)
        mCommitRL.setOnClickListener(this)
        mBankRL.setOnClickListener(this)
        mSpeciesNameRL.setOnClickListener(this)
        mBuildingRL.setOnClickListener(this)
        mDangerRL.setOnClickListener(this)
        mPlantLocationRL.setOnClickListener(this)
        mliangwei.setOnClickListener(this)
        mNameET.addTextChangedListener(mListener)
        mTelET.addTextChangedListener(mListener)
        mWorkET.addTextChangedListener(mListener)
        mLayerET.addTextChangedListener(mListener)
        mFourET.addTextChangedListener(mListener)
        mImages = arrayOf("0", "0", "0", "0")

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && data != null) {
            mAIUploadImagesIV.visibility = View.GONE
            val images = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT)
            val editor = mSharedPreferences!!.edit()
            editor.putString("upload_images", images.toString())
            editor.commit()

            val sp = activity!!.getSharedPreferences("UPLOAD_IMAGES", Context.MODE_PRIVATE)
            var currImages = sp.getString("upload_images", "").substring(1, sp.getString("upload_images", "").length - 1)
            mImagesArray!!.addAll(currImages.split(",") as MutableList<String>)
            Logger.d(mImagesArray)
            for (i in 0 until mImagesArray!!.size) {
                mImagesArray!![i] = mImagesArray!![i].trim()
                mImages!![i] = mImagesArray!![i]
                mFileArray!![i] = File(mImagesArray!![i])

            }
            Logger.d(mImages)
            imageCounts = mImagesArray!!.size
            checkCommitDone()

            when (mImagesArray!!.size) {
                1 -> setImage1(mImagesArray!![0])
                2 -> setImage12(mImagesArray!![0], mImagesArray!![1])
                3 -> setImage123(mImagesArray!![0], mImagesArray!![1], mImagesArray!![2])
                4 -> setImage1234(mImagesArray!![0], mImagesArray!![1], mImagesArray!![2], mImagesArray!![3])
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onFragmentResult(requestCode: Int, resultCode: Int, data: Bundle) {
        super.onFragmentResult(requestCode, resultCode, data)
        if (requestCode == 0x001 && resultCode == ISupportFragment.RESULT_OK) {
            mIdIndex = data.getString("ID_INFO").split(" ")[0].toInt()
            mIdNumber = data.getString("ID_INFO").split(" ")[1]
            Logger.d(data.getString("ID_INFO"))
            if (mIdIndex != -1) {
                identityType = mIdTypeList[mIdIndex]
                idNumber = mIdNumber.toString()
                mIdInfoTV.text = mIdTypeList[mIdIndex] + " " + mIdNumber
                checkCommitDone()
            }
        } else if (requestCode == 0x003 && resultCode == ISupportFragment.RESULT_OK) {
            insurerAddress = data.getString("INSURER_ADDRESS")
            plantingPlace = data.getString("PLANTING_PLACE")
            mRegisterTV.text = insurerAddress + plantingPlace
            checkCommitDone()
//            flag4 = true
//            checkCommitFlag()
        } else if (requestCode == 0x004 && resultCode == ISupportFragment.RESULT_OK) {
            mMaterial = data.getString("MATERIAL_INDEX")
            mMaterialTypeTV.text = mMaterialList[mMaterial.toInt()]
            checkCommitDone()
//            flag4 = true
//            checkCommitFlag()
        }
    }

    private fun setImage1(url: String) {
        mAIImage1.visibility = View.VISIBLE
        mAIImage2.visibility = View.VISIBLE
        MAX_IMAGE -= 1
        mAIImage1.setBackgroundColor(Color.parseColor("#00000000"))
        Glide.with(this).load(File(url)).apply(OPTIONS).into(mAIImage1)

    }

    private fun setImage12(url: String, url2: String) {
        mAIImage1.visibility = View.VISIBLE
        mAIImage2.visibility = View.VISIBLE
        mAIImage3.visibility = View.VISIBLE
        MAX_IMAGE -= 2
        IMAGE2_FLAG = true

        mAIImage1.setBackgroundColor(Color.parseColor("#00000000"))
//        mAIImage2.setBackgroundColor(Color.parseColor("#00000000"))
        Glide.with(this).load(File(url)).apply(OPTIONS).into(mAIImage1)
        Glide.with(this).load(File(url2.trim())).apply(OPTIONS).into(mAIImage2)

    }

    private fun setImage123(url: String, url2: String, url3: String) {
        mAIImage1.visibility = View.VISIBLE
        mAIImage2.visibility = View.VISIBLE
        mAIImage3.visibility = View.VISIBLE
        mAIImage4.visibility = View.VISIBLE
        MAX_IMAGE -= 3
        IMAGE2_FLAG = true
        IMAGE3_FLAG = true

        mAIImage1.setBackgroundColor(Color.parseColor("#00000000"))
        mAIImage2.setBackgroundColor(Color.parseColor("#00000000"))
        mAIImage3.setBackgroundColor(Color.parseColor("#00000000"))

        Glide.with(this).load(File(url)).apply(OPTIONS).into(mAIImage1)
        Glide.with(this).load(File(url2.trim())).apply(OPTIONS).into(mAIImage2)
        Glide.with(this).load(File(url3.trim())).apply(OPTIONS).into(mAIImage3)
    }

    private fun setImage1234(url: String, url2: String, url3: String, url4: String) {
        mAIImage1.visibility = View.VISIBLE
        mAIImage2.visibility = View.VISIBLE
        mAIImage3.visibility = View.VISIBLE
        mAIImage4.visibility = View.VISIBLE
        IMAGE2_FLAG = true
        IMAGE3_FLAG = true
        IMAGE4_FLAG = true


        mAIImage1.setBackgroundColor(Color.parseColor("#00000000"))
        mAIImage2.setBackgroundColor(Color.parseColor("#00000000"))
        mAIImage3.setBackgroundColor(Color.parseColor("#00000000"))
        mAIImage4.setBackgroundColor(Color.parseColor("#00000000"))

        Glide.with(this).load(File(url)).apply(OPTIONS).into(mAIImage1)
        Glide.with(this).load(File(url2.trim())).apply(OPTIONS).into(mAIImage2)
        Glide.with(this).load(File(url3.trim())).apply(OPTIONS).into(mAIImage3)
        Glide.with(this).load(File(url4.trim())).apply(OPTIONS).into(mAIImage4)

    }

    private fun selectImage() {
        val intent = Intent(activity, ImageSelectorActivity::class.java)
        intent.putExtra(ImageSelector.MAX_SELECT_COUNT, MAX_IMAGE)
        intent.putExtra(ImageSelector.IS_SINGLE, false)
        intent.putExtra(ImageSelector.USE_CAMERA, true)
        startActivityForResult(intent, REQUEST_CODE)
    }

    private fun startFullScreenActivity(position: Int, view: View) {
        var intent = Intent(activity, ShowImageActivity::class.java)
        var bundle = Bundle()
        bundle.putStringArray("IMAGES", mImages!!)
        bundle.putString("INDEX", position.toString())
        intent.putExtras(bundle)
//        startActivity(intent)
        val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity!!, view, "image")
        try {
            ActivityCompat.startActivity(activity!!, intent, optionsCompat.toBundle())
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            startActivity(intent)
        }
    }

    private val onDateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        mYear = year
        mMonth = monthOfYear
        mDay = dayOfMonth
        val days: String = if (mMonth + 1 < 10) {
            StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).toString()
        } else {
            StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).toString()
        }
        Logger.d(days)
        mDate = days
        mDateTV.text = mDate
        checkCommitDone()

    }

    private fun setDialogBroadcast(string: String, i: Int) {
        val viewDialogBroadcast: View
        var index = 0
        val inflater: LayoutInflater = LayoutInflater.from(activity)
        viewDialogBroadcast = inflater.inflate(R.layout.dialog_radio, null) as LinearLayout
        val groupBroadcast = viewDialogBroadcast.findViewById<View>(R.id.groupBroadcast) as RadioGroup
        val rbtn_BroadcastClose = viewDialogBroadcast.findViewById<View>(R.id.rbtn_BroadcastClose) as RadioButton
        val rbtn_BroadcastFifteen = viewDialogBroadcast.findViewById<View>(R.id.rbtn_BroadcastFifteen) as RadioButton

        if (i == 0) {
            groupBroadcast.check(rbtn_BroadcastClose.id)
            index = 0
        } else if (i == 1) {
            groupBroadcast.check(rbtn_BroadcastFifteen.id)
            index = 1
        }

        groupBroadcast.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == rbtn_BroadcastClose.id) {
                index = 0
            } else if (checkedId == rbtn_BroadcastFifteen.id) {
                index = 1
            }
        }
        AlertDialog.Builder(activity!!)
                .setView(viewDialogBroadcast)
                .setTitle(string)
                .setNegativeButton("取消") { dialogInterface, j ->
                    dialogInterface.dismiss()
                }
                .setPositiveButton("确定") { dialogInterface, j ->
                    dialogInterface.dismiss()
                    Logger.d(index)
                    mDangerousIndex = index
                    mDangerousTV.text = mYesOrNoList[mDangerousIndex!!]
                    checkCommitDone()

                }
                .create().show()
    }

    private fun setDialogBroadcast2(string: String, i: Int) {
        val viewDialogBroadcast: View
        var index = 0
        val inflater: LayoutInflater = LayoutInflater.from(activity)
        viewDialogBroadcast = inflater.inflate(R.layout.dialog_radio, null) as LinearLayout
        val groupBroadcast = viewDialogBroadcast.findViewById<View>(R.id.groupBroadcast) as RadioGroup
        val rbtn_BroadcastClose = viewDialogBroadcast.findViewById<View>(R.id.rbtn_BroadcastClose) as RadioButton
        val rbtn_BroadcastFifteen = viewDialogBroadcast.findViewById<View>(R.id.rbtn_BroadcastFifteen) as RadioButton

        if (i == 0) {
            groupBroadcast.check(rbtn_BroadcastClose.id)
            index = 0
        } else if (i == 1) {
            groupBroadcast.check(rbtn_BroadcastFifteen.id)
            index = 1
        }

        groupBroadcast.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == rbtn_BroadcastClose.id) {
                index = 0
            } else if (checkedId == rbtn_BroadcastFifteen.id) {
                index = 1
            }
        }
        AlertDialog.Builder(activity!!)
                .setView(viewDialogBroadcast)
                .setTitle(string)
                .setNegativeButton("取消") { dialogInterface, j ->
                    dialogInterface.dismiss()
                }
                .setPositiveButton("确定") { dialogInterface, j ->
                    dialogInterface.dismiss()
                    Logger.d(index)
                    mLiangWeiIndex = index
                    mLiangweiTV.text = mYesOrNoList[mLiangWeiIndex!!]
                    checkCommitDone()

                }
                .create().show()
    }

    private fun checkCommitDone() {
        if (!mNameET.text.toString().isEmpty() && !mTelET.text.toString().isEmpty() && !mWorkET.text.toString().isEmpty() && !mIdInfoTV.text.toString().isEmpty() &&
//                !mRegisterTV.text.toString().isEmpty() &&
                !mLayerET.text.toString().isEmpty() &&
                !mMaterialTypeTV.text.toString().isEmpty() && !mDateTV.text.toString().isEmpty() && !mDangerousTV.text.toString().isEmpty() &&
                !mLiangweiTV.text.toString().isEmpty() && !mFourET.text.toString().isEmpty()
                && checkImage())
            mCommitRL.setBackgroundColor(Color.parseColor("#6299FF"))
        else
            if (PAGE_FROM == "ILLEGALARCHITECTURALCONTOURSEDITFRAGMENT" || PAGE_FROM == "RESULTINFOEDIT") {
                mCommitRL.setBackgroundColor(Color.parseColor("#6299FF"))
            } else
                mCommitRL.setBackgroundColor(Color.parseColor("#C5C5C5"))

    }

    private var mListener: TextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // TODO Auto-generated method stub
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                       after: Int) {
            // TODO Auto-generated method stub
        }

        override fun afterTextChanged(s: Editable) {
            // TODO Auto-generated method stub
            checkCommitDone()
        }
    }

    fun newInstance(): InfoEditFragment {
        val args = Bundle()
        val fragment = InfoEditFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info_edit, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()

    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportVisible() {

        super.onSupportVisible()
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

    }

    fun stampToDate(s: String): String {
        val res: String
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val lt = s.toLong()
        val date = Date(lt)
        res = simpleDateFormat.format(date)
        return res
    }
}
