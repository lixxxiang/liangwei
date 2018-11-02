package com.android.lixiang.liangwei.service.impl

import com.android.lixiang.liangwei.presenter.data.repository.InfoCollectPictureRepository
import com.android.lixiang.liangwei.service.InfoCollectPictureService
import javax.inject.Inject

class InfoCollectPictureServiceImpl @Inject constructor(): InfoCollectPictureService {

    @Inject
    lateinit var infoCollectPictureRepository : InfoCollectPictureRepository


//    override fun getData2(area: String, productType: String): Observable<DetailBean> {
//        Logger.d(area)
//        Logger.d(productType)
//        return infoCollectRepository.getDataFromRepository(area, productType).flatMap(Function <DetailBean, ObservableSource<DetailBean>> { t ->
//            return@Function Observable.just(t)
//        })
//    }

//    override fun getData(informationId: String, collectingPictureNames: String): Observable<InfoCollectPictureBean> {
////        Logger.d(loginNameId)
////        Logger.d(plotNumber)
//
////        return infoCollectPictureRepository.getDataFromRepository(informationId,collectingPictureNames).flatMap(Function <InfoCollectPictureBean,ObservableSource<InfoCollectPictureBean>> { t ->
////            return@Function Observable.just(t)
////        })
//    }

//    private var params: WeakHashMap<String, Any>? = WeakHashMap()
//    private var detailBean = DetailBean()

//    override fun getData(area: String, productType: String): DetailBean {
////        params!!["area"] = area
////        params!!["productType"] = productType
////
////        RestClient().builder()
////                .url("http://59.110.164.214:8024/global/programming/detail")
////                .params(params!!)
////                .success(object : ISuccess {
////                    override fun onSuccess(response: String) {
////                        detailBean = Gson().fromJson(response, DetailBean::class.java)
////                        Logger.d(response)
////
////                        if (detailBean.message == "success") {
////                        } else {
////                        }
////                    }
////                })
////                .error(object : IError {
////                    override fun onError(code: Int, msg: String) {}
////                })
////                .failure(object : IFailure {
////                    override fun onFailure() {}
////                })
////                .build()
////                .post()
////
//        return detailBean
//
//    }

    override fun test(): String {
        return "FFF"
    }
}