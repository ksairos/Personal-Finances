package com.example.personalfinances.categories

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.personalfinances.Utils
import com.example.personalfinances.data.Category
import com.example.personalfinances.data.MainDb
import com.example.personalfinances.databinding.FragmentCategoriesBinding
import kotlinx.coroutines.launch

class CategoriesFragment : Fragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private lateinit var adapter: CategoryAdapter
    private val catDb by lazy { MainDb.getDb(requireContext()).catDao() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCategoriesBinding.inflate(layoutInflater)

        // Set Recycler View
        binding.categoryRecView.layoutManager = GridLayoutManager(requireActivity(), 4)
        adapter = CategoryAdapter()
        binding.categoryRecView.adapter = adapter

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observeCatDb()
    }

    // this launcher allows us to get results from our another activity
    private var launcher: ActivityResultLauncher<Intent>? =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            when (result.resultCode) {
                AppCompatActivity.RESULT_OK -> {

                    val catName = result.data?.getStringExtra(Utils.CAT_NAME_KEY)
                    val catColor = result.data?.getIntExtra(Utils.CAT_COLOR_KEY, 0)
                    val catIcon = result.data?.getIntExtra(Utils.CAT_ICON_KEY, 0)

                    val newCategory = Category(null, catName, 0, catIcon, catColor)

                    lifecycleScope.launch {
                        catDb.insert(newCategory)
                    }
                    Toast.makeText(requireActivity(), "A new category is added", Toast.LENGTH_SHORT).show()
                }
                AppCompatActivity.RESULT_CANCELED -> {
                    Toast.makeText(requireActivity(), "Canceled", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(requireActivity(), "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }

    /*
This function is used to observe changes in our database. Whenever data is changed, the code in
the curly braces us run. In our case this updates the content in our adapter.
 */
    private fun observeCatDb() {
        lifecycleScope.launch {
            catDb.getAll().collect { categoryList ->
                if (categoryList.isNotEmpty()) {
                    adapter.submitList(categoryList)

                }
            }
        }
    }

    // This function is used to initialize views and their inner content
    private fun init() {
        binding.apply {
            // Set Listener for FAB that creates new categories
            addCategoryFab.setOnClickListener {
                val intent = Intent(requireActivity(), AddCategoryActivity::class.java)
                launcher?.launch(intent)
            }
        }
    }

}