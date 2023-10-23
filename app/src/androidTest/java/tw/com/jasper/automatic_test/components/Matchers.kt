package tw.com.jasper.automatic_test.components

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.StateListDrawable
import android.view.View
import android.widget.AdapterView
import android.widget.CompoundButton
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.matcher.BoundedMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher

abstract class ExcludeNullBoundedMatcher<T, S : T>(expectedType: Class<out S>) : BoundedMatcher<T, S>(expectedType) {
    abstract fun describeToNoneNullable(description: Description)
    override fun describeTo(description: Description?) {
        if (description == null) {
            return
        }
        describeToNoneNullable(description)
    }

    abstract fun matchesSafelyNoneNullable(item: S): Boolean
    override fun matchesSafely(item: S?): Boolean {
        if (item == null) {
            return false
        }
        return matchesSafelyNoneNullable(item)
    }
}

abstract class ExcludeNullTypeSafeMatcher<T> : TypeSafeMatcher<T>() {
    abstract fun describeToNoneNullable(description: Description)
    override fun describeTo(description: Description?) {
        if (description == null) {
            return
        }
        describeToNoneNullable(description)
    }

    abstract fun matchesSafelyNoneNullable(item: T): Boolean
    override fun matchesSafely(item: T): Boolean {
        if (item == null) {
            return false
        }
        return matchesSafelyNoneNullable(item)
    }
}

class RecyclerViewMatchers {
    companion object {
        fun atPosition(position: Int, itemMatcher: Matcher<View>, desc: String = ""): Matcher<View> = object : ExcludeNullBoundedMatcher<View, RecyclerView>(RecyclerView::class.java) {
            override fun describeToNoneNullable(description: Description) {
                description.appendText(desc)
                itemMatcher.describeTo(description)
            }

            override fun matchesSafelyNoneNullable(item: RecyclerView): Boolean {
                val viewHolder = item.findViewHolderForAdapterPosition(position) ?: return false
                return itemMatcher.matches(viewHolder.itemView)
            }
        }

        fun <T : TextView> isItemViewWithTextAt(viewId: Int, matcher: Matcher<View>, desc: String = ""): Matcher<View> = object : ExcludeNullTypeSafeMatcher<View>() {
            override fun describeToNoneNullable(description: Description) {
                description.appendText(desc)
                matcher.describeTo(description)
            }

            override fun matchesSafelyNoneNullable(item: View): Boolean = matcher.matches(item.findViewById<T>(viewId))
        }

        fun <T : CompoundButton> isItemViewCheckedAt(viewId: Int, matcher: Matcher<View>, desc: String = ""): Matcher<View> = object : ExcludeNullTypeSafeMatcher<View>() {
            override fun describeToNoneNullable(description: Description) {
                description.appendText(desc)
                matcher.describeTo(description)
            }

            override fun matchesSafelyNoneNullable(item: View): Boolean = matcher.matches(item.findViewById<T>(viewId))
        }
    }
}

class ListViewMatchers {
    fun withAdaptedData(matcher: Matcher<Any>, desc: String = ""): Matcher<AdapterView<*>> = object : ExcludeNullTypeSafeMatcher<AdapterView<*>>() {
        override fun describeToNoneNullable(description: Description) {
            description.appendText(desc)
            matcher.describeTo(description)
        }

        override fun matchesSafelyNoneNullable(item: AdapterView<*>): Boolean {
            val adapter = item.adapter
            for (i in 0 until adapter.count) {
                if (matcher.matches(adapter.getItem(i))) {
                    return true
                }
            }
            return false
        }
    }
}

class BackgroundColorMatchers {
    companion object {
        fun isPureBgColor(@ColorRes colorResId: Int, desc: String = ""): TypeSafeMatcher<View> = object : ExcludeNullTypeSafeMatcher<View>() {
            override fun describeToNoneNullable(description: Description) {
                description.appendText(desc)
            }

            override fun matchesSafelyNoneNullable(item: View): Boolean = when (val actualDrawable = item.background) {
                is ColorDrawable -> {
                    val expectedColor = item.context.resources.getColor(colorResId, null)
                    actualDrawable.color == expectedColor
                }
                else -> false
            }
        }

        fun isStateBgColor(@ColorRes colorResId: Int, desc: String = ""): TypeSafeMatcher<View> = object : ExcludeNullTypeSafeMatcher<View>() {
            override fun describeToNoneNullable(description: Description) {
                description.appendText(desc)
            }

            override fun matchesSafelyNoneNullable(item: View): Boolean = when (val drawable = item.background) {
                is StateListDrawable -> {
                    val expectedColor = item.context.resources.getColor(colorResId, null)
                    val actualDrawable = drawable.current
                    if (actualDrawable is ColorDrawable) {
                        actualDrawable.color == expectedColor
                    } else false
                }
                else -> false
            }
        }
    }
}