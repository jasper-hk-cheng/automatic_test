package tw.com.jasper.automatic_test.junit4api.utils

import org.junit.AssumptionViolatedException
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import timber.log.Timber

class TracingTestWatcher(private val isEnabled: Boolean = true) : TestWatcher() {

    override fun starting(description: Description) {
        super.starting(description)
        if (!isEnabled) return
        Timber.i("starting...${description.displayName}")
    }

    override fun finished(description: Description) {
        super.finished(description)
        if (!isEnabled) return
        Timber.i("finished...${description.displayName}\n")
    }

    override fun succeeded(description: Description?) {
        super.succeeded(description)
        if (!isEnabled) return
        Timber.i("succeeded...${description?.displayName}")
    }

    override fun failed(e: Throwable?, description: Description?) {
        super.failed(e, description)
        if (!isEnabled) return
        Timber.e("failed...${description?.displayName}")
    }

    override fun skipped(e: AssumptionViolatedException?, description: Description?) {
        super.skipped(e, description)
        if (!isEnabled) return
        Timber.w("skipped...${description?.displayName}")
    }
}