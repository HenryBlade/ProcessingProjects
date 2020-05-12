package com.example.myapplication.recipes.util

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.models.Recipe

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(recipe: Recipe)

    @Update
    suspend fun update(recipe: Recipe)

    @Delete
    suspend fun delete(recipe: Recipe)

    @Query("DELETE FROM recipe_table")
    suspend fun deleteAllRecipes()

    @Query("SELECT * FROM recipe_table WHERE id=:recipeId")
    fun getRecipe(recipeId: Int): LiveData<Recipe>

    @Query("SELECT * FROM recipe_table ORDER BY title ASC")
    fun getAllRecipes(): LiveData<List<Recipe>>
}