package com.example.myapplication.recipes.editrecipe

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.models.Recipe
import com.example.myapplication.models.RecipeIngredient
import com.example.myapplication.models.Step
import com.example.myapplication.recipes.editrecipe.util.RecipeDatabase
import com.example.myapplication.recipes.util.RecipesDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditRecipeViewModel(
    application: Application,
    recipeId: Int
): AndroidViewModel(application) {

    private val repository: EditRecipeRepository

    val allIngredients: LiveData<List<RecipeIngredient>>
    val allSteps: LiveData<List<Step>>

    init {
        val recipesDao = RecipesDatabase.getDatabase(application).recipesDao()
        val ingredientsDao = RecipeDatabase.getDatabase(application).ingredientsDao()
        val stepsDao = RecipeDatabase.getDatabase(application).stepsDao()
        repository = EditRecipeRepository(recipeId, recipesDao, ingredientsDao, stepsDao)
        allIngredients = repository.allIngredients
        allSteps = repository.allSteps
    }

    fun getRecipe(): LiveData<Recipe> {
        return repository.recipe
    }

    fun insert(recipe: Recipe) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(recipe)
    }

    fun update(recipe: Recipe) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(recipe)
    }

    fun insertIngredient(ingredient: RecipeIngredient) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertIngredient(ingredient)
    }

    fun updateIngredient(ingredient: RecipeIngredient) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateIngredient(ingredient)
    }

    fun deleteIngredient(ingredient: RecipeIngredient) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteIngredient(ingredient)
    }

    fun deleteAllIngredients() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllIngredients()
    }

    fun insertStep(step: Step) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertStep(step)
    }

    fun updateStep(step: Step) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateStep(step)
    }

    fun deleteStep(step: Step) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteStep(step)
    }

    fun deleteAllSteps() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAllSteps()
    }

}