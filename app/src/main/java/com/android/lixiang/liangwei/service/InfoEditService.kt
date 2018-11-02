package com.android.lixiang.liangwei.service

import com.android.lixiang.liangwei.presenter.data.bean.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import java.io.File

interface InfoEditService {
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
                    line: String): Observable<IllegalInfoBean>

    fun updataIllegalInfo(name: String,
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
                          line: String,
                          number: String): Observable<UpdateIllegalInfoBean>

    fun selectIllegal(id: String): Observable<SelectIllegalBean>

    fun upload(collectingPictureNames: MultipartBody.Part): Observable<UploadBean>
    fun upload2(collectingPictureNames: List<MultipartBody.Part>): Observable<UploadBean>


}