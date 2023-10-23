package tw.com.jasper.automatic_test.components

import android.view.MotionEvent
import android.view.View
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.GeneralLocation
import androidx.test.espresso.action.MotionEvents
import androidx.test.espresso.action.Press
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isDisplayingAtLeast
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf

abstract class ExcludeNullViewAction : ViewAction {
    abstract fun performNoneNullable(uiController: UiController, view: View)
    override fun perform(uiController: UiController?, view: View?) {
        if (uiController == null || view == null) {
            return
        }
        performNoneNullable(uiController, view)
    }
}

class RecyclerViewHolderActions {
    companion object {
        fun clickItemViewAt(viewId: Int, desc: String = ""): ViewAction = object : ExcludeNullViewAction() {
            override fun getConstraints(): Matcher<View> = allOf(isDisplayed())
            override fun getDescription(): String = desc
            override fun performNoneNullable(uiController: UiController, view: View) {
                view.findViewById<View>(viewId).performClick()
            }
        }
    }
}

class CommonViewActions {
    companion object {
        fun chottomatte(latencyDuration: Long, checkPeriod: Long = 100L): ViewAction {
            return object : ViewAction {
                override fun getConstraints(): Matcher<View> = isRoot()
                override fun getDescription(): String = "chottomatte $latencyDuration millis ~"
                override fun perform(uiController: UiController, rootView: View) {
                    uiController.loopMainThreadUntilIdle()
                    val startTime = System.currentTimeMillis()
                    val endTime = startTime + latencyDuration
                    do {
                        if (System.currentTimeMillis() > endTime) {
                            return
                        }
                        uiController.loopMainThreadForAtLeast(checkPeriod)
                    } while (System.currentTimeMillis() < endTime)
                }
            }
        }
    }
}

class KeepPressAndReleaseActions {
    companion object {
        private const val AT_LEAST_DISPLAYED_PERCENTAGE = 50
        private val CONSTRAINTS = allOf(isDisplayingAtLeast(AT_LEAST_DISPLAYED_PERCENTAGE))
        private lateinit var motionEvent: MotionEvent

        fun pressAndKeepAt(viewId: Int, desc: String = ""): ViewAction = object : ExcludeNullViewAction() {
            override fun getConstraints(): Matcher<View> = CONSTRAINTS
            override fun getDescription(): String = desc
            override fun performNoneNullable(uiController: UiController, view: View) {
                val describePrecision: FloatArray = Press.FINGER.describePrecision()
                val coordinates: FloatArray = GeneralLocation.CENTER.calculateCoordinates(view.findViewById(viewId))
                val downResultHolder: MotionEvents.DownResultHolder = MotionEvents.sendDown(uiController, coordinates, describePrecision)
                motionEvent = downResultHolder.down
            }
        }

        fun releaseAt(viewId: Int, desc: String = ""): ViewAction = object : ExcludeNullViewAction() {
            override fun getConstraints(): Matcher<View> = CONSTRAINTS
            override fun getDescription(): String = desc
            override fun performNoneNullable(uiController: UiController, view: View) {
                val coordinates = GeneralLocation.CENTER.calculateCoordinates(view.findViewById(viewId))
                MotionEvents.sendUp(uiController, motionEvent, coordinates)
            }
        }
    }
}
