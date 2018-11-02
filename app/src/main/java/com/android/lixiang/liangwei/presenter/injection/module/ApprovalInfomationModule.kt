package com.android.lixiang.liangwei.presenter.injection.module

import com.android.lixiang.liangwei.service.*
import com.android.lixiang.liangwei.service.impl.*
import dagger.Module
import dagger.Provides
@Module
class ApprovalInfomationModule {

    @Provides
    fun provideApprovalInfomationService(service: ApprovalInfomationServiceImpl): ApprovalInfomationService {
        return service
    }
}