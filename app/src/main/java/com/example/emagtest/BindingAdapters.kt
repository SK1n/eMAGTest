package com.example.emagtest

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.BitmapImageViewTarget

@BindingAdapter("ImageUrl")
fun bindImageItem(cardView: CardView, url: String?) {
    Glide.with(cardView.context)
        .asBitmap()
        .load(url)
        .apply(RequestOptions().centerCrop())
        .placeholder(R.drawable.loading_animation)
        .error(R.drawable.ic_error)
        .into(object : BitmapImageViewTarget(cardView.findViewById(R.id.item_poster)) {
        })
}

@BindingAdapter("Favorites")
fun bindFavorites(imageView: ImageView, image: Int) {
    Glide.with(imageView.context).asBitmap().load(image).into(object : BitmapImageViewTarget(imageView) {
        override fun setResource(resource: Bitmap?) {
            super.setResource(resource)
        }
    })
}

@BindingAdapter("imageUrl")
fun bindImage(imageView: ImageView, url: String?) {
    Glide.with(imageView.context).load(url).apply(RequestOptions().centerCrop())
        .placeholder(R.drawable.loading_animation)
        .error(R.drawable.ic_error)
        .into(imageView)
}