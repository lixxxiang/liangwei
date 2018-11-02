package com.android.lixiang.liangwei.presenter.injection.component

import com.android.lixiang.base.injection.ComponentScope
import com.android.lixiang.base.injection.component.FragmentComponent
import com.android.lixiang.liangwei.presenter.injection.module.ApprovalInfomationEditModule
import com.android.lixiang.liangwei.presenter.injection.module.ApprovalInfomationModule
import com.android.lixiang.liangwei.presenter.injection.module.InfoCollectModule
import com.android.lixiang.liangwei.presenter.injection.module.RegisterModule
import com.android.lixiang.liangwei.ui.fragment.area1.bak.InfoCollectFragment
import com.android.lixiang.liangwei.ui.fragment.area2.ApprovalInformationEditFragment
import com.android.lixiang.liangwei.ui.fragment.area2.ApprovalInformationFragment
import com.android.lixiang.liangwei.ui.fragment.user.RegisterFragment
import dagger.Component

@ComponentScope
@Component(dependencies = arrayOf(FragmentComponent::class), modules = arrayOf(ApprovalInfomationModule::class))
interface ApprovalInfomationFragmentComponent {
    fun inject(fragment: ApprovalInformationFragment)
}