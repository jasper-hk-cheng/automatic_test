package tw.com.jasper.automatic_test.robolectric

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class VersionActivity : AppCompatActivity() {
    companion object {
        const val VERSION_DESC_BEYOND = "beyond"
        const val VERSION_DESC_UNDER = "under"
    }

    lateinit var versionDesc: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            versionDesc = VERSION_DESC_BEYOND
        } else {
            versionDesc = VERSION_DESC_UNDER
        }
    }
}