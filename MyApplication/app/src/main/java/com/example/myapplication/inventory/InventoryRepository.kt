package com.example.myapplication.inventory

import androidx.lifecycle.LiveData
import com.example.myapplication.inventory.util.InventoryDao
import com.example.myapplication.models.InventoryIngredient

class InventoryRepository(private val inventoryDao: InventoryDao) {

    val allIngredients: LiveData<List<InventoryIngredient>> = inventoryDao.getAllIngredients()

    suspend fun insert(ingredient: InventoryIngredient) = inventoryDao.insert(ingredient)

    suspend fun update(ingredient: InventoryIngredient) = inventoryDao.update(ingredient)

    suspend fun delete(ingredient: InventoryIngredient) = inventoryDao.delete(ingredient)

    suspend fun deleteAllIngredients() = inventoryDao.deleteAllIngredients()
}