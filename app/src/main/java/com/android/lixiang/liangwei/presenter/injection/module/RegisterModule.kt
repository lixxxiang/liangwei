package com.android.lixiang.liangwei.presenter.injection.module

import com.android.lixiang.liangwei.service.InfoCollectService
import com.android.lixiang.liangwei.service.RegisterService
import com.android.lixiang.liangwei.service.impl.InfoCollectServiceImpl
import com.android.lixiang.liangwei.service.impl.RegisterServiceImpl
import dagger.Module
import dagger.Provides
@Module
class RegisterModule {

    @Provides
    fun provideRegisterService(service: RegisterServiceImpl): RegisterService {
        return service
    }
}