package ro.tasegula.exchange.utils.espresso.recyclerview

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.PerformException
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.util.HumanReadables
import org.hamcrest.Matcher
import org.hamcrest.Matchers

fun ViewInteraction.performOnItemViewAtPosition(position: Int,
                                                @IdRes viewId: Int,
                                                viewAction: ViewAction) {
    perform(actionOnItemViewAtPosition(position, viewId, viewAction))
}

fun actionOnItemViewAtPosition(position: Int,
                               @IdRes viewId: Int,
                               viewAction: ViewAction): ViewAction {
    return ActionOnItemViewAtPositionViewAction(position, viewAction, viewId)
}

private class ActionOnItemViewAtPositionViewAction(
    private val position: Int = 0,
    private val viewAction: ViewAction,
    @IdRes private val viewId: Int = 0) : ViewAction {

    override fun getDescription(): String {
        return "actionOnItemAtPosition performing ViewAction: ${viewAction.description} on item at position: $position"
    }

    override fun getConstraints(): Matcher<View> {
        return Matchers.allOf(ViewMatchers.isAssignableFrom(RecyclerView::class.java),
                              ViewMatchers.isDisplayed())
    }

    override fun perform(uiController: UiController, view: View) {
        val recyclerView = view as RecyclerView
        ScrollToPositionViewAction(position).perform(uiController, view)
        uiController.loopMainThreadUntilIdle()

        val itemView: View? = recyclerView.layoutManager?.findViewByPosition(position)
        val targetView: View = itemView?.findViewById(viewId)
                ?: throw PerformException.Builder().withActionDescription(this.toString())
                    .withViewDescription(HumanReadables.describe(view))
                    .withCause(IllegalStateException("No view with id $viewId found at position: $position"))
                    .build()

        viewAction.perform(uiController, targetView)
    }
}
