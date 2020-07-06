package com.example.myapplication.recipes.editrecipe

import androidx.lifecycle.LiveData
import com.example.myapplication.models.Recipe
import com.example.myapplication.models.RecipeIngredient
import com.example.myapplication.models.Step
import com.example.myapplication.recipes.editrecipe.util.RecipeIngredientsDao
import com.example.myapplication.recipes.editrecipe.util.RecipeStepsDao
import com.example.myapplication.recipes.util.RecipesDao
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EditRecipeRepository(
    private val recipeId: Int,
    private val recipesDao: RecipesDao,
    private val ingredientsDao: RecipeIngredientsDao,
    private val stepsDao: RecipeStepsDao
) {

    val recipe: LiveData<Recipe> = recipesDao.getRecipe(recipeId)
    val allIngredients: LiveData<List<RecipeIngredient>> = ingredientsDao.getAllIngredients()
    val allSteps: LiveData<List<Step>> = stepsDao.getAllSteps()

    init {
        GlobalScope.launch { populateDb() }
    }

    private suspend fun populateDb() {
        recipe.value?.let {
            insertIngredients(it.ingredientList)
            insertSteps(it.stepList)
        }

    }

    suspend fun insert(recipe: Recipe) = recipesDao.insert(recipe)

    suspend fun update(recipe: Recipe) = recipesDao.update(recipe)

    suspend fun insertIngredient(ingredient: RecipeIngredient) {
        ingredientsDao.insert(ingredient)
        val tempRecipe = recipe.value!!
        tempRecipe.addIngredient(ingredient)
        recipesDao.update(tempRecipe)
    }

    private suspend fun insertIngredients(ingredients: List<RecipeIngredient>) {
        ingredientsDao.insert(ingredients)
        val tempRecipe = recipe.value!!
        ingredients.forEach { tempRecipe.addIngredient(it) }
        recipesDao.update(tempRecipe)
    }

    suspend fun updateIngredient(ingredient: RecipeIngredient) = ingredientsDao.update(ingredient)

    suspend fun deleteIngredient(ingredient: RecipeIngredient) = ingredientsDao.delete(ingredient)

    suspend fun deleteAllIngredients() = ingredientsDao.deleteAllIngredients()

    suspend fun insertStep(step: Step) = stepsDao.insert(step)

    private suspend fun insertSteps(steps: List<Step>) = stepsDao.insert(steps)

    suspend fun updateStep(step: Step) = stepsDao.update(step)

    suspend fun deleteStep(step: Step) = stepsDao.delete(step)

    suspend fun deleteAllSteps() = stepsDao.deleteAllSteps()

}