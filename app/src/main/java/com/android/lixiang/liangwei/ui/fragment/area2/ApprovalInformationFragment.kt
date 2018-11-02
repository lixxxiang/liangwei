package com.android.lixiang.liangwei.ui.fragment.area2

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.android.lixiang.base.ui.fragment.BaseMvpFragment
import com.android.lixiang.liangwei.GlideSimpleLoader
import com.android.lixiang.liangwei.R
import com.android.lixiang.liangwei.presenter.ApprovalInfomationPresenter
import com.android.lixiang.liangwei.presenter.data.bean.SelectExamBean
import com.android.lixiang.liangwei.presenter.injection.component.DaggerApprovalInfomationFragmentComponent
import com.android.lixiang.liangwei.presenter.injection.module.ApprovalInfomationModule
import com.android.lixiang.liangwei.presenter.view.ApprovalInfomationEditView
import com.android.lixiang.liangwei.presenter.view.ApprovalInfomationView
import com.android.lixiang.liangwei.ui.fragment.area1.InfoFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.ielse.imagewatcher.ImageWatcherHelper
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_approval_information.*
import me.yokeyword.fragmentation.SupportFragment
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import java.text.SimpleDateFormat
import java.util.*


class ApprovalInformationFragment : BaseMvpFragment<ApprovalInfomationPresenter>(), View.OnClickListener, ApprovalInfomationView {

    private var ID: String? = null
    private val OPTIONS = RequestOptions().centerCrop()
    private var iwHelper: ImageWatcherHelper? = null
    private val pictureList = ArrayList<Uri>()
    private var mapping = SparseArray<ImageView>()

    override fun returnSelectExam(selectExamBean: SelectExamBean) {
        ID = selectExamBean.data.info.infonumber
        mApprovalInfomationIdTV.text = selectExamBean.data.info.number
        mWorkTV.text = selectExamBean.data.info.work
        mDateTV.text = stampToDate(selectExamBean.data.info.createtime.toString())
        mLostTV.text = selectExamBean.data.info.lost
        mIdTV.text = selectExamBean.data.info.infonumber
        mPlantLocationET.text = selectExamBean.data.info.info
        var size = selectExamBean.data.info.url.split(",").size
        val urls = selectExamBean.data.info.url.split(",")
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

    override fun injectComponent() {
        DaggerApprovalInfomationFragmentComponent.builder().fragmentComponent(fragmentComponent).approvalInfomationModule(ApprovalInfomationModule()).build().inject(this)
        mPresenter.mView = this
    }

    override fun onClick(v: View?) {
        when (v) {
//            mIdConfirmRL -> {
//                start(LocationInfoFragment().newInstance())
//            }
            mIdRL -> {
                val fragment = InfoFragment().newInstance()
                val bundle = Bundle()
                bundle.putString("ID", ID)
                Logger.d(ID)
                bundle.putString("TAG", "APPROVAL_INFOMATION")
                fragment.arguments = bundle
                start(fragment)
            }

            mOutlineRL -> {
//                start()
                val fragment = ShowLandFragment().newInstance()
                val bundle = Bundle()
                bundle.putString("ID", ID)
                bundle.putString("TAG","APPROVAL_INFOMATION")
                fragment.arguments = bundle
                start(fragment)
            }

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

        }
    }

    private fun initViews() {
        mApprovalInfomationToolbar.title = "审批信息"
        (activity as AppCompatActivity).setSupportActionBar(mApprovalInfomationToolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        mIdRL.setOnClickListener(this)
        mOutlineRL.setOnClickListener(this)
        mPresenter.selectExam(arguments!!.getString("APPROVAL_ID"))
        mapping = SparseArray<ImageView>()

        mApprovalInfomationToolbar.setNavigationOnClickListener {
            pop()
        }
        if (activity is ImageWatcherHelper.Provider) {
            iwHelper = ImageWatcherHelper.with(activity, GlideSimpleLoader())
        }

        mAIImage1.setOnClickListener(this)
        mAIImage2.setOnClickListener(this)
        mAIImage3.setOnClickListener(this)
        mAIImage4.setOnClickListener(this)
    }

    fun newInstance(): ApprovalInformationFragment {
        val args = Bundle()
        val fragment = ApprovalInformationFragment()
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_approval_information, container, false)
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

    fun stampToDate(s: String): String {
        val res: String
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val lt = s.toLong()
        val date = Date(lt)
        res = simpleDateFormat.format(date)
        return res
    }
}
