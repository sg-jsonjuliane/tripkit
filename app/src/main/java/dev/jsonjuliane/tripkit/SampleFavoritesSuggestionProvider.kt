package dev.jsonjuliane.tripkit

import android.content.Context
import androidx.core.content.ContextCompat
import com.skedgo.tripkit.common.model.Location
import com.skedgo.tripkit.ui.controller.locationsearchcontroller.TKUIFavoritesSuggestionProvider
import com.skedgo.tripkit.ui.search.DefaultSearchSuggestion
import com.skedgo.tripkit.ui.search.LocationSearchIconProvider
import com.skedgo.tripkit.ui.search.SearchSuggestion

/**
 * A sample implementation of a favorite suggestion provider for location search.
 */
class SampleFavoritesSuggestionProvider : TKUIFavoritesSuggestionProvider() {

    // List to store sample favorite data.
    private val sampleFavorites = mutableListOf<SampleFavoriteData>()
    private var homeFavorite: SampleFavoriteData? = null
    private var workFavorite: SampleFavoriteData? = null

    /**
     * Handles the onClick event for a search suggestion.
     *
     * @param id The identifier of the clicked suggestion.
     */
    override suspend fun onClick(id: Any) {
        super.onClick(id)
    }

    /**
     * Provides search suggestions based on a query.
     *
     * @param context The application context.
     * @param iconProvider The icon provider for search results.
     * @param query The search query.
     * @return A list of search suggestions.
     */
    override suspend fun query(
        context: Context,
        iconProvider: LocationSearchIconProvider,
        query: String
    ): List<SearchSuggestion> {
        val resultType = LocationSearchIconProvider.SearchResultType.FAVORITE
        var drawableId = iconProvider.iconForSearchResult(resultType, null)
        if (drawableId == 0) {
            drawableId = com.skedgo.tripkit.ui.R.drawable.ic_favorite
        }

        val result = mutableListOf<DefaultSearchSuggestion>()

        sampleFavorites.forEach {
            it.location.isFavourite(true)
            with((it.additionalData as SampleAdditionalData)) {
                result.add(
                    DefaultSearchSuggestion(
                        it.id,
                        this.title,
                        this.address,
                        R.color.black,
                        com.skedgo.tripkit.ui.R.color.black1,
                        ContextCompat.getDrawable(context, drawableId)!!,
                        it.location
                    )
                )
            }
        }

        return result
    }

    /**
     * Saves the user's home location as a favorite.
     *
     * @param location The home location.
     * @param additionalData Additional data associated with the location.
     */
    override fun saveHome(location: Location, additionalData: Any?) {
        location.address = (additionalData as SampleAdditionalData).address

        homeFavorite = SampleFavoriteData(
            HOME_ID,
            location,
            additionalData
        )
    }

    /**
     * Retrieves the user's home location.
     *
     * @return The user's home location.
     */
    override fun getHome(): Location? {
        return homeFavorite?.run {
            location.id = id.toLong()
            location.address = (additionalData as SampleAdditionalData).address
            location
        }
    }

    /**
     * Saves the user's work location as a favorite.
     *
     * @param location The work location.
     * @param additionalData Additional data associated with the location.
     */
    override fun saveWork(location: Location, additionalData: Any?) {
        location.address = (additionalData as SampleAdditionalData).address

        workFavorite = SampleFavoriteData(
            WORK_ID,
            location,
            additionalData
        )
    }

    /**
     * Retrieves the user's work location.
     *
     * @return The user's work location.
     */
    override fun getWork(): Location? {
        return workFavorite?.run {
            location.id = id.toLong()
            location.address = (additionalData as SampleAdditionalData).address
            location
        }
    }

    /**
     * Retrieves a favorite location based on its identifier.
     *
     * @param id The identifier of the favorite location.
     * @return The favorite location.
     */
    override fun getFavorite(id: Any): Location? {
        return sampleFavorites.firstOrNull { it.id == id }?.run {
            location.id = this.id.toLong()
            location.address = (additionalData as SampleAdditionalData).address
            location
        }
    }

    /**
     * Saves a favorite location.
     *
     * @param location The favorite location.
     * @param id The identifier of the favorite.
     * @param additionalData Additional data associated with the location.
     */
    override fun saveFavorite(location: Location, id: Any, additionalData: Any?) {
        location.address = (additionalData as SampleAdditionalData).address

        sampleFavorites.add(
            SampleFavoriteData(
                id as Int,
                location,
                additionalData
            )
        )
    }

    /**
     * Removes a favorite location based on its identifier.
     *
     * @param id The identifier of the favorite location to be removed.
     */
    override fun removeFavorite(id: Any) {
        sampleFavorites.removeAll { it.id == id as Int }
    }

    data class SampleAdditionalData(
        val title: String,
        val address: String = ""
    )

    data class SampleFavoriteData(
        val id: Int,
        val location: Location,
        val additionalData: Any?
    )

    companion object {
        const val HOME_ID = 1001
        const val WORK_ID = 1002
    }
}
