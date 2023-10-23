package tw.com.jasper.automatic_test.junit4api.robolectric

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey
import androidx.test.espresso.intent.rule.IntentsRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import lab.kito.digital.test.R
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf
import org.robolectric.shadows.ShadowDialog
import tw.com.jasper.automatic_test.robolectric.RobolectricRegisterActivity
import tw.com.jasper.automatic_test.robolectric.RobolectricResultActivity

@RunWith(RobolectricTestRunner::class)
class TestShadowActivityAndDialog {

    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(RobolectricRegisterActivity::class.java)

    @get:Rule
    val intentsRule = IntentsRule()

    @Test
    fun registerSuccessShouldDirectToResult() {
        activityScenarioRule.scenario.use { scenario ->
            scenario.onActivity { activity ->
                val shadowActivity = shadowOf(activity)
                // given
                val loginId = "A123456789"
                val password = "a123456789"
                // when
                onView(withId(R.id.loginId)).perform(typeText(loginId))
                onView(withId(R.id.password)).perform(typeText(password))
                onView(withId(R.id.send)).perform(click())
                // then
                val recentIntent = shadowActivity.nextStartedActivity
                intended(hasComponent(RobolectricResultActivity::class.java.name))
                intended(hasExtraWithKey("ID"))
                Assertions.assertEquals(1, recentIntent.extras?.size())
            }
        }
    }

    @Test
    fun registerFailShouldAlert() {
        // given
        val loginId = "A1234"
        val password = "a123456789"
        // when
        onView(withId(R.id.loginId)).perform(typeText(loginId))
        onView(withId(R.id.password)).perform(typeText(password))
        onView(withId(R.id.send)).perform(click())
        // then
        val dialog = ShadowDialog.getLatestDialog()
        Assertions.assertNotNull(dialog)
        Assertions.assertTrue(dialog.isShowing)
    }
}