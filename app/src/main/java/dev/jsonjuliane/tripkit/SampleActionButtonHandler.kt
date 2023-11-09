package dev.jsonjuliane.tripkit

import android.content.Context
import android.widget.Toast
import com.skedgo.tripkit.routing.Trip
import com.skedgo.tripkit.ui.controller.ViewControllerEventBus
import com.skedgo.tripkit.ui.controller.utils.actionhandler.TKUIActionButtonHandler
import com.skedgo.tripkit.ui.tripresult.ActionButtonViewModel
import com.skedgo.tripkit.ui.tripresults.actionbutton.ActionButton
import javax.inject.Inject

/**
 * A sample implementation of a custom action handler for trip-related actions.
 *
 * @param eventBus The event bus for handling actions.
 */
open class SampleActionButtonHandler @Inject constructor(
    eventBus: ViewControllerEventBus
) : TKUIActionButtonHandler(eventBus) {

    /**
     * This method is responsible for finding a segment in a trip and launching its preview.
     *
     * @param trip The trip to be handled.
     * @param fromListOverviewAction Flag indicating if the action is from a list overview.
     */
    override fun findSegmentAndLaunchPreview(trip: Trip, fromListOverviewAction: Boolean) {
        // Implementation details go here.
    }

    /**
     * Retrieves a list of action buttons for a given trip.
     *
     * @param context The application context.
     * @param trip The trip for which actions are being retrieved.
     * @return A list of [ActionButton] instances representing available actions.
     */
    override suspend fun getActions(context: Context, trip: Trip): List<ActionButton> {
        val defaultAction = super.getActions(context, trip).toMutableList()
        defaultAction.add(
            ActionButton(
                "Custom Button",
                ACTION_TAG_CUSTOM_ACTION_1,
                com.skedgo.tripkit.ui.R.drawable.ic_action_warning,
                false,
            )
        )
        return defaultAction
    }

    /**
     * Handles the action when a button is clicked.
     *
     * @param context The application context.
     * @param tag The tag associated with the clicked action.
     * @param trip The trip to which the action is related.
     * @param viewModel The view model associated with the action button.
     */
    override fun actionClicked(
        context: Context,
        tag: String,
        trip: Trip,
        viewModel: ActionButtonViewModel
    ) {
        when (tag) {
            ACTION_TAG_FAVORITE -> favoriteCustomFunction(trip, viewModel)
            ACTION_TAG_CUSTOM_ACTION_1 -> {
                Toast.makeText(context, "Custom Button Clicked", Toast.LENGTH_LONG).show()
            }

            else -> super.actionClicked(context, tag, trip, viewModel)
        }
    }

    /**
     * A custom function to handle the "favorite" action.
     *
     * @param trip The trip to which the favorite action is related.
     * @param viewModel The view model associated with the action button.
     */
    private fun favoriteCustomFunction(trip: Trip, viewModel: ActionButtonViewModel) {
        // Implementation details go here.
    }

    companion object {
        const val ACTION_TAG_CUSTOM_ACTION_1 = "custom_action_1"
    }
}
