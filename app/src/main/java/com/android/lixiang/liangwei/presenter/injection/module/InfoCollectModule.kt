package com.android.lixiang.liangwei.presenter.injection.module

import com.android.lixiang.liangwei.service.InfoCollectService
import com.android.lixiang.liangwei.service.impl.InfoCollectServiceImpl
import dagger.Module
import dagger.Provides
@Module
class InfoCollectModule {

    @Provides
    fun provideInfoCollectService(service: InfoCollectServiceImpl): InfoCollectService {
        return service
    }
}