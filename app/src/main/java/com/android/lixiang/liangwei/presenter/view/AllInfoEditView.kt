package com.android.lixiang.liangwei.presenter.view

import com.android.lixiang.base.presenter.view.BaseView
import com.android.lixiang.liangwei.presenter.data.bean.*

interface AllInfoEditView : BaseView {
    fun returnSearch(searchBean: SearchBean)
    fun returnListExamInfo(listExamInfoBean: ListExamInfoBean)
    fun returnDeleteExamInfo(deleteExamInfoBean: DeleteExamInfoBean)
    fun returnDeleteIllegalInfo(deleteIllegalInfoBean: DeleteIllegalInfoBean)
}