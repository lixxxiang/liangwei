package com.android.lixiang.liangwei.presenter.data.repository

import com.android.lixiang.base.data.net.RetrofitFactory
import com.android.lixiang.liangwei.presenter.data.api.ApiService
import com.android.lixiang.liangwei.presenter.data.bean.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import javax.inject.Inject

class InfoEditRepository @Inject constructor() {
    fun illegalInfo(name: String,
                    phone: String,
                    work: String,
                    iid: String,
                    address: String,
                    area: String,
                    perimeter: String,
                    flag: String,
                    floor: String,
                    type: String,
                    time: String,
                    isdanger: String,
                    isliangwei: String,
                    info: String,
                    url: String,
                    status: String,
                    line: String): Observable<IllegalInfoBean> {
        return RetrofitFactory("http://59.110.161.48:8088/").create(ApiService::class.java).illegalinfo(
                name,
                phone,
                work,
                iid,
                address,
                area,
                perimeter,
                flag,
                floor,
                type,
                time,
                isdanger,
                isliangwei,
                info,
                url,
                status,
                line)
    }


    fun updateIllegalInfo(name: String,
                          phone: String,
                          work: String,
                          iid: String,
                          address: String,
                          area: String,
                          perimeter: String,
                          flag: String,
                          floor: String,
                          type: String,
                          time: String,
                          isdanger: String,
                          isliangwei: String,
                          info: String,
                          url: String,
                          status: String,
                          line: String
                          , number: String): Observable<UpdateIllegalInfoBean> {
        return RetrofitFactory("http://59.110.161.48:8088/").create(ApiService::class.java).updateillegalinfo(
                name,
                phone,
                work,
                iid,
                address,
                area,
                perimeter,
                flag,
                floor,
                type,
                time,
                isdanger,
                isliangwei,
                info,
                url,
                status,
                line, number)
    }

    fun selectIllegal(id: String): Observable<SelectIllegalBean> {
        return RetrofitFactory("http://" +
//                "59.110.162.194:5201" +
                "59.110.161.48:8088" +
                "/").create(ApiService::class.java).selectillegal(id)
    }

    fun upload(s2: MultipartBody.Part): Observable<UploadBean> {
        return RetrofitFactory("http://59.110.161.48:8088/").create(ApiService::class.java).upload(s2)
    }

    fun upload2(s2: List<MultipartBody.Part>): Observable<UploadBean> {
        return RetrofitFactory("http://59.110.161.48:8088/").create(ApiService::class.java).upload2(s2)
    }
}