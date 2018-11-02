package com.android.lixiang.liangwei.presenter.view

import com.android.lixiang.base.presenter.view.BaseView
import com.android.lixiang.liangwei.presenter.data.bean.*

interface InfoEditView : BaseView {
    fun test(string: String)
    fun returnIllegalInfo(illegalInfoBean: IllegalInfoBean)
    fun returnSelectIllegal(selectIllegalBean: SelectIllegalBean)
    fun returnUpdateIllegalInfo(updateIllegalInfoBean: UpdateIllegalInfoBean)
    fun returnUpload(uploadBean: UploadBean)
}