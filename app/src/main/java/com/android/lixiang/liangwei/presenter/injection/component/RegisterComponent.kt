package com.android.lixiang.liangwei.presenter.injection.component

import com.android.lixiang.base.injection.ComponentScope
import com.android.lixiang.base.injection.component.FragmentComponent
import com.android.lixiang.liangwei.presenter.injection.module.InfoCollectModule
import com.android.lixiang.liangwei.presenter.injection.module.RegisterModule
import com.android.lixiang.liangwei.ui.fragment.area1.bak.InfoCollectFragment
import com.android.lixiang.liangwei.ui.fragment.user.RegisterFragment
import dagger.Component

@ComponentScope
@Component(dependencies = arrayOf(FragmentComponent::class), modules = arrayOf(RegisterModule::class))
interface RegisterFragmentComponent {
    fun inject(fragment: RegisterFragment)
}