package com.android.lixiang.liangwei.presenter.injection.component

import com.android.lixiang.base.injection.ComponentScope
import com.android.lixiang.base.injection.component.FragmentComponent
import com.android.lixiang.liangwei.presenter.injection.module.InfoCollectModule
import com.android.lixiang.liangwei.ui.fragment.area1.bak.InfoCollectFragment
import dagger.Component

@ComponentScope
@Component(dependencies = arrayOf(FragmentComponent::class), modules = arrayOf(InfoCollectModule::class))
interface InfoCollectFragmentComponent {
    fun inject(fragment: InfoCollectFragment)
}