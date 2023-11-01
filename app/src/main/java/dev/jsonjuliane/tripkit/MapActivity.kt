package dev.jsonjuliane.tripkit

import android.os.Bundle
import com.skedgo.tripkit.ui.core.BaseActivity
import com.skedgo.tripkit.ui.map.home.TripKitMapFragment
import dev.jsonjuliane.tripkit.databinding.ActivityMapBinding

class MapActivity : BaseActivity<ActivityMapBinding>() {
    override val layoutRes: Int
        get() = R.layout.activity_map

    override fun onCreated(instance: Bundle?) {
        // Intentionally left empty for the mean time
    }
}
