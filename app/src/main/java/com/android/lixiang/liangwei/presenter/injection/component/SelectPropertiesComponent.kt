package com.android.lixiang.liangwei.presenter.injection.component

import com.android.lixiang.base.injection.ComponentScope
import com.android.lixiang.base.injection.component.FragmentComponent
import com.android.lixiang.liangwei.presenter.injection.module.AddTagModule
import com.android.lixiang.liangwei.presenter.injection.module.AllInfoModule
import com.android.lixiang.liangwei.presenter.injection.module.SelectPropertiesModule
import com.android.lixiang.liangwei.ui.fragment.area1.AllInfoFragment
import com.android.lixiang.liangwei.ui.fragment.area2.AddTagFragment
import com.android.lixiang.liangwei.ui.fragment.area2.SelectPropertiesFragment
import dagger.Component

@ComponentScope
@Component(dependencies = arrayOf(FragmentComponent::class), modules = arrayOf(SelectPropertiesModule::class))
interface SelectPropertiesFragmentComponent {
    fun inject(fragment: SelectPropertiesFragment)
}