package com.example.myapplication.inventory

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.inventory.util.InventoryDatabase
import com.example.myapplication.models.InventoryIngredient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class InventoryViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: InventoryRepository

    val allIngredients: LiveData<List<InventoryIngredient>>

    init {
        val inventoryDao = InventoryDatabase.getDatabase(application).inventoryDao()
        repository = InventoryRepository(inventoryDao)
        allIngredients = repository.allIngredients
    }

    fun insert(inventoryIngredient: InventoryIngredient) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(inventoryIngredient)
    }

    fun update(inventoryIngredient: InventoryIngredient) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(inventoryIngredient)
    }

    fun delete(inventoryIngredient: InventoryIngredient) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(inventoryIngredient)
    }

    fun deleteAllIngredients() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllIngredients()
    }

}
