package com.example.myapplication.recipes

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.Recipe
import com.example.myapplication.recipes.util.RecipesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecipesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecipesRepository

    val allRecipes: LiveData<List<Recipe>>

    init {
        val recipesDao = RecipesDatabase.getDatabase(application).recipesDao()
        repository = RecipesRepository(recipesDao)
        allRecipes = repository.allRecipes
    }

    fun insert(recipe: Recipe) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(recipe)
    }

    fun update(recipe: Recipe) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(recipe)
    }

    fun delete(recipe: Recipe) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(recipe)
    }

    fun deleteAllRecipes() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllRecipes()
    }

}
