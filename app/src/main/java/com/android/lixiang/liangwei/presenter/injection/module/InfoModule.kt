package com.android.lixiang.liangwei.presenter.injection.module

import com.android.lixiang.liangwei.service.InfoCollectService
import com.android.lixiang.liangwei.service.InfoEditService
import com.android.lixiang.liangwei.service.InfoService
import com.android.lixiang.liangwei.service.RegisterService
import com.android.lixiang.liangwei.service.impl.InfoCollectServiceImpl
import com.android.lixiang.liangwei.service.impl.InfoEditServiceImpl
import com.android.lixiang.liangwei.service.impl.InfoServiceImpl
import com.android.lixiang.liangwei.service.impl.RegisterServiceImpl
import dagger.Module
import dagger.Provides
@Module
class InfoModule {

    @Provides
    fun provideInfoService(service: InfoServiceImpl): InfoService {
        return service
    }
}