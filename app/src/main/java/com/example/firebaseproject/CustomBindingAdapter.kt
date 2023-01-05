package com.example.firebaseproject

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("app:imgeUrl")
fun setImgeUrl(imageView: ImageView,uri: String?){
    uri?.let {
        Glide.with(imageView.context)
            .load(uri)
            .placeholder(R.drawable.ic_baseline_image_24)
            .centerCrop()
            .into(imageView)

    }

}

