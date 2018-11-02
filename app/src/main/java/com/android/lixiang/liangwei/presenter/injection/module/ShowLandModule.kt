package com.android.lixiang.liangwei.presenter.injection.module

import com.android.lixiang.liangwei.service.*
import com.android.lixiang.liangwei.service.impl.*
import dagger.Module
import dagger.Provides
@Module
class ShowLandModule {

    @Provides
    fun provideShowLandService(service: ShowLandServiceImpl): ShowLandService {
        return service
    }
}