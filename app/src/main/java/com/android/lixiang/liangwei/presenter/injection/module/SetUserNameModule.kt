package com.android.lixiang.liangwei.presenter.injection.module

import com.android.lixiang.liangwei.service.InfoCollectService
import com.android.lixiang.liangwei.service.RegisterService
import com.android.lixiang.liangwei.service.SetUserNameService
import com.android.lixiang.liangwei.service.impl.InfoCollectServiceImpl
import com.android.lixiang.liangwei.service.impl.RegisterServiceImpl
import com.android.lixiang.liangwei.service.impl.SetUserNameServiceImpl
import dagger.Module
import dagger.Provides
@Module
class SetUserNameModule {

    @Provides
    fun provideSetUserNameService(service: SetUserNameServiceImpl): SetUserNameService {
        return service
    }
}