package tw.com.jasper.automatic_test.robolectric

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import tw.com.jasper.automatic_test.R
import tw.com.jasper.automatic_test.databinding.ActivityRegisterRobolectricBinding

class RobolectricRegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterRobolectricBinding
    private val registerVerify = RegisterVerify()
    private val repository = Repository(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterRobolectricBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.send.setOnClickListener {
            val loginId = binding.loginId.text.toString()
            if (!registerVerify.isLoginIdVerify(loginId)) {
                AlertDialog.Builder(this)
                    .setMessage("帳號至少要6碼，第1碼為英文")
                    .setTitle("錯誤")
                    .show()
                return@setOnClickListener
            }
            val pwd = binding.password.text.toString()
            if (!registerVerify.isPasswordVerify(pwd)) {
                AlertDialog.Builder(this)
                    .setMessage("密碼至少要8碼，第1碼為英文，並包含1碼數字")
                    .setTitle("錯誤")
                    .show()
                return@setOnClickListener
            }
            repository.saveUserId(loginId)
            val intent = Intent(this, RobolectricResultActivity::class.java).apply {
                putExtra("ID", loginId)
            }
            startActivity(intent)
        }
    }
}

class RobolectricResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result_robolectric)

        val id = intent.getStringExtra("ID")
    }
}
