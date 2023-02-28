package com.example.campusoffer.viewmodels

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.campusoffer.data.ProductRepository
import com.example.campusoffer.models.Product
import com.example.campusoffer.models.responses.ProductsIdList
import com.example.campusoffer.models.responses.SubCategory
import com.example.campusoffer.util.NetworkResult
import kotlinx.coroutines.launch
import retrofit2.Response

class ProfileViewModel @ViewModelInject constructor(
    private val repository: ProductRepository,
    application: Application) : AndroidViewModel(application) {
    //region products under category
    var productsUnderCategoryRes: MutableLiveData<NetworkResult<ProductsIdList>> = MutableLiveData()

    fun getProductsUnderCategory(queries: Map<String, String>) = viewModelScope.launch {
        getProductsUnderCategorySafeCall(queries)
    }

    private suspend fun getProductsUnderCategorySafeCall(queries: Map<String, String>){
        productsUnderCategoryRes.value = NetworkResult.Loading()

        try {
            val res = repository.remote.getProductsUnderCategory(queries)
            productsUnderCategoryRes.value = handleProductsUnderCategoryResponse(res)
        }catch (e: Exception){
            productsUnderCategoryRes.value = NetworkResult.Error("No Internet Connection.")
        }

    }

    private fun handleProductsUnderCategoryResponse(res: Response<ProductsIdList>): NetworkResult<ProductsIdList>{
        when {
            res.code() >= 500 -> {
                return NetworkResult.Error("Server Internal Error")
            }

            res.code() >= 400 -> {
                return NetworkResult.Error("Server Error")
            }

            res.body()!!.productId.isEmpty() -> {
                return NetworkResult.Error("No product under this category")
            }

            res.isSuccessful -> {
                return NetworkResult.Success(res.body()!!)
            }

            else -> {
                return NetworkResult.Error(res.message())
            }
        }
    }
    //endregion
    //region get product by id
    var productByIDRes: MutableLiveData<NetworkResult<Product>> = MutableLiveData()

    fun getProductByID(queries: Map<String, String>) = viewModelScope.launch {
        getProductByIDSafeCall(queries)
    }

    private suspend fun getProductByIDSafeCall(queries: Map<String, String>){
        productByIDRes.value = NetworkResult.Loading()

        try {
            val res = repository.remote.getProductByID(queries)
            productByIDRes.value = handleProductByIDResponse(res)
        }catch (e: Exception){
            productByIDRes.value = NetworkResult.Error("No Internet Connection.")
        }

    }

    private fun handleProductByIDResponse(res: Response<Product>): NetworkResult<Product>{
        when {
            res.code() >= 500 -> {
                return NetworkResult.Error("Server Internal Error")
            }

            res.code() >= 400 -> {
                return NetworkResult.Error("Server Error")
            }

            res.isSuccessful -> {
                return NetworkResult.Success(res.body()!!)
            }

            else -> {
                return NetworkResult.Error(res.message())
            }
        }
    }
    //endregion

    //region get sub category
    var subCategoryRes: MutableLiveData<NetworkResult<SubCategory>> = MutableLiveData()

    fun getSubCategory(queries: Map<String, String>) = viewModelScope.launch {
        getSubCategorySafeCall(queries)
    }

    private suspend fun getSubCategorySafeCall(queries: Map<String, String>){
        subCategoryRes.value = NetworkResult.Loading()

        try {
            val res = repository.remote.getSubCategory(queries)
            subCategoryRes.value = handleSubCategoryResponse(res)
        }catch (e: Exception){
            subCategoryRes.value = NetworkResult.Error("No Internet Connection.")
        }

    }

    private fun handleSubCategoryResponse(res: Response<SubCategory>): NetworkResult<SubCategory>{
        when {
            res.code() >= 500 -> {
                return NetworkResult.Error("Server Internal Error")
            }

            res.code() >= 400 -> {
                return NetworkResult.Error("Server Error")
            }

            res.isSuccessful -> {
                return NetworkResult.Success(res.body()!!)
            }

            else -> {
                return NetworkResult.Error(res.message())
            }
        }
    }
    //endregion

    }