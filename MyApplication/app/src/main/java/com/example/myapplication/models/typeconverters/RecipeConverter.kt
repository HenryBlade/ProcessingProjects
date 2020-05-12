package com.example.myapplication.models.typeconverters

import androidx.room.TypeConverter
import com.example.myapplication.models.RecipeIngredient
import com.example.myapplication.models.Step
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RecipeConverter {

    @TypeConverter
    fun fromStepList(steps: MutableList<Step>): String {
        return Gson().toJson(steps, object : TypeToken<MutableList<Step>>() {}.type)
    }

    @TypeConverter
    fun toStepList(stepString: String): MutableList<Step> {
        return Gson().fromJson(stepString, object : TypeToken<MutableList<Step>>() {}.type)
    }

    @TypeConverter
    fun fromIngredientList(ingredients: MutableList<RecipeIngredient>): String {
        return Gson().toJson(ingredients, object : TypeToken<MutableList<RecipeIngredient>>() {}.type)
    }

    @TypeConverter
    fun toIngredientList(ingredientString: String): MutableList<RecipeIngredient> {
        return Gson().fromJson(ingredientString, object : TypeToken<MutableList<RecipeIngredient>>() {}.type)
    }
}