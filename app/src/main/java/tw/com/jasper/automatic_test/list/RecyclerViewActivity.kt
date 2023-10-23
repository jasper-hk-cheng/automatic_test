package tw.com.jasper.automatic_test.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tw.com.jasper.automatic_test.databinding.ActivityRecyclerViewBinding
import tw.com.jasper.automatic_test.databinding.ListItemBinding
import tw.com.jasper.automatic_test.util.DataProvider.ROW_TEXT
import tw.com.jasper.automatic_test.util.DataProvider.populateData

class RecyclerViewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecyclerViewBinding

    public override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // recycler view
        binding.rvList.run {
            adapter = RecyclerViewAdapter(populateData(10)) {
                binding.selectionRowValue.text = it.tvContent.text
            }
            layoutManager = LinearLayoutManager(this@RecyclerViewActivity, LinearLayoutManager.VERTICAL, false)
        }
    }
}

class RecyclerViewAdapter(private val list: List<Map<String, String>>, private val callback: (ListItemBinding) -> Unit) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {
    override fun getItemCount(): Int = list.count()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemBinding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemBinding, callback)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position])
    }

    class ViewHolder(private val listItemBinding: ListItemBinding, callback: (ListItemBinding) -> Unit) : RecyclerView.ViewHolder(listItemBinding.root) {
        init {
            listItemBinding.tgBtn.setOnClickListener {
                callback(listItemBinding)
            }
        }

        fun bind(map: Map<String, String>) {
            with(listItemBinding) {
                tvContent.text = map[ROW_TEXT]
            }
        }
    }
}