package tw.com.jasper.automatic_test.junit4api.utils

import android.app.Application

class RobolectricApp : Application() {

    companion object {
        private lateinit var instance: RobolectricApp
        fun getInstance(): RobolectricApp = instance
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}