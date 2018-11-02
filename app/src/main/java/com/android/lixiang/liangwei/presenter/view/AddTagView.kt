package com.android.lixiang.liangwei.presenter.view

import com.android.lixiang.base.presenter.view.BaseView
import com.android.lixiang.liangwei.presenter.data.bean.*

interface AddTagView : BaseView {
    fun returnUpdateLabel(updateLabelBean: UpdateLabelBean)
}