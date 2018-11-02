package com.android.lixiang.liangwei.ui.activity

import android.os.Bundle
import com.android.lixiang.base.utils.view.StatusBarUtil
import com.android.lixiang.liangwei.ui.fragment.HomeFragment
import com.android.lixiang.liangwei.R
import com.android.lixiang.liangwei.ui.fragment.LauncherFragment
import com.android.lixiang.liangwei.ui.fragment.ResultInfoEditFragment2
import com.facebook.drawee.backends.pipeline.Fresco
import me.yokeyword.fragmentation.SupportActivity
import com.android.lixiang.liangwei.ui.fragment.area1.FrescoSimpleLoader
import com.android.lixiang.liangwei.ui.fragment.area1.InfoCollectEntryFragment
import com.android.lixiang.liangwei.ui.fragment.area1.InfoEditFragment
import com.android.lixiang.liangwei.ui.fragment.area2.*
import com.github.ielse.imagewatcher.ImageWatcherHelper


class MainActivity : SupportActivity(), ImageWatcherHelper.Provider {
    private var iwHelper: ImageWatcherHelper? = null

    override fun iwHelper(): ImageWatcherHelper {
        return iwHelper!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        Fresco.initialize(this)

        StatusBarUtil.setTranslucentForImageViewInFragment(this, 0, null)

        iwHelper = ImageWatcherHelper.with(this, FrescoSimpleLoader())
        if (findFragment(HomeFragment::class.java) == null) {
            loadRootFragment(R.id.fl_tab_container, LauncherFragment().newInstance())
//            loadRootFragment(R.id.fl_tab_container, InfomationMaintenanceFragment().newInstance())

        }
    }

    ///////
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
////        if (resultCode == REQUEST_CODE){
////            val fragment = fragmentManager.findFragmentById(R.id.fl_tab_container)
////            fragment.onActivityResult(requestCode, resultCode, data)
////        }
////        val fragment = supportFragmentManager.findFragmentById(R.id.fl_tab_container)
////        fragment.onActivityResult(requestCode, resultCode, data)
//        if (data != null) {
//            val editor = mSharedPreferences!!.edit()
//            editor.putString("upload_images", data.getStringArrayListExtra(ImageSelector.SELECT_RESULT).toString())
//            editor.commit()
//        }
//
//
////        Glide.with(this).load(File(image[0])).into(InfoCollectFragment().image1!!)
//
//    }

//    override fun onBackPressedSupport(): Boolean {
//        if (!iwHelper!!.handleBackPressed()){
//            return onBackPressedSupport()
//        }
//        return super.onBackPressedSupport()
//    }

//    override fun onBackPressedSupport() {
//        if (!iwHelper!!.handleBackPressed()) {
//            return onBackPressedSupport()
//        }
//        super.onBackPressedSupport()
//    }
}
