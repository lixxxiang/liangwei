package com.android.lixiang.liangwei.presenter.injection.module

import com.android.lixiang.liangwei.service.ResetPasswordService
import com.android.lixiang.liangwei.service.impl.ResetPasswordServiceImpl
import dagger.Module
import dagger.Provides
@Module
class ResetPasswordModule {

    @Provides
    fun provideInfoCollectService(service: ResetPasswordServiceImpl): ResetPasswordService {
        return service
    }
}