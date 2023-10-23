package tw.com.jasper.automatic_test.junit4api.utils

import lab.kito.digital.test.junit4api.utils.TimberTestRule
import lab.kito.digital.test.junit4api.utils.TracingTestWatcher



import org.junit.ClassRule
import org.junit.Rule

open class BaseTest {

    companion object {
        @JvmStatic
        @get:ClassRule
        val timberTestRule = TimberTestRule()
    }

    @get:Rule
    val tracingTestWatcher = TracingTestWatcher()
}
