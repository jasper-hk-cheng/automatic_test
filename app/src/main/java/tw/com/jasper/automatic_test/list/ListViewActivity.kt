package tw.com.jasper.automatic_test.list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.appcompat.app.AppCompatActivity
import tw.com.jasper.automatic_test.databinding.ActivityListViewBinding
import tw.com.jasper.automatic_test.databinding.ListItemBinding
import tw.com.jasper.automatic_test.util.DataProvider.ROW_TEXT
import tw.com.jasper.automatic_test.util.DataProvider.populateData

class ListViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListViewBinding

    public override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        binding = ActivityListViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.lvList.adapter = MyListAdapter(this, populateData(10)) {
            binding.selectionRowValue.text = it.tvContent.text
        }
    }
}

class MyListAdapter(private val context: Context, private val list: List<Map<String, String>>, private val onClickListener: (ListItemBinding) -> Unit) : BaseAdapter() {
    override fun getCount(): Int = list.count()
    override fun getItem(position: Int): Map<String, String> = list[position]
    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View = if (convertView == null) {
        val itemViewBinding = ListItemBinding.inflate(LayoutInflater.from(context)).apply {
            tvContent.text = list[position][ROW_TEXT]
            tgBtn.setOnClickListener {
                onClickListener.invoke(this)
            }
        }
        itemViewBinding.root
    } else {
        val itemViewBinding = ListItemBinding.bind(convertView).apply {
            tvContent.text = list[position][ROW_TEXT]
        }
        itemViewBinding.root
    }
}
