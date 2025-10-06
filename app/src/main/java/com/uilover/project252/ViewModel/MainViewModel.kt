package com.uilover.project252.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uilover.project252.Domain.CategoryModel
import com.uilover.project252.Domain.ItemModel
import com.uilover.project252.Repository.MainRepository

class MainViewModel : ViewModel() {
    private val _categories = MutableLiveData<MutableList<CategoryModel>>()
    fun loadCategory(): LiveData<MutableList<CategoryModel>> = _categories

    init {
        // Put your manual list here
        _categories.value = mutableListOf(
            CategoryModel("Northern",   101),
            CategoryModel("Southern",     102),
            CategoryModel("Central",      103),
            CategoryModel("Northeastern",     104),
        )
    }

    private val repository = MainRepository()


    fun loadPopular(): LiveData<MutableList<ItemModel>> {
        return repository.loadPopular()
    }

    fun loadSpecial(): LiveData<MutableList<ItemModel>> {
        return repository.loadSpecial()
    }

    fun loadItems(categoryId: String): LiveData<MutableList<ItemModel>> {
        return repository.loadCategoryItems(categoryId)
    }
}