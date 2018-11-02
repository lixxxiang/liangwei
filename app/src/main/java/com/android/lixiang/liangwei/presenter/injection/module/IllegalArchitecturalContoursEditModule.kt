package com.android.lixiang.liangwei.presenter.injection.module

import com.android.lixiang.liangwei.service.IllegalArchitecturalContoursEditService
import com.android.lixiang.liangwei.service.InfoCollectEntryService
import com.android.lixiang.liangwei.service.impl.IllegalArchitecturalContoursEditServiceImpl
import com.android.lixiang.liangwei.service.impl.InfoCollectEntryServiceImpl

import dagger.Module
import dagger.Provides

@Module
class IllegalArchitecturalContoursEditModule {

    @Provides
    fun provideIllegalArchitecturalContoursEditService(service: IllegalArchitecturalContoursEditServiceImpl): IllegalArchitecturalContoursEditService {
        return service
    }
}
