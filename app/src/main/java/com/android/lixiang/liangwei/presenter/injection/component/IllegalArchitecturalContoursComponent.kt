package com.android.lixiang.liangwei.presenter.injection.component

import com.android.lixiang.base.injection.ComponentScope
import com.android.lixiang.base.injection.component.FragmentComponent
import com.android.lixiang.liangwei.presenter.injection.module.IllegalArchitecturalContoursModule
import com.android.lixiang.liangwei.presenter.injection.module.InfoCollectModule
import com.android.lixiang.liangwei.presenter.injection.module.LoginModule
import com.android.lixiang.liangwei.presenter.injection.module.RegisterModule
import com.android.lixiang.liangwei.ui.fragment.area1.IllegalArchitecturalContoursFragment
import com.android.lixiang.liangwei.ui.fragment.area1.bak.InfoCollectFragment
import com.android.lixiang.liangwei.ui.fragment.user.LoginFragment
import com.android.lixiang.liangwei.ui.fragment.user.RegisterFragment
import dagger.Component

@ComponentScope
@Component(dependencies = arrayOf(FragmentComponent::class), modules = arrayOf(IllegalArchitecturalContoursModule::class))
interface IllegalArchitecturalContoursFragmentComponent {
    fun inject(fragment: IllegalArchitecturalContoursFragment)
}