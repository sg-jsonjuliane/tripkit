package dev.jsonjuliane.tripkit

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.skedgo.geocoding.LatLng
import com.skedgo.tripkit.common.model.Location
import com.skedgo.tripkit.ui.controller.ViewControllerEventBus
import com.skedgo.tripkit.ui.controller.homeviewcontroller.TKUIHomeViewControllerFragment
import com.skedgo.tripkit.ui.controller.utils.actionhandler.TKUIActionButtonHandlerFactory
import dev.jsonjuliane.tripkit.databinding.ActivityHomeViewControllerBinding

/**
 * The main activity representing the home view controller of the application.
 */
class HomeViewControllerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeViewControllerBinding

    // Instantiate a sample favorite suggestion provider.
    private val favoriteProvider = SampleFavoritesSuggestionProvider()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize data binding for the activity layout.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home_view_controller)

        // Create an instance of the SampleActionButtonHandler and its factory.
        val actionButtonHandler = SampleActionButtonHandler(ViewControllerEventBus)
        val actionButtonHandlerFactory = TKUIActionButtonHandlerFactory { actionButtonHandler }

        // Add sample favorite data to the provider.
        addSampleFavoriteData()

        // Load the TKUIHomeViewControllerFragment.
        val homeControllerFragment = TKUIHomeViewControllerFragment
            .load(
                activity = this@HomeViewControllerActivity,
                containerId = R.id.homeFragment,
                defaultLocation = LatLng(-27.470125, 153.021072),
                actionButtonHandlerFactory = actionButtonHandlerFactory,
                favoriteSuggestionProvider = favoriteProvider,
                showMyLocationButtonWithoutPermission = false,
                bottomSheetVisibilityCallback = {
                    if (it == 0) {
                        binding.etSearch.visibility = View.VISIBLE
                    } else {
                        binding.etSearch.visibility = View.GONE
                    }
                },
                onBackPressOnEmptyBottomSheetCallback = {
                    it.remove()
                    this@HomeViewControllerActivity.finish()
                }
            )

        // Handle the search button click to initiate a location search.
        binding.etSearch.setOnClickListener {
            homeControllerFragment.loadSearchCardFragment()
        }
    }

    /**
     * Adds sample favorite data to the favorite provider.
     */
    private fun addSampleFavoriteData() {
        favoriteProvider.saveFavorite(
            Location(-27.4822985, 152.9859037),
            0,
            SampleFavoritesSuggestionProvider.SampleAdditionalData(
                "Sample Favorite 1", "Favorite's Address 1"
            )
        )

        favoriteProvider.saveFavorite(
            Location(-27.4744549, 152.9889291),
            1,
            SampleFavoritesSuggestionProvider.SampleAdditionalData(
                "Sample Favorite 2", "Favorite's Address 2"
            )
        )

        favoriteProvider.saveFavorite(
            Location(-27.4750639, 152.9844771),
            1,
            SampleFavoritesSuggestionProvider.SampleAdditionalData(
                "Sample Favorite 3", "Favorite's Address 3"
            )
        )

        favoriteProvider.saveHome(
            Location(-27.3816409, 153.0118921),
            SampleFavoritesSuggestionProvider.SampleAdditionalData(
                "HOME", "Home Address"
            )
        )
    }
}
