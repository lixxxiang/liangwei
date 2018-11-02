package com.android.lixiang.liangwei.presenter.injection.component

import com.android.lixiang.base.injection.ComponentScope
import com.android.lixiang.base.injection.component.FragmentComponent
import com.android.lixiang.liangwei.presenter.injection.module.AllInfoModule
import com.android.lixiang.liangwei.ui.fragment.area1.AllInfoFragment
import dagger.Component

@ComponentScope
@Component(dependencies = arrayOf(FragmentComponent::class), modules = arrayOf(AllInfoModule::class))
interface AllInfoFragmentComponent {
    fun inject(fragment: AllInfoFragment)
}