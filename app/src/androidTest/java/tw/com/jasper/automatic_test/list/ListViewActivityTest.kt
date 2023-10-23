package tw.com.jasper.automatic_test.list

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.DataInteraction
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import de.mannodermaus.junit5.ActivityScenarioExtension
import lab.kito.digital.test.R
import org.hamcrest.Matchers.hasEntry
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import tw.com.jasper.automatic_test.components.TimberExtension
import tw.com.jasper.automatic_test.list.DataInteractions.Companion.onRow
import tw.com.jasper.automatic_test.util.DataProvider.ROW_TEXT

@ExtendWith(TimberExtension::class)
// @ExtendWith(ActivityScenarioExtension::class)
internal class ListViewActivityTest {

    companion object {
        private const val TARGET_ITEM_INDEX = 80
        private const val TARGET_ITEM_TEXT = "item: $TARGET_ITEM_INDEX"
    }

    @JvmField
    @RegisterExtension
    val scenarioExtension: ActivityScenarioExtension<ListViewActivity> = ActivityScenarioExtension.launch()

    @Test
    @DisplayName("onData 操作listView")
    fun testOnData(activityScenario: ActivityScenario<ListViewActivity>) {
        onRow(TARGET_ITEM_TEXT).onChildView(withId(R.id.tg_btn)).perform(click())
        onRow(TARGET_ITEM_TEXT).onChildView(withText(TARGET_ITEM_TEXT)).check(matches(withText(TARGET_ITEM_TEXT)))
    }
}

class DataInteractions {
    companion object {
        fun onRow(text: String): DataInteraction = onData(hasEntry(`is`(ROW_TEXT), `is`(text)))
    }
}