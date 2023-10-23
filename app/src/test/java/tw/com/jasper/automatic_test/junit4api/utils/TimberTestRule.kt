package tw.com.jasper.automatic_test.junit4api.utils

import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import timber.log.Timber

class TimberTestRule : TestRule {
    private val printlnTree = object : Timber.DebugTree() {
        override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
            println("$tag: --> $message")
        }

        override fun createStackElementTag(element: StackTraceElement): String = "(${element.fileName}:${element.lineNumber}) - ${element.methodName}"
    }

    override fun apply(base: Statement, description: Description): Statement = object : Statement() {
        override fun evaluate() {
            Timber.plant(printlnTree)
            Timber.i("Timber.plant...\n")
            base.evaluate()
            Timber.i("Timber.uproot...")
            Timber.uproot(printlnTree)
        }
    }
}