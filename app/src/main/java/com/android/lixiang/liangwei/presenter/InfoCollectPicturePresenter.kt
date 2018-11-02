package com.android.lixiang.liangwei.presenter

import com.android.lixiang.base.presenter.BasePresenter
import com.android.lixiang.liangwei.presenter.view.InfoCollectPictureView
import com.android.lixiang.liangwei.service.InfoCollectPictureService
import javax.inject.Inject

class InfoCollectPicturePresenter @Inject constructor(): BasePresenter<InfoCollectPictureView>(){
    @Inject
    lateinit var mInfoCollectPictureService : InfoCollectPictureService

    fun test(){
        mView.testfromPic(mInfoCollectPictureService.test())
    }




//    fun insertCollectingPicture(informationId:String,collectPictureNames:String){
//        mInfoCollectPictureService.getData(informationId,collectPictureNames).execute(object :BaseObserver<InfoCollectPictureBean>() {
//
//            override fun onNext(t: InfoCollectPictureBean) {
//                super.onNext(t)
//
//                mView.returnDatafromPic(t)
//            }
//        },lifecycleProvider)
//    }
}