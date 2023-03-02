package com.example.emagtest

import android.view.View
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
@BindingAdapter("goneIfNull")
fun goneIfNull(view: View, it: Any?) {
    view.visibility = if (it == "" || it == null) View.GONE else View.VISIBLE
}

private const val IMAGE_LOW_RES_BASE_URL = "https://image.tmdb.org/t/p/w500"