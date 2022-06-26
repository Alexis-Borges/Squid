package com.example.squid1.Search

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.squid.databinding.FragmentSearchBinding
import com.example.squid1.Api.APIConfig
import com.example.squid1.Api.APIService
import com.example.squid1.Api.Category
import com.example.squid1.ProductAdapter
import retrofit2.Call
import retrofit2.Response

class SearchFragment : Fragment() {

    private lateinit var apiService: APIService
    private lateinit var productAdapter: ProductAdapter

    private var _binding: FragmentSearchBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val listValue = arrayListOf<String>()
    private var category = listOf<Category>()
    var categoryId: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        loadCategories()

        binding.searchBar

        val valueAdapter: ArrayAdapter<String> =
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_expandable_list_item_1,
                listValue

            )

        binding.listView.adapter = valueAdapter

        binding.searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchBar.clearFocus()

                if (listValue!!.contains(query)) {
                    valueAdapter.filter.filter(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                valueAdapter.filter.filter(newText)
                return false
            }
        })

        binding.listView.setOnItemClickListener { parent, view, position, id ->
            for (value in category) {
                if (value.name == valueAdapter.getItem(position)) categoryId = value.id
            }
            Log.d("id category", categoryId.toString())

            val intentValue = Intent(context, ResultSearchActivity::class.java)

            intentValue.putExtra("CATEGORY", categoryId)
            startActivity(intentValue)
        }
        return root
    }


    private fun loadCategories() {

        apiService =
            activity?.let { APIConfig.getRetrofitClient(it).create(APIService::class.java) }!!


        apiService.getCategories().enqueue(object : retrofit2.Callback<List<Category>> {
            override fun onFailure(call: Call<List<Category>>, t: Throwable) {

                print(t.message)
                Log.d("Data error", t.message.toString())
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()

            }

            override fun onResponse(
                call: Call<List<Category>>,
                response: Response<List<Category>>
            ) {

                category = response.body()!!

                listValue.clear()

                for (cate in category) {
                    listValue.add(cate.name)

                }

            }

        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}