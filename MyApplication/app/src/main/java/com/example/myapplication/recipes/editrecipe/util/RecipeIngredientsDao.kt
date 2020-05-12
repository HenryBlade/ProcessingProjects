package com.example.myapplication.recipes.editrecipe.util

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.models.RecipeIngredient

@Dao
interface RecipeIngredientsDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(ingredient: RecipeIngredient)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(ingredient: List<RecipeIngredient>)

    @Update
    suspend fun update(ingredient: RecipeIngredient)

    @Delete
    suspend fun delete(ingredient: RecipeIngredient)

    @Query("DELETE FROM recipe_ingredient_table")
    suspend fun deleteAllIngredients()

    @Query("SELECT * FROM recipe_ingredient_table ORDER BY amount ASC")
    fun getAllIngredients(): LiveData<List<RecipeIngredient>>

}
