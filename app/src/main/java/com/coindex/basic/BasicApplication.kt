package com.coindex.basic

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.coindex.utils.LogUtils
import com.coindex.utils.LogUtils.logI
import com.coindex.utils.StorageUtils
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

open class BasicApplication : Application() {
    companion object {
        @JvmStatic
        lateinit var appContext: BasicApplication
            private set
    }

    override fun onCreate() {
        super.onCreate()
        appContext =this
        StorageUtils.init(this)
        LogUtils.init("coinDex").showLog(true)


        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                "activity=${activity.componentName.shortClassName} onActivityCreated".logI()
            }

            override fun onActivityStarted(activity: Activity) {
                "activity=${activity.componentName.shortClassName} onActivityStarted".logI()
            }

            override fun onActivityResumed(activity: Activity) {
               "activity=${activity.componentName.shortClassName} onActivityResumed".logI()
            }

            override fun onActivityPaused(activity: Activity) {
                "activity=${activity.componentName.shortClassName} onActivityPaused".logI()
            }

            override fun onActivityStopped(activity: Activity) {
                "activity=${activity.componentName.shortClassName} onActivityStopped".logI()
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                "activity=${activity.componentName.shortClassName} onActivitySaveInstanceState".logI()
            }

            override fun onActivityDestroyed(activity: Activity) {
                "activity=${activity.componentName.shortClassName} onActivityDestroyed".logI()
            }
        })

    }
}