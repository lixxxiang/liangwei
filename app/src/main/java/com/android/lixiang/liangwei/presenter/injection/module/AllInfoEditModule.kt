package com.android.lixiang.liangwei.presenter.injection.module

import com.android.lixiang.liangwei.service.*
import com.android.lixiang.liangwei.service.impl.*
import dagger.Module
import dagger.Provides
@Module
class AllInfoEditModule {

    @Provides
    fun provideAllInfoEditService(service: AllInfoEditServiceImpl): AllInfoEditService {
        return service
    }
}