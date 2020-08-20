package com.example.elgaml.leakcanary

import android.app.Application
import com.squareup.leakcanary.LeakCanary

class MyApplication : Application()
{

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }


}