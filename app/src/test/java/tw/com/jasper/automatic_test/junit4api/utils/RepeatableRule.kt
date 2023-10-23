package tw.com.jasper.automatic_test.junit4api.utils

import org.junit.rules.MethodRule
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.FrameworkMethod
import org.junit.runners.model.Statement

abstract class NotNullMethodRule : MethodRule {

    override fun apply(base: Statement?, method: FrameworkMethod?, target: Any?): Statement {
        if (base == null || method == null || target == null) return getEmptyStatement()
        return applyWithoutNull(base, method, target)
    }

    abstract fun applyWithoutNull(base: Statement, method: FrameworkMethod, target: Any): Statement

    private fun getEmptyStatement(): Statement = object : Statement() {
        override fun evaluate() {}
    }
}

class RepeatableMethodRule(private val repeatingMap: Map<String, Int>) : NotNullMethodRule() {
    override fun applyWithoutNull(base: Statement, method: FrameworkMethod, target: Any): Statement = object : Statement() {
        override fun evaluate() {
            repeatingMap[method.name]?.let {
                for (i in 0 until it) {
                    base.evaluate()
                }
            } ?: run {
                base.evaluate()
            }
        }
    }
}

/**
 * TestRule 可取代 MethodRule！
 */
class RepeatableTestRule(private val repeatingMap: Map<String, Int>) : TestRule {
    override fun apply(base: Statement, description: Description): Statement = object : Statement() {
        override fun evaluate() {
            repeatingMap[description.methodName]?.let {
                for (i in 0 until it) {
                    base.evaluate()
                }
            } ?: run {
                base.evaluate()
            }
        }
    }
}