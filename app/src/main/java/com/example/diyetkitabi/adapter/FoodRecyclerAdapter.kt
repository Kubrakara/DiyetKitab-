package com.example.diyetkitabi.adapter

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.diyetkitabi.databinding.FoodRecyclerViewBinding
import com.example.diyetkitabi.model.Food
import com.example.diyetkitabi.util.ImageDownload
import com.example.diyetkitabi.util.placeHolderYap
import com.example.diyetkitabi.view.FoodListFragmentDirections

class FoodRecyclerAdapter(val foodList : ArrayList<Food>) : RecyclerView.Adapter<FoodRecyclerAdapter.FoodViewHolder>() {

    class FoodViewHolder(val binding : FoodRecyclerViewBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = FoodRecyclerViewBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FoodViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    fun foodListUpdate(newFoodList : List<Food>){
        foodList.clear()
        foodList.addAll(newFoodList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.binding.name.text = foodList[position].name
        holder.binding.calorie.text = foodList[position].calories

        holder.itemView.setOnClickListener{
            val action = FoodListFragmentDirections.actionFoodListFragmentToFoodDetailFragment(foodList[position].uuid)
            Navigation.findNavController(it).navigate(action)
        }
        //extensions
        holder.binding.imageView.ImageDownload(foodList[position].image, placeHolderYap(holder.itemView.context))
    }
}


