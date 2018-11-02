package com.android.lixiang.liangwei.presenter.injection.component

import com.android.lixiang.base.injection.ComponentScope
import com.android.lixiang.base.injection.component.FragmentComponent
import com.android.lixiang.liangwei.presenter.injection.module.ResetPasswordModule
import com.android.lixiang.liangwei.ui.fragment.user.SetPasswordFragment
import dagger.Component

@ComponentScope
@Component(dependencies = arrayOf(FragmentComponent::class), modules = arrayOf(ResetPasswordModule::class))
interface ResetPasswordFragmentComponent {
    fun inject(fragment: SetPasswordFragment)
}