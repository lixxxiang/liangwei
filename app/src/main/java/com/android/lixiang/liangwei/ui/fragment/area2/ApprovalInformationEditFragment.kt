package com.android.lixiang.liangwei.ui.fragment.area2

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
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
import android.widget.ImageView
import android.widget.Toast
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.liangwei.GlideSimpleLoader
import com.android.lixiang.liangwei.R
import com.android.lixiang.liangwei.presenter.data.bean.ExamInfoBean
import com.android.lixiang.liangwei.presenter.injection.component.DaggerApprovalInfomationEditFragmentComponent
import com.android.lixiang.liangwei.presenter.injection.module.ApprovalInfomationEditModule
import com.android.lixiang.liangwei.presenter.ApprovalInfomationEditPresenter
import com.android.lixiang.liangwei.presenter.data.api.ApiService
import com.android.lixiang.liangwei.presenter.data.bean.SelectExamBean
import com.android.lixiang.liangwei.presenter.data.bean.UpdateExamInfoBean
import com.android.lixiang.liangwei.presenter.data.bean.UploadBean
import com.android.lixiang.liangwei.presenter.view.ApprovalInfomationEditView
import com.android.lixiang.liangwei.ui.activity.ShowImageActivity
import com.android.lixiang.liangwei.ui.fragment.area1.InfoEditFragment
import com.android.lixiang.liangwei.ui.fragment.area1.InfoFragment
import com.blankj.utilcode.util.KeyboardUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.donkingliang.imageselector.ImageSelectorActivity
import com.donkingliang.imageselector.utils.ImageSelector
import com.github.ielse.imagewatcher.ImageWatcherHelper
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_approval_information_edit.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class ApprovalInformationEditFragment : BaseMvpFragment<ApprovalInfomationEditPresenter>(), View.OnClickListener, ApprovalInfomationEditView {


    private var ca = Calendar.getInstance()
    private var mYear = ca.get(Calendar.YEAR)
    private var mMonth = ca.get(Calendar.MONTH)
    private var mDay = ca.get(Calendar.DAY_OF_MONTH)
    private var day: String? = null
    private val OPTIONS = RequestOptions().centerCrop()
    private var work: String? = null
    private var lost: String? = null
    private var infonumber: String? = null
    private var info: String? = null
    private var label: String? = null
    private var spnumber: String? = null
    private var sptime: String? = null
    private var isClickable: Boolean? = false
    private var mImages: Array<String>? = arrayOf()
    private var LId: String? = null
    private var SId: String? = null
    private var MAX_IMAGE = 4
    private val REQUEST_CODE = 0x00000011
    private var IMAGE2_FLAG = false
    private var IMAGE3_FLAG = false
    private var IMAGE4_FLAG = false
    private var mImagesArray: MutableList<String>? = mutableListOf()
    private var mFileArray: Array<File>? = null
    private var imageCounts: Int? = 0
    private var parts: MutableList<MultipartBody.Part>? = mutableListOf()
    private var url: String? = null
    private var iwHelper: ImageWatcherHelper? = null
    private val pictureList = ArrayList<Uri>()
    private var mapping = SparseArray<ImageView>()

    private fun initViews() {
        mApprovalInfomationTToolbar.title = "审批信息"
        (activity as AppCompatActivity).setSupportActionBar(mApprovalInfomationTToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mApprovalInfomationTToolbar.setNavigationOnClickListener {
            KeyboardUtils.hideSoftInput(activity!!)

            pop()
        }

        mapping = SparseArray<ImageView>()

        if (activity is ImageWatcherHelper.Provider) {
            iwHelper = ImageWatcherHelper.with(activity, GlideSimpleLoader())
        }

        mCommitBtn.setOnClickListener(this)
        mSelectDateRL.setOnClickListener(this)
        mOutlineRL.setOnClickListener(this)
        mIdET.addTextChangedListener(mListener)
        mWorkET.addTextChangedListener(mListener)
        mLostET.addTextChangedListener(mListener)
        mAIUploadImagesIV.setOnClickListener(this)
//        mRemarkET.addTextChangedListener(mListener)
        mIdTV.text = arguments!!.getString("ID")
        mIdRL.setOnClickListener(this)
        mAIImage1.setOnClickListener(this)
        mAIImage2.setOnClickListener(this)
        mAIImage3.setOnClickListener(this)
        mAIImage4.setOnClickListener(this)

        /**
         * 从列表进
         */
        if (arguments!!.getString("TAG") == "ApprovalInfomationEditViewPagerFragment") {
            mTV.text = "保存修改"
            mCommitBtn.setBackgroundColor(Color.parseColor("#6299FF"))
            mCommitBtn.isClickable = true
            mAIUploadImagesIV.visibility = View.GONE
            mPresenter.selectExam(arguments!!.getString("ID"))
        }
        mFileArray = arrayOf(File(""), File(""), File(""), File(""))
        mImages = arguments!!.getStringArray("IMAGES")
        if (mImages != null)
            when (mImages!!.size) {
                0 -> {
                    mAIImage1.visibility = View.GONE
                    mAIImage2.visibility = View.GONE
                    mAIImage3.visibility = View.GONE
                    mAIImage4.visibility = View.GONE
                }
                1 -> {
                    Glide.with(this).load(File(mImages!![0])).apply(OPTIONS).into(mAIImage1)
                    mAIImage2.visibility = View.GONE
                    mAIImage3.visibility = View.GONE
                    mAIImage4.visibility = View.GONE
                }
                2 -> {
                    Glide.with(this).load(File(mImages!![0])).apply(OPTIONS).into(mAIImage1)
                    Glide.with(this).load(File(mImages!![1])).apply(OPTIONS).into(mAIImage2)
                    mAIImage3.visibility = View.GONE
                    mAIImage4.visibility = View.GONE
                }
                3 -> {
                    Glide.with(this).load(File(mImages!![0])).apply(OPTIONS).into(mAIImage1)
                    Glide.with(this).load(File(mImages!![1])).apply(OPTIONS).into(mAIImage2)
                    Glide.with(this).load(File(mImages!![2])).apply(OPTIONS).into(mAIImage3)
                    mAIImage4.visibility = View.GONE
                }
                4 -> {
                    Glide.with(this).load(File(mImages!![0])).apply(OPTIONS).into(mAIImage1)
                    Glide.with(this).load(File(mImages!![1])).apply(OPTIONS).into(mAIImage2)
                    Glide.with(this).load(File(mImages!![2])).apply(OPTIONS).into(mAIImage3)
                    Glide.with(this).load(File(mImages!![3])).apply(OPTIONS).into(mAIImage4)

                }
            }

    }

    @SuppressLint("SimpleDateFormat")
    override fun onClick(v: View?) {
        when (v) {
            mCommitBtn -> {
                Logger.d("dddddd")

                work = mWorkET.text.toString()
                lost = mLostET.text.toString()
                infonumber = arguments!!.getString("ID")
                info = mRemarkET.text.toString()
                label = ""
                spnumber = mIdET.text.toString()
                sptime = mDateTV.text.toString()
                Logger.d(arguments!!.getString("TAG"))
                if (arguments!!.getString("TAG") == "ApprovalInfomationEditViewPagerFragment") {
                    val sd = mDateTV.text.toString()
                    Logger.d(work)
                    Logger.d(lost)
                    Logger.d(LId)
                    Logger.d(info)
                    Logger.d(label)
                    Logger.d(mIdET.text.toString())
                    Logger.d(sd!!)
                    Logger.d("")
                    Logger.d(arguments!!.getString("ID"))
                    mPresenter.updateexaminfo(work!!, lost!!, LId!!, info!!, label!!, mIdET.text.toString(), sd, url!!, arguments!!.getString("ID"))
                } else {

                    if (isClickable!!) {
                        mProgressbar.visibility = View.VISIBLE
                        mTV.visibility = View.GONE
                        mCommitBtn.isClickable = false
                        if (mWorkET.text.toString().isEmpty() ||
//                                mLostET.text.toString().isEmpty() ||
                                mIdET.text.toString().isEmpty() ||
                                mDateTV.text.toString().isEmpty() ||
                                !checkImage()
                        ) {
                            Toast.makeText(activity!!, "未录入全部信息", Toast.LENGTH_SHORT).show()

                        } else {
                            Logger.d("dddddd")
                            uploadImage()
//                            mPresenter.examinfo(work!!, lost!!, infonumber!!, info!!, label!!, spnumber!!, sptime!!, "")
                        }
                    }
                }
            }

            mSelectDateRL -> {
                DatePickerDialog(activity!!, R.style.MyDatePickerDialogTheme, onDateSetListener, mYear, mMonth, mDay).show()
            }

            mIdRL -> {
                KeyboardUtils.hideSoftInput(activity)

                val fragment = InfoFragment().newInstance()
                val bundle = Bundle()
                if(arguments!!.getString("TAG") == "ApprovalInfomationEditViewPagerFragment"){
                    bundle.putString("ID", LId)
                }else{
                    bundle.putString("ID", arguments!!.getString("ID"))

                }
                bundle.putString("TAG", "ApprovalInformationEdit")
                if (mImages != null) {
                    bundle.putStringArray("IMAGES", mImages!!)
                }
                fragment.arguments = bundle
                start(fragment)
            }
            mOutlineRL -> {
                KeyboardUtils.hideSoftInput(activity)

                val fragment = ShowLandFragment().newInstance()
                val bundle = Bundle()
                bundle.putString("ID", arguments!!.getString("ID"))
                bundle.putString("TAG", "ApprovalInformationEdit")
                fragment.arguments = bundle
                start(fragment)
            }
            mAIUploadImagesIV -> {
                selectImage()
            }


//            mAIImage1 -> {
//                Logger.d("ffff")
//                startFullScreenActivity(0, mAIImage1)
//            }
//            mAIImage2 -> {
//                if (!IMAGE2_FLAG) {
//                    selectImage()
//                } else {
////                    startActivity<ShowImageActivity>()
////                    startPictureActivity(mImage2)
//                    startFullScreenActivity(1, mAIImage2)
//                }
//            }
//            mAIImage3 -> {
//                if (!IMAGE3_FLAG) {
//                    selectImage()
//                } else {
//                    startFullScreenActivity(2, mAIImage3)
//                }
//            }
//
//            mAIImage4 -> {
//                if (!IMAGE4_FLAG) {
//                    selectImage()
//                } else {
//                    startFullScreenActivity(3, mAIImage4)
//                }
//            }

            mAIImage1 -> {
                if (arguments!!.getString("TAG") == "ApprovalInfomationEditViewPagerFragment" || arguments!!.getString("TAG") == "RESULTINFOEDIT") {
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
                if (arguments!!.getString("TAG") == "ApprovalInfomationEditViewPagerFragment" || arguments!!.getString("TAG") == "RESULTINFOEDIT") {
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
                if (arguments!!.getString("TAG") == "ApprovalInfomationEditViewPagerFragment" || arguments!!.getString("TAG") == "RESULTINFOEDIT") {
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
                if (arguments!!.getString("TAG") == "ApprovalInfomationEditViewPagerFragment" || arguments!!.getString("TAG") == "RESULTINFOEDIT") {
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

        }
    }


    override fun returnUpload(uploadBean: UploadBean) {
        if (uploadBean.message == "success") {
            if (mWorkET.text.toString().isEmpty() ||
//                    mLostET.text.toString().isEmpty() ||
                    mIdET.text.toString().isEmpty() ||
                    mDateTV.text.toString().isEmpty() ||
                    !checkImage()
            ) {
                Toast.makeText(activity!!, "未录入全部信息", Toast.LENGTH_SHORT).show()

            } else {
                work = mWorkET.text.toString()
                lost = mLostET.text.toString()
                infonumber = arguments!!.getString("ID")
                info = mRemarkET.text.toString()
                label = ""
                spnumber = mIdET.text.toString()
                sptime = day
                mPresenter.examinfo(work!!, lost!!, infonumber!!, info!!, label!!, spnumber!!, sptime!!, uploadBean.data.url)
            }
        }
    }

    override fun returnUpdateExamInfo(updateExamInfoBean: UpdateExamInfoBean) {
        if (updateExamInfoBean.message == "success") {
            val snackBar = Snackbar.make(view!!, "保存成功", Snackbar.LENGTH_SHORT)
            snackBar.show()
        }
    }

    override fun returnSelectExam(selectExamBean: SelectExamBean) {
        Logger.d(selectExamBean.data.info.infonumber)
        if (selectExamBean.message == "success") {
            LId = selectExamBean.data.info.infonumber
            mIdTV.text = selectExamBean.data.info.infonumber
            mLostET.setText(selectExamBean.data.info.lost)
            mIdET.setText(selectExamBean.data.info.spnumber)
            mWorkET.setText(selectExamBean.data.info.work)
            mDateTV.text = stampToDate(selectExamBean.data.info.sptime.toString())
            mRemarkET.setText(selectExamBean.data.info.info)
            var size = selectExamBean.data.info.url.split(",").size
            val urls = selectExamBean.data.info.url.split(",")
            url = selectExamBean.data.info.url
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
    }

    override fun injectComponent() {
        DaggerApprovalInfomationEditFragmentComponent.builder().fragmentComponent(fragmentComponent).approvalInfomationEditModule(ApprovalInfomationEditModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun test(string: String) {
        Logger.d(string)
    }

    override fun returnexamInfo(examInfoBean: ExamInfoBean) {
        Logger.d(examInfoBean.data.number)
        if (examInfoBean.message == "success") {
            val fragment = SuccessAcceptFragment().newInstance()
            val bundle = Bundle()
            bundle.putString("APPROVAL_ID", examInfoBean.data.number)
            bundle.putString("TAG", "APPROVALINFOMATION")
            fragment.arguments = bundle
            start(fragment)
        }
    }


    private fun uploadImage() {
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && data != null) {
            mAIUploadImagesIV.visibility = View.GONE
            val images = data.getStringArrayListExtra(ImageSelector.SELECT_RESULT)
//            val editor = mSharedPreferences!!.edit()
//            editor.putString("upload_images", images.toString())
//            editor.commit()
//
//            val sp = activity!!.getSharedPreferences("UPLOAD_IMAGES", Context.MODE_PRIVATE)
            var currImages = images.toString().substring(1, images.toString().length - 1)
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

    private val onDateSetListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
        mYear = year
        mMonth = monthOfYear
        mDay = dayOfMonth
        val days: String = if (mMonth + 1 < 10) {
            StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).toString()
        } else {
            StringBuffer().append(mYear).append("-").append(mMonth + 1).append("-").append(mDay).toString()
        }
        mDateTV.text = days
        day = days
    }

    fun checkImage(): Boolean {
        return imageCounts != 0
    }


    fun newInstance(): ApprovalInformationEditFragment {
        val args = Bundle()
        val fragment = ApprovalInformationEditFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_approval_information_edit, container, false)
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
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }

    private fun checkCommitDone() {
        if (!mIdET.text.toString().isEmpty() && !mWorkET.text.toString().isEmpty() && !mWorkET.text.toString().isEmpty() &&
//                !mLostET.text.toString().isEmpty() &&
//                !mRemarkET.text.toString().isEmpty() &&
                !mDateTV.text.toString().isEmpty()
                && checkImage()) {
            mCommitBtn.setBackgroundColor(Color.parseColor("#6299FF"))
            isClickable = true
        } else {
            if (arguments!!.getString("TAG") == "ApprovalInfomationEditViewPagerFragment") {
            } else {
                mCommitBtn.setBackgroundColor(Color.parseColor("#C5C5C5"))
                isClickable = false
            }
        }
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

    fun stampToDate(s: String): String {
        val res: String
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val lt = s.toLong()
        val date = Date(lt)
        res = simpleDateFormat.format(date)
        return res
    }
}
