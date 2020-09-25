package land.moka.kmm.androidApp

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import land.moka.androidApp.BuildConfig
import moka.land.base.DEBUG

@SuppressLint("StaticFieldLeak")
class _Application : Application() {

    companion object {
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        DEBUG = BuildConfig.DEBUG
    }

}
