package tw.com.jasper.automatic_test.junit4api.robolectric

import android.os.Build
import android.util.Patterns
import androidx.test.core.app.ActivityScenario
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import tw.com.jasper.automatic_test.robolectric.VersionActivity
import tw.com.jasper.automatic_test.robolectric.VersionActivity.Companion.VERSION_DESC_BEYOND
import tw.com.jasper.automatic_test.robolectric.VersionActivity.Companion.VERSION_DESC_UNDER

@RunWith(RobolectricTestRunner::class)
class TestStaticAndroidComponent {

    companion object {
        fun isEmailAddress(email: String): Boolean = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    @Test
    fun testPatternsEmailAddress() {
        assertTrue(isEmailAddress("ggg@taa.com"))
        assertFalse(isEmailAddress("ggga"))
        assertFalse(isEmailAddress("ggga#/aacc."))
    }

    @Test
    @Config(sdk = [Build.VERSION_CODES.TIRAMISU])
    fun testVersionTiramisu() {
        ActivityScenario.launch(VersionActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                assertEquals(VERSION_DESC_BEYOND, activity.versionDesc)
            }
        }
    }

    @Test
    @Config(sdk = [Build.VERSION_CODES.P])
    fun testVersionP() {
        ActivityScenario.launch(VersionActivity::class.java).use { scenario ->
            scenario.onActivity { activity ->
                assertEquals(VERSION_DESC_UNDER, activity.versionDesc)
            }
        }
    }
}
