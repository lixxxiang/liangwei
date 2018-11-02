package com.android.lixiang.liangwei.service

import com.android.lixiang.liangwei.presenter.data.bean.DetailBean
import io.reactivex.Observable

interface InfoCollectEntryService {
    fun test(): String
    fun getData(area: String, productType: String): DetailBean
    fun getData2(area: String, productType: String): Observable<DetailBean>
}