package tw.com.jasper.automatic_test.list

import android.widget.TextView
import android.widget.ToggleButton
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.contrib.RecyclerViewActions.scrollTo
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isChecked
import androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import de.mannodermaus.junit5.ActivityScenarioExtension
import lab.kito.digital.test.R
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.api.extension.RegisterExtension
import tw.com.jasper.automatic_test.components.BaseTest
import tw.com.jasper.automatic_test.components.CommonViewActions.Companion.chottomatte
import tw.com.jasper.automatic_test.components.KeepPressAndReleaseActions.Companion.pressAndKeepAt
import tw.com.jasper.automatic_test.components.KeepPressAndReleaseActions.Companion.releaseAt
import tw.com.jasper.automatic_test.components.RecyclerViewHolderActions.Companion.clickItemViewAt
import tw.com.jasper.automatic_test.components.RecyclerViewMatchers.Companion.atPosition
import tw.com.jasper.automatic_test.components.RecyclerViewMatchers.Companion.isItemViewCheckedAt
import tw.com.jasper.automatic_test.components.RecyclerViewMatchers.Companion.isItemViewWithTextAt
import tw.com.jasper.automatic_test.components.TimberExtension

@ExtendWith(TimberExtension::class)
internal class RecyclerViewActivityTest : BaseTest() {

    companion object {
        private const val TARGET_ITEM_INDEX = 80
        private const val TARGET_ITEM_TEXT = "item: $TARGET_ITEM_INDEX"
    }

    @JvmField
    @RegisterExtension
    val scenarioExtension: ActivityScenarioExtension<RecyclerViewActivity> = ActivityScenarioExtension.launch()

    @Test
    @DisplayName("點擊item中的ToggleButton然後驗證狀態是否為checked")
    fun testScrollToAndClickSpecifiedView(activityScenario: ActivityScenario<RecyclerViewActivity>) {
        onView(withId(R.id.rv_list)).perform(
            actionOnItemAtPosition<RecyclerViewAdapter.ViewHolder>(
                TARGET_ITEM_INDEX, clickItemViewAt(R.id.tg_btn, "點擊ViewHolder中 id為 ${R.id.tg_btn.toResEntryName()} 的view")
            )
        )
        onView(withId(R.id.rv_list)).check(matches(atPosition(TARGET_ITEM_INDEX, isItemViewCheckedAt<ToggleButton>(R.id.tg_btn, isChecked()))))
    }

    @Test
    @DisplayName("捲到第80筆 看文字內容是否真的是第80筆 use \"scrollTo<ViewHolder>\"")
    fun testScrollTo(activityScenario: ActivityScenario<RecyclerViewActivity>) {
        onView(withId(R.id.rv_list)).perform(
            scrollTo<RecyclerViewAdapter.ViewHolder>(
                hasDescendant(withText(TARGET_ITEM_TEXT))
            )
        )
        onView(withId(R.id.rv_list)).check(matches(atPosition(TARGET_ITEM_INDEX, isDisplayed())))
        onView(withId(R.id.rv_list)).check(matches(atPosition(TARGET_ITEM_INDEX, isItemViewWithTextAt<TextView>(R.id.tv_content, withText(TARGET_ITEM_TEXT)))))
        // 直接看畫面
        onView(withText(TARGET_ITEM_TEXT)).check(matches(isDisplayed()))
        onView(withText(TARGET_ITEM_TEXT)).check(matches(isCompletelyDisplayed()))
    }

    @Test
    @DisplayName("捲到第80筆 看文字內容是否真的是第80筆 use \"scrollToHolder\"")
    fun test() {
        // onView(withId(R.id.rv_list)).perform(
        //     scrollToHolder<ViewHolder>(
        //        // isViewHolderWithTextAt<TextView>(R.id.tv_content, withText(TARGET_ITEM_TEXT))
        //        // hasDescendant(withText(TARGET_ITEM_TEXT))
        //     )
        // )
        // 直接看畫面
        onView(withText(TARGET_ITEM_TEXT)).check(matches(isDisplayed()))
        onView(withText(TARGET_ITEM_TEXT)).check(matches(isCompletelyDisplayed()))
    }

    @Test
    @DisplayName("測試長按不放一個button")
    fun testKeepPressButton() {
        onView(withId(R.id.rv_list)).perform(actionOnItemAtPosition<RecyclerViewAdapter.ViewHolder>(0, pressAndKeepAt(R.id.tg_btn)))
        onView(isRoot()).perform(chottomatte(2000))
        onView(withId(R.id.rv_list)).perform(actionOnItemAtPosition<RecyclerViewAdapter.ViewHolder>(0, releaseAt(R.id.tg_btn)))
    }
}
