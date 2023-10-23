package tw.com.jasper.automatic_test.junit4api.annotation

import lab.kito.digital.test.junit4api.utils.TimberTestRule
import lab.kito.digital.test.junit4api.utils.TracingTestWatcher
import org.junit.ClassRule
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Suite
import org.junit.runners.Suite.SuiteClasses
import timber.log.Timber

@RunWith(Suite::class)
@SuiteClasses(ClazzA::class, ClazzB::class, ClazzC::class)
class TestRunWithSuite {
    companion object {
        @JvmStatic
        @get:ClassRule
        val timberTestRule = TimberTestRule()
    }
}

class ClazzA {

    @get:Rule
    val tracingTestWatcher = TracingTestWatcher()

    @Test
    fun testClazzA() {
        Timber.i("ClazzA started")
    }
}

class ClazzB {

    @get:Rule
    val tracingTestWatcher = TracingTestWatcher()

    @Test
    fun testClazzB() {
        Timber.i("ClazzB started")
    }
}

class ClazzC {

    @get:Rule
    val tracingTestWatcher = TracingTestWatcher()

    @Test
    fun testClazzC() {
        Timber.i("ClazzC started")
    }
}
