package com.example.myapplication.recipes

import androidx.lifecycle.LiveData
import com.example.myapplication.models.Recipe
import com.example.myapplication.recipes.util.RecipesDao

class RecipesRepository(private val recipesDao: RecipesDao) {

    val allRecipes: LiveData<List<Recipe>> = recipesDao.getAllRecipes()

    suspend fun insert(recipe: Recipe) = recipesDao.insert(recipe)

    suspend fun update(recipe: Recipe) = recipesDao.update(recipe)

    suspend fun delete(recipe: Recipe) = recipesDao.delete(recipe)

    suspend fun deleteAllRecipes() = recipesDao.deleteAllRecipes()

}