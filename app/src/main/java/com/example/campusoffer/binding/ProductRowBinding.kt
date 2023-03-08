package com.example.campusoffer.binding

import android.app.Notification.Action
import android.graphics.BitmapFactory
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import coil.load
import com.example.campusoffer.R
import com.example.campusoffer.data.ProductRepository
import com.example.campusoffer.models.Product
import com.example.campusoffer.ui.fragments.FavoriteFragmentDirections
import com.example.campusoffer.ui.fragments.ShopFragmentDirections
import javax.inject.Inject

class ProductRowBinding{
    companion object{
        val TAG = "ProductRowBinding"

        @BindingAdapter("onProductClickListener")
        @JvmStatic
        fun onProductClickListener(productRowLayout: ConstraintLayout, product: Product) {
            productRowLayout.setOnClickListener {
                try {
                    val action =
                        ShopFragmentDirections.actionShopFragmentToDetailsActivity(product)
                    productRowLayout.findNavController().navigate(action)
                } catch (e: Exception) {
                    Log.d("onProductClickListener", e.toString())
                }
            }
        }

        @BindingAdapter("onProductClickListenerFavorite")
        @JvmStatic
        fun onProductClickListenerFavorite(favoriteRowLayout: ConstraintLayout, product: Product) {
            favoriteRowLayout.setOnClickListener {
                try {
                    val action =
                        FavoriteFragmentDirections.actionFavoriteFragmentToDetailsActivity(product)
                    favoriteRowLayout.findNavController().navigate(action)
                } catch (e: Exception) {
                    Log.d("onProductClickListener", e.toString())
                }
            }
        }




        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, product: Product?) {
            if(product == null) return
            val imageBytes = product.coverImage
            if (imageBytes != null){
                val decodedImage =
                    BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size) ?: return
                imageView.setImageBitmap(decodedImage)
            }
        }



        @BindingAdapter("applySoldColor")
        @JvmStatic
        fun applyVeganColor(view: View, sold: Int) {
            if(sold == 0){
                when(view){
                    is ImageView -> {
                        view.setColorFilter(
                            ContextCompat.getColor(
                                view.context,
                                R.color.green
                            )
                        )
                    }
                }
            } else {
                when(view){
                    is ImageView -> {
                        view.setColorFilter(
                            ContextCompat.getColor(
                                view.context,
                                R.color.red
                            )
                        )
                    }
                }
            }
        }

    }
}