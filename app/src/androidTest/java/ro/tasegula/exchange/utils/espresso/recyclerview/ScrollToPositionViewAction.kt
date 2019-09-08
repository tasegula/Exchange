package ro.tasegula.exchange.utils.espresso.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.matcher.ViewMatchers
import org.hamcrest.Matcher
import org.hamcrest.Matchers

class ScrollToPositionViewAction(private val position: Int) : ViewAction {

    override fun getConstraints(): Matcher<View> {
        return Matchers.allOf(ViewMatchers.isAssignableFrom(RecyclerView::class.java),
                ViewMatchers.isDisplayed())
    }

    override fun getDescription(): String {
        return "scroll RecyclerView to position: " + this.position
    }

    override fun perform(uiController: UiController?, view: View?) {
        val recyclerView = view as RecyclerView?
        recyclerView?.scrollToPosition(this.position)
    }
}
