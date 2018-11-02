package com.android.lixiang.liangwei.presenter.injection.module

import com.android.lixiang.liangwei.service.InfoCollectService
import com.android.lixiang.liangwei.service.LoginService
import com.android.lixiang.liangwei.service.RegisterService
import com.android.lixiang.liangwei.service.impl.InfoCollectServiceImpl
import com.android.lixiang.liangwei.service.impl.LoginServiceImpl
import com.android.lixiang.liangwei.service.impl.RegisterServiceImpl
import dagger.Module
import dagger.Provides
@Module
class LoginModule {

    @Provides
    fun provideLoginService(service: LoginServiceImpl): LoginService {
        return service
    }
}