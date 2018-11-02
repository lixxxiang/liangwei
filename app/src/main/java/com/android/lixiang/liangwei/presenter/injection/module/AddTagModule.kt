package com.android.lixiang.liangwei.presenter.injection.module

import com.android.lixiang.liangwei.service.*
import com.android.lixiang.liangwei.service.impl.*
import dagger.Module
import dagger.Provides
@Module
class AddTagModule {

    @Provides
    fun provideAddTagService(service: AddTagServiceImpl): AddTagService {
        return service
    }
}