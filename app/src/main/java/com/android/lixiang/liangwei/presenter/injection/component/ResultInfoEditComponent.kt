package com.android.lixiang.liangwei.presenter.injection.component

import com.android.lixiang.base.injection.ComponentScope
import com.android.lixiang.base.injection.component.FragmentComponent
import com.android.lixiang.liangwei.presenter.injection.module.AllInfoModule
import com.android.lixiang.liangwei.presenter.injection.module.ResultInfoEditModule
import com.android.lixiang.liangwei.ui.fragment.area1.AllInfoFragment
import com.android.lixiang.liangwei.ui.fragment.area1.ResultInfoFragment
import com.android.lixiang.liangwei.ui.fragment.area2.ResultInfoEditFragment
import dagger.Component

@ComponentScope
@Component(dependencies = arrayOf(FragmentComponent::class), modules = arrayOf(ResultInfoEditModule::class))
interface ResultInfoEditFragmentComponent {
    fun inject(fragment: ResultInfoEditFragment)
}