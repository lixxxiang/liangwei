package com.android.lixiang.base.common

import android.app.Application
import android.os.Handler
import com.android.lixiang.base.injection.component.AppComponent
import com.android.lixiang.base.injection.component.DaggerAppComponent
import com.android.lixiang.base.injection.module.AppModule
import javax.inject.Inject
import android.os.Looper
import android.util.Log
import android.widget.Toast
import com.android.lixiang.base.R
import com.android.lixiang.base.crash.Cockroach
import com.android.lixiang.base.crash.ExceptionHandler


class BaseApplication: Application(){

    @Inject
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        initAppInjection()
        val sysExcepHandler = Thread.getDefaultUncaughtExceptionHandler()
        Cockroach.install(object : ExceptionHandler() {
            override fun onUncaughtExceptionHappened(thread: Thread, throwable: Throwable) {
                Log.e("AndroidRuntime", "--->onUncaughtExceptionHappened:$thread<---", throwable)
                Handler(Looper.getMainLooper()).post(Runnable {
                })
            }

            override fun onBandageExceptionHappened(throwable: Throwable) {
                throwable.printStackTrace()//打印警告级别log，该throwable可能是最开始的bug导致的，无需关心
            }

            override fun onEnterSafeMode() {
            }

            override fun onMayBeBlackScreen(e: Throwable) {
                val thread = Looper.getMainLooper().thread
                Log.e("AndroidRuntime", "--->onUncaughtExceptionHappened:$thread<---", e)
                //黑屏时建议直接杀死app
                sysExcepHandler.uncaughtException(thread, RuntimeException("black screen"))
            }
        })
    }

    private fun initAppInjection() {
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
    }
}