package com.android.lixiang.liangwei.ui.fragment.area1

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
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import com.android.lixiang.base.net.RestClient
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.base.utils.view.StatusBarUtil
import com.android.lixiang.liangwei.GlideSimpleLoader
import com.android.lixiang.liangwei.R
import com.android.lixiang.liangwei.presenter.InfoPresenter
import com.android.lixiang.liangwei.presenter.data.bean.IfExamBean
import com.android.lixiang.liangwei.presenter.data.bean.SearchBean
import com.android.lixiang.liangwei.presenter.data.bean.SelectIllegalBean
import com.android.lixiang.liangwei.presenter.injection.component.DaggerInfoFragmentComponent
import com.android.lixiang.liangwei.presenter.injection.module.InfoModule
import com.android.lixiang.liangwei.presenter.view.InfoView
import com.android.lixiang.liangwei.ui.activity.ShowImageActivity
import com.android.lixiang.liangwei.ui.adapter.IllegalArchitecturalContoursAdapter
import com.android.lixiang.liangwei.ui.fragment.area2.ApprovalInformationEditFragment
import com.android.lixiang.liangwei.ui.fragment.area2.MaterialTypeFragment
import com.android.lixiang.liangwei.ui.fragment.area2.ShowLandFragment
import com.android.lixiang.liangwei.ui.fragment.area2.SuccessAcceptFragment
import com.android.lixiang.liangwei.ui.fragment.area2.bak.InsureCountFragment
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
import kotlinx.android.synthetic.main.fragment_info.*
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class InfoFragment : BaseMvpFragment<InfoPresenter>(), View.OnClickListener, InfoView {
    private var mMaterialList: MutableList<String> = mutableListOf("土", "砖", "砖混", "其它")
    private var mDangerousList: MutableList<String> = mutableListOf("是", "否")
    private val OPTIONS = RequestOptions().centerCrop()
    private var ifExamBean: IfExamBean? = null
    private var param: WeakHashMap<String, Any>? = WeakHashMap()
    private var iwHelper: ImageWatcherHelper? = null
    private val pictureList = ArrayList<Uri>()
    private var mapping = SparseArray<ImageView>()
    override fun injectComponent() {
        DaggerInfoFragmentComponent.builder().fragmentComponent(fragmentComponent).infoModule(InfoModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun returnSelectIllegal(selectIllegalBean: SelectIllegalBean) {
        Logger.d(selectIllegalBean.data.info.address)
        mPropertyOwnerNameET.text = selectIllegalBean.data.info.name
        mTelET.text = selectIllegalBean.data.info.phone
        mWorkTV.text = selectIllegalBean.data.info.work
        mIdTV.text = selectIllegalBean.data.info.iid
        mRegisterTV.text = selectIllegalBean.data.info.address
        mAreaTV.text = String.format("%s亩", selectIllegalBean.data.info.area)
        mPerimeterTV.text = String.format("%s千米", selectIllegalBean.data.info.perimeter)
        mLayerTV.text = selectIllegalBean.data.info.floor.toString()
        mMaterialTV.text = mMaterialList[selectIllegalBean.data.info.type]
        mDateTV.text = stampToDate(selectIllegalBean.data.info.createtime.toString())
        mDangerousTV.text = mDangerousList[selectIllegalBean.data.info.isdanger]
        mLiangweiTV.text = mDangerousList[selectIllegalBean.data.info.isliangwei]
        mFourTV.text = selectIllegalBean.data.info.info
        mAcceptInsuranceToolbar.title = String.format("信息ID：%s", selectIllegalBean.data.info.number)

        var size = selectIllegalBean.data.info.url.split(",").size
        val urls = selectIllegalBean.data.info.url.split(",")
        when(size){
            1 -> {
                mAIImage1.visibility = View.VISIBLE
                Glide.with(this).load("http://59.110.162.194:8085/2weiPic/" + urls[0]).apply(OPTIONS).into(mAIImage1)
                pictureList.add(Uri.parse("http://59.110.162.194:8085/2weiPic/" + urls[0]))
                mapping.put(0, view!!.findViewById<View>(R.id.mAIImage1) as ImageView)

            }
            2 -> {
                mAIImage1.visibility = View.VISIBLE
                Glide.with(this).load("http://59.110.162.194:8085/2weiPic/" + urls[0]).apply(OPTIONS).into(mAIImage1)
                mAIImage2.visibility = View.VISIBLE
                Glide.with(this).load("http://59.110.162.194:8085/2weiPic/" + urls[1]).apply(OPTIONS).into(mAIImage2)
                pictureList.add(Uri.parse("http://59.110.162.194:8085/2weiPic/" + urls[0]))
                pictureList.add(Uri.parse("http://59.110.162.194:8085/2weiPic/" + urls[1]))
                mapping.put(0, view!!.findViewById<View>(R.id.mAIImage1) as ImageView)
                mapping.put(1, view!!.findViewById<View>(R.id.mAIImage2) as ImageView)
            }
            3 -> {
                mAIImage1.visibility = View.VISIBLE
                Glide.with(this).load("http://59.110.162.194:8085/2weiPic/" + urls[0]).apply(OPTIONS).into(mAIImage1)
                mAIImage2.visibility = View.VISIBLE
                Glide.with(this).load("http://59.110.162.194:8085/2weiPic/" + urls[1]).apply(OPTIONS).into(mAIImage2)
                mAIImage3.visibility = View.VISIBLE
                Glide.with(this).load("http://59.110.162.194:8085/2weiPic/" + urls[2]).apply(OPTIONS).into(mAIImage3)
                pictureList.add(Uri.parse("http://59.110.162.194:8085/2weiPic/" + urls[0]))
                pictureList.add(Uri.parse("http://59.110.162.194:8085/2weiPic/" + urls[1]))
                pictureList.add(Uri.parse("http://59.110.162.194:8085/2weiPic/" + urls[2]))
                mapping.put(0, view!!.findViewById<View>(R.id.mAIImage1) as ImageView)
                mapping.put(1, view!!.findViewById<View>(R.id.mAIImage2) as ImageView)
                mapping.put(2, view!!.findViewById<View>(R.id.mAIImage3) as ImageView)
            }
            4 -> {
                mAIImage1.visibility = View.VISIBLE
                Glide.with(this).load("http://59.110.162.194:8085/2weiPic/" + urls[0]).apply(OPTIONS).into(mAIImage1)
                mAIImage2.visibility = View.VISIBLE
                Glide.with(this).load("http://59.110.162.194:8085/2weiPic/" + urls[1]).apply(OPTIONS).into(mAIImage2)
                mAIImage3.visibility = View.VISIBLE
                Glide.with(this).load("http://59.110.162.194:8085/2weiPic/" + urls[2]).apply(OPTIONS).into(mAIImage3)
                mAIImage4.visibility = View.VISIBLE
                Glide.with(this).load("http://59.110.162.194:8085/2weiPic/" + urls[3]).apply(OPTIONS).into(mAIImage4)
                pictureList.add(Uri.parse("http://59.110.162.194:8085/2weiPic/" + urls[0]))
                pictureList.add(Uri.parse("http://59.110.162.194:8085/2weiPic/" + urls[1]))
                pictureList.add(Uri.parse("http://59.110.162.194:8085/2weiPic/" + urls[2]))
                pictureList.add(Uri.parse("http://59.110.162.194:8085/2weiPic/" + urls[3]))
                mapping.put(0, view!!.findViewById<View>(R.id.mAIImage1) as ImageView)
                mapping.put(1, view!!.findViewById<View>(R.id.mAIImage2) as ImageView)
                mapping.put(2, view!!.findViewById<View>(R.id.mAIImage3) as ImageView)
                mapping.put(3, view!!.findViewById<View>(R.id.mAIImage4) as ImageView)
            }
        }
    }

    private var mImages: Array<String>? = arrayOf()
    private var mSharedPreferences: SharedPreferences? = null

    override fun onClick(v: View?) {
        when (v) {
            mAcceptInsuranceCommitRL -> {
                param!!["infonumber"] = arguments!!.getString("ID")
                RestClient().builder()
                        .url("http://59.110.161.48:8088/ifexam")
                        .params(param!!)
                        .success(object : ISuccess {
                            override fun onSuccess(response: String) {
                                Logger.d(response)
                                ifExamBean = Gson().fromJson(response, IfExamBean::class.java)
                                if (ifExamBean!!.data.isRes){
                                    val snackBar = Snackbar.make(view!!, "该违章建筑审批信息已存在", Snackbar.LENGTH_SHORT)
                                    snackBar.show()
                                }else{
                                    val fragment = ApprovalInformationEditFragment().newInstance()
                                    val bundle = Bundle()
                                    bundle.putString("ID", arguments!!.getString("ID"))
                                    bundle.putStringArray("IMAGES", mImages)
                                    fragment.arguments = bundle
                                    start(fragment)
                                }
                            }
                        })
                        .failure(object : IFailure {
                            override fun onFailure() {

                            }
                        })
                        .error(object : IError {
                            override fun onError(code: Int, msg: String) {

                            }
                        })
                        .build()
                        .post()



            }

//            mMyInsuranceCheckRL -> {
//                start(FillSurveyInfoFragment().newInstance())
//            }
            mAIImage1 -> {
//                startFullScreenActivity(0, mAIImage1)
                if (iwHelper != null) {
                    Logger.d(pictureList)
                    iwHelper!!.show(mAIImage1, mapping, pictureList)
                }
            }
            mAIImage2 -> {
//                startFullScreenActivity(1, mAIImage2)
                if (iwHelper != null) {
                    Logger.d(pictureList)
                    iwHelper!!.show(mAIImage2, mapping, pictureList)
                }
            }
            mAIImage3 -> {
//                startFullScreenActivity(2, mAIImage3)
                if (iwHelper != null) {
                    Logger.d(pictureList)
                    iwHelper!!.show(mAIImage3, mapping, pictureList)
                }
            }
            mAIImage4 -> {
//                startFullScreenActivity(3, mAIImage4)
                if (iwHelper != null) {
                    Logger.d(pictureList)
                    iwHelper!!.show(mAIImage4, mapping, pictureList)
                }
            }
            mBuildingRL -> {
                val fragment = ShowLandFragment().newInstance()
                val bundle = Bundle()
                bundle.putString("ID", arguments!!.getString("ID"))
                bundle.putString("TAG", "INFOFRAGMENT")
                fragment.arguments = bundle
                start(fragment)
            }


        }
    }

    private fun initViews() {
        StatusBarUtil.setTranslucentForImageViewInFragment(activity, 0, null)
        mAcceptInsuranceToolbar.title = ""
        (activity as AppCompatActivity).setSupportActionBar(mAcceptInsuranceToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mapping = SparseArray<ImageView>()

        if (activity is ImageWatcherHelper.Provider) {
            iwHelper = ImageWatcherHelper.with(activity, GlideSimpleLoader())
        }

        mAcceptInsuranceToolbar.setNavigationOnClickListener {
            KeyboardUtils.hideSoftInput(activity!!)

            pop()
        }



        if (arguments!!.getString("TAG") == "APPROVAL_INFOMATION"
                || arguments!!.getString("TAG") == "ApprovalInformationEdit"
                || arguments!!.getString("TAG") == "ILLEGALARCHITECTURAL"
                || arguments!!.getString("TAG") == "RESULTINFOFRAGMENT") {
            mAcceptInsuranceCommitRL.visibility = View.GONE
        }

        if (arguments!!.getString("TAG") == "ApprovalInformationEdit") {
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
        Logger.d(arguments!!.getString("ID"))

        mPresenter.selectIllegal(arguments!!.getString("ID"))
        mSharedPreferences = activity!!.getSharedPreferences("UPLOAD_IMAGES", Context.MODE_PRIVATE)

    }

    fun newInstance(): InfoFragment {
        val args = Bundle()
        val fragment = InfoFragment()
        fragment.arguments = args
        return fragment
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initViews()
        if (arguments!!.getString("fromMyInsure") != null) {
            if (arguments!!.getString("fromMyInsure").equals("MyInsure")) {
                mAcceptInsuranceCommitRL.visibility = View.INVISIBLE
            }
        }
        if (arguments!!.getString("TAG") != null) {
            if (arguments!!.getString("TAG").equals("SUCCESSCOMMIT")) {
                mAcceptInsuranceCommitRL.visibility = View.INVISIBLE
            }
        }
        mAIImage1.setOnClickListener(this)
        mAIImage2.setOnClickListener(this)
        mAIImage3.setOnClickListener(this)
        mAIImage4.setOnClickListener(this)
        mAcceptInsuranceCommitRL.setOnClickListener(this)
        mBuildingRL.setOnClickListener(this)
        mImages = arrayOf("0", "0", "0", "0")
    }

    override fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
    }

    override fun onSupportVisible() {
        super.onSupportVisible()
        activity!!.window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE

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

    fun stampToDate(s: String): String {
        val res: String
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val lt = s.toLong()
        val date = Date(lt)
        res = simpleDateFormat.format(date)
        return res
    }
}
