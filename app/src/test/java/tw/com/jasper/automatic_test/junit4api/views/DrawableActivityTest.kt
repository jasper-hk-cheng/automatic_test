package tw.com.jasper.automatic_test.junit4api.views

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.StateListDrawable
import androidx.test.ext.junit.rules.ActivityScenarioRule
import lab.kito.digital.test.databinding.ActivityDrawableBinding
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import timber.log.Timber
import tw.com.jasper.automatic_test.junit4api.utils.RobolectricApp
import tw.com.jasper.automatic_test.junit4api.utils.TimberTestRule
import tw.com.jasper.automatic_test.style.DrawableActivity

@RunWith(RobolectricTestRunner::class)
@Config(application = RobolectricApp::class)
class DrawableActivityTest {

    @get:Rule(order = 0)
    val timberTestRule = TimberTestRule()

    @get:Rule(order = 1)
    val activityScenarioRule = ActivityScenarioRule(DrawableActivity::class.java)

    @Test
    fun test() {
        activityScenarioRule.scenario.onActivity { activity ->
            val binding = ActivityDrawableBinding.inflate(activity.layoutInflater).apply {
                activity.setContentView(this.root)
            }
            with(binding) {
                // color drawable
                val pureColorDrawable = tvBgPureColor.background as ColorDrawable
                Timber.i("pureColorDrawable.color: ${pureColorDrawable.color}")
                // state list drawable
                val stateListDrawable = tvBgSelector.background as StateListDrawable
                val currentColorDrawable = stateListDrawable.current as ColorDrawable
                Timber.i("currentColorDrawable.color: ${currentColorDrawable.color}")
                Timber.i("stateListDrawable.stateCount: ${stateListDrawable.stateCount}")
                stateListDrawable.state.forEachIndexed { index: Int, state: Int ->
                    val resEntryName = activity.resources.getResourceEntryName(state)
                    Timber.i("state: $index - $state - $resEntryName")
                }
                val colorDrawableAt0 = stateListDrawable.getStateDrawable(0) as ColorDrawable
                Timber.i("colorDrawableAt0 color: ${colorDrawableAt0.color}")
                val colorDrawableAt1 = stateListDrawable.getStateDrawable(1) as ColorDrawable
                Timber.i("colorDrawableAt1 color: ${colorDrawableAt1.color}")
            }
        }
    }
}








