package dev.jsonjuliane.tripkit

import android.app.Application
import com.skedgo.DaggerTripKit
import com.skedgo.TripKit
import com.skedgo.tripkit.HttpClientModule
import com.skedgo.tripkit.MainModule
import com.skedgo.tripkit.TripKitConfigs
import com.skedgo.tripkit.configuration.Key

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        val configs = TripKitConfigs.builder().context(this)
            .debuggable(true)
            .key { Key.ApiKey(BuildConfig.SKEDGO_KEY) }
            .build()

        val httpClientModule = HttpClientModule(null, null, configs)

        val tripKit = DaggerTripKit.builder()
            .mainModule(MainModule(configs))
            .httpClientModule(httpClientModule)
            .build()

        TripKit.initialize(this, tripKit)
    }
}