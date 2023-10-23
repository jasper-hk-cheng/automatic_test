package tw.com.jasper.automatic_test.bottomSheetDialog

import android.view.View
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource
import com.google.android.material.bottomsheet.BottomSheetBehavior

object IdlingResources {
    // 用 CountingIdlingResource
    val countingIdlingResource = CountingIdlingResource("test CountingIdlingResource")
    // 用 BottomSheetBehavior
    // TODO
}

abstract class BottomSheetCheckIdlingResource(
    private val bottomSheetBehavior: BottomSheetBehavior<View>
) : IdlingResource, BottomSheetBehavior.BottomSheetCallback() {

    private var isIdle: Boolean = false
    private var resourceCallback: IdlingResource.ResourceCallback? = null

    override fun onSlide(bottomSheet: View, slideOffset: Float) {}

    override fun onStateChanged(bottomSheet: View, newState: Int) {
        val wasIdle = isIdle
        isIdle = isDesiredState(newState)
        if (!wasIdle && isIdle) {
            bottomSheetBehavior.removeBottomSheetCallback(this)
            resourceCallback?.onTransitionToIdle()
        }
    }

    override fun isIdleNow(): Boolean {
        return isIdle
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        resourceCallback = callback

        val state = bottomSheetBehavior.state
        isIdle = isDesiredState(state)
        if (isIdle) {
            resourceCallback?.onTransitionToIdle()
        } else {
            bottomSheetBehavior.addBottomSheetCallback(this)
        }
    }

    abstract fun isDesiredState(@BottomSheetBehavior.State state: Int): Boolean
}

class MyBottomSheetCheckIdlingResource(
    bottomSheetBehavior: BottomSheetBehavior<View>,
    @BottomSheetBehavior.State private val desiredState: Int,
) : BottomSheetCheckIdlingResource(bottomSheetBehavior) {

    override fun getName(): String {
        return "BottomSheet awaiting state: $desiredState"
    }

    override fun isDesiredState(@BottomSheetBehavior.State state: Int): Boolean {
        return state == desiredState
    }
}