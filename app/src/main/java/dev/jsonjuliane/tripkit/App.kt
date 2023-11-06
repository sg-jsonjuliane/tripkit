package dev.jsonjuliane.tripkit

import android.app.Application
import com.skedgo.tripkit.DateTimePickerConfig
import com.skedgo.tripkit.HttpClientModule
import com.skedgo.tripkit.TripKitConfigs
import com.skedgo.tripkit.configuration.Key
import com.skedgo.tripkit.ui.TripKitUI

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        val baseConfig = TripKitUI.buildTripKitConfig(
            applicationContext,
            Key.ApiKey(BuildConfig.SKEDGO_KEY)
        )

        val httpClientModule = HttpClientModule(
            null,
            BuildConfig.VERSION_NAME,
            baseConfig,
            null
        )

        val dateTimePickerConfig = DateTimePickerConfig(
            "Leave at",
            "Arrive By"
        )

        val appConfigs = TripKitConfigs.builder()
            .from(baseConfig)
            .dateTimePickerConfig(dateTimePickerConfig)
            .hasGetOffAlerts(false)
            .build()

        TripKitUI.initialize(
            this,
            Key.ApiKey(BuildConfig.SKEDGO_KEY),
            appConfigs,
            httpClientModule,
            getString(R.string.MAPS_KEY)
        )

    }
}