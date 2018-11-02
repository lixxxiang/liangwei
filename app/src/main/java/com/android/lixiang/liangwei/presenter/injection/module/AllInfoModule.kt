package com.android.lixiang.liangwei.presenter.injection.module

import com.android.lixiang.liangwei.service.AllInfoService
import com.android.lixiang.liangwei.service.InfoCollectService
import com.android.lixiang.liangwei.service.LoginService
import com.android.lixiang.liangwei.service.RegisterService
import com.android.lixiang.liangwei.service.impl.AllInfoServiceImpl
import com.android.lixiang.liangwei.service.impl.InfoCollectServiceImpl
import com.android.lixiang.liangwei.service.impl.LoginServiceImpl
import com.android.lixiang.liangwei.service.impl.RegisterServiceImpl
import dagger.Module
import dagger.Provides
@Module
class AllInfoModule {

    @Provides
    fun provideAllInfoService(service: AllInfoServiceImpl): AllInfoService {
        return service
    }
}