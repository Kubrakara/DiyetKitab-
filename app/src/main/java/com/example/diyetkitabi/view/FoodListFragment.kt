package com.example.diyetkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.diyetkitabi.adapter.FoodRecyclerAdapter
import com.example.diyetkitabi.databinding.FragmentFoodDetailBinding
import com.example.diyetkitabi.databinding.FragmentFoodListBinding
import com.example.diyetkitabi.service.FoodApi
import com.example.diyetkitabi.viewmodel.FoodListViewModel
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


class FoodListFragment : Fragment() {


    private var _binding: FragmentFoodListBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FoodListViewModel
    private val  FoodRecyclerAdapter = FoodRecyclerAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //requireActivity().getSharedPreferences()  - bu şekilde de kullanılabilir.
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[FoodListViewModel::class.java]
        viewModel.refreshData()

        binding.foodRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.foodRecyclerView.adapter = FoodRecyclerAdapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.foodRecyclerView.visibility = View.GONE
            binding.foodErrorMessage.visibility = View.GONE
            binding.foodLoadingprogressBar.visibility = View.VISIBLE
            viewModel.refreshDataFromInternet()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()

    }

    private fun observeLiveData() {
        viewModel.foods.observe(viewLifecycleOwner) {
            FoodRecyclerAdapter.foodListUpdate(it)
            binding.foodRecyclerView.visibility = View.VISIBLE
    }
        viewModel.foodErrorMessage.observe(viewLifecycleOwner) {
            if (it) {
                binding.foodErrorMessage.visibility = View.VISIBLE
                binding.foodRecyclerView.visibility = View.GONE
            } else {
                binding.foodErrorMessage.visibility = View.GONE
        }
    }
        viewModel.foodLoading.observe(viewLifecycleOwner) {
            if(it) {
                binding.foodErrorMessage.visibility = View.GONE
                binding.foodRecyclerView.visibility = View.GONE
                binding.foodLoadingprogressBar.visibility = View.VISIBLE
            } else {
                binding.foodLoadingprogressBar.visibility = View.GONE
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}