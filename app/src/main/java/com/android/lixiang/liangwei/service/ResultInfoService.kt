package com.android.lixiang.liangwei.service

import com.android.lixiang.liangwei.presenter.data.bean.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import java.io.File

interface ResultInfoService {
    fun search(param: String, pagenum: String, pageSize: String): Observable<SearchBean>
}
