package com.example.diyetkitabi.util

import android.content.Context
import android.widget.ImageView
import androidx.constraintlayout.widget.Placeholder
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.diyetkitabi.R

//extensions
//fun String.myExtension(parameter : String){
//    println(parameter)
//}

fun ImageView.ImageDownload(url : String?, placeholder: CircularProgressDrawable){
    val options = RequestOptions().placeholder(placeholder).error(R.mipmap.ic_launcher)
    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
}

fun placeHolderYap(context : Context) : CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
    //adaptöre gittik
}
