package com.android.lixiang.liangwei.presenter.injection.module

import com.android.lixiang.liangwei.service.InfoCollectEntryService
import com.android.lixiang.liangwei.service.impl.InfoCollectEntryServiceImpl

import dagger.Module
import dagger.Provides

@Module
class InfoCollectEntryModule {

    @Provides
    fun provideInfoCollectEntryService(service: InfoCollectEntryServiceImpl): InfoCollectEntryService{
        return service
    }
}
