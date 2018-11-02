package com.android.lixiang.liangwei.presenter.injection.module

import com.android.lixiang.liangwei.service.InfoCollectService
import com.android.lixiang.liangwei.service.InfoEditService
import com.android.lixiang.liangwei.service.RegisterService
import com.android.lixiang.liangwei.service.impl.InfoCollectServiceImpl
import com.android.lixiang.liangwei.service.impl.InfoEditServiceImpl
import com.android.lixiang.liangwei.service.impl.RegisterServiceImpl
import dagger.Module
import dagger.Provides
@Module
class InfoEditModule {

    @Provides
    fun provideInfoEditService(service: InfoEditServiceImpl): InfoEditService {
        return service
    }
}