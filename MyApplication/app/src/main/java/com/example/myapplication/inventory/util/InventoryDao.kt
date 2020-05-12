package com.example.myapplication.inventory.util

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.models.InventoryIngredient

@Dao
interface InventoryDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ingredient: InventoryIngredient)

    @Update
    suspend fun update(ingredient: InventoryIngredient)

    @Delete
    suspend fun delete(ingredient: InventoryIngredient)

    @Query("DELETE FROM inventory_ingredient_table")
    suspend fun deleteAllIngredients()

    @Query("SELECT * FROM inventory_ingredient_table ORDER BY name ASC")
    fun getAllIngredients(): LiveData<List<InventoryIngredient>>
}