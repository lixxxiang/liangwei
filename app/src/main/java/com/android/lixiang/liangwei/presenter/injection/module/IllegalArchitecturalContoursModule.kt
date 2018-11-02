package com.android.lixiang.liangwei.presenter.injection.module

import com.android.lixiang.liangwei.service.IllegalArchitecturalContoursService
import com.android.lixiang.liangwei.service.InfoCollectService
import com.android.lixiang.liangwei.service.LoginService
import com.android.lixiang.liangwei.service.RegisterService
import com.android.lixiang.liangwei.service.impl.IllegalArchitecturalContoursServiceImpl
import com.android.lixiang.liangwei.service.impl.InfoCollectServiceImpl
import com.android.lixiang.liangwei.service.impl.LoginServiceImpl
import com.android.lixiang.liangwei.service.impl.RegisterServiceImpl
import dagger.Module
import dagger.Provides
@Module
class IllegalArchitecturalContoursModule {

    @Provides
    fun provideIllegalArchitecturalContoursService(service: IllegalArchitecturalContoursServiceImpl): IllegalArchitecturalContoursService {

        return service
    }
}