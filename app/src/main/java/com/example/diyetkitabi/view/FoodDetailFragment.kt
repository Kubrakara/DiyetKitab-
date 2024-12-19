package com.example.diyetkitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.diyetkitabi.databinding.FragmentFoodDetailBinding
import com.example.diyetkitabi.databinding.FragmentFoodListBinding
import com.example.diyetkitabi.util.ImageDownload
import com.example.diyetkitabi.util.placeHolderYap
import com.example.diyetkitabi.viewmodel.FoodDetailViewModel
import com.example.diyetkitabi.viewmodel.FoodListViewModel


class FoodDetailFragment : Fragment() {

    private var _binding: FragmentFoodDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FoodDetailViewModel
    var foodId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFoodDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[FoodDetailViewModel::class.java]

        arguments?.let{
            foodId = FoodDetailFragmentArgs.fromBundle(it).uuid

        }
        viewModel.getRoomData(foodId)
        observeLiveData()
    }

    private fun observeLiveData(){
        viewModel.foodLiveData.observe(viewLifecycleOwner){
            binding.foodName.text = it.name
            binding.foodCalories.text = it.calories
            binding.foodCarbohydrate.text = it.carbohydrates
            binding.foodProtein.text = it.protein
            binding.foodOil.text = it.oil
            binding.foodImage.ImageDownload(it.image, placeHolderYap(requireContext()))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}