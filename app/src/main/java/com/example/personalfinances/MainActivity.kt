package com.example.personalfinances

import android.content.Intent
import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.personalfinances.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding
    private var launcher: ActivityResultLauncher<Intent>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Arrays for Icon and Color resources
        val icons = resources.obtainTypedArray(R.array.icons)
        val colors = resources.obtainTypedArray(R.array.colors)

//        val db = MainDb.getDb(this)
//        db.getDao().getAll()

        // this launcher allows us to get results from our another activity
        launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result: ActivityResult ->
            if (result.resultCode == RESULT_OK){
                val catName = result.data?.getStringExtra(Utils.CAT_NAME_KEY)
                val catColor = result.data?.getIntExtra(Utils.CAT_COLOR_KEY, 0)
                val catIcon = result.data?.getIntExtra(Utils.CAT_ICON_KEY, 0)

                binding.categoryName.text = catName
                binding.categoryIcon.setBackgroundColor(colors.getColor(catColor!!, -1))
                binding.categoryIcon.icon = icons.getDrawable(catIcon!!)

            }else{
                Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }

//        binding.categoryName.text = catName
//        binding.categoryIcon.setBackgroundColor(colors.getColor(catColor!!, -1))
//        binding.categoryIcon.icon = icons.getDrawable(catIcon!!)

        // use launcher to start AddCategoryActivity when FAB is pressed
        binding.addCategoryFab.setOnClickListener {
            val intent = Intent(this@MainActivity, AddCategoryActivity::class.java)
//            launcher?.launch(intent)
            startActivity(intent)
        }
    }
}