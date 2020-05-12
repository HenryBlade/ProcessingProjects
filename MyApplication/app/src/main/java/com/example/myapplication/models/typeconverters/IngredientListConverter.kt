package com.example.myapplication.models.typeconverters

import androidx.room.TypeConverter
import com.example.myapplication.models.RecipeIngredient
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class IngredientListConverter {

    @TypeConverter
    fun fromIngredientList(ingredients: List<RecipeIngredient>): String {
        return Gson().toJson(ingredients, object : TypeToken<List<RecipeIngredient>>() {}.type)
    }

    @TypeConverter
    fun toIngredientList(ingredientString: String): List<RecipeIngredient> {
        return Gson().fromJson(ingredientString, object : TypeToken<List<RecipeIngredient>>() {}.type)
    }
}