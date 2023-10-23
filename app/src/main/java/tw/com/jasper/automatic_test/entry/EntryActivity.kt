package tw.com.jasper.automatic_test.entry

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import tw.com.jasper.automatic_test.databinding.ActivityEntryBinding
import tw.com.jasper.automatic_test.list.ListViewActivity
import tw.com.jasper.automatic_test.list.RecyclerViewActivity
import tw.com.jasper.automatic_test.robolectric.RobolectricRegisterActivity
import tw.com.jasper.automatic_test.robolectric.VersionActivity

class EntryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEntryBinding

    private val activityClazzArray: Array<Class<*>> = arrayOf(
        RecyclerViewActivity::class.java,
        ListViewActivity::class.java,
        RobolectricRegisterActivity::class.java,
        VersionActivity::class.java,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        with(binding.gvEntry) {
            adapter = ArrayAdapter(this@EntryActivity, android.R.layout.simple_list_item_1, activityClazzArray.map { it.simpleName.replace("Activity", "") })
            setOnItemClickListener { adapterView, view, position, id ->
                val targetActivityClazz = activityClazzArray[position]
                startActivity(Intent(this@EntryActivity, targetActivityClazz))
            }
        }
    }
}