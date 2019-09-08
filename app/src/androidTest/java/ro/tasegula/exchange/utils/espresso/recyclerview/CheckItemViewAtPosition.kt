package ro.tasegula.exchange.utils.espresso.recyclerview

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.matcher.ViewMatchers

fun ViewInteraction.checkItemViewAtPosition(position: Int,
                                            @IdRes viewId: Int,
                                            viewAssertion: ViewAssertion) {
    perform(ScrollToPositionViewAction(position))
            .check(assertionOnItemViewAtPosition(position, viewId, viewAssertion))
}

fun assertionOnItemViewAtPosition(position: Int,
                                  @IdRes viewId: Int,
                                  viewAssertion: ViewAssertion): ViewAssertion {
    return AssertionOnItemViewAtPositionViewAssertion(position, viewAssertion, viewId)
}

private class AssertionOnItemViewAtPositionViewAssertion(private val position: Int = 0,
                                                         private val viewAssertion: ViewAssertion,
                                                         @IdRes private val viewId: Int = 0) : ViewAssertion {
    override fun check(view: View?, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }

        // perform assertion
        val recyclerView = view as RecyclerView

        val itemView: View? = recyclerView.layoutManager?.findViewByPosition(position)
        val targetView: View? = itemView?.findViewById(viewId)

        val itemViewNotFoundException =
                if (targetView == null)
                    NoMatchingViewException.Builder()
                            .withRootView(view)
                            .withViewMatcher(ViewMatchers.withId(viewId))
                            .withCause(IllegalStateException("No view with id $viewId found at position: $position"))
                            .build()
                else null

        viewAssertion.check(targetView, itemViewNotFoundException)
    }
}
