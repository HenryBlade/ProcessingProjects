package com.example.myapplication.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.myapplication.models.typeconverters.RecipeConverter

@Entity(tableName = "recipe_table")
@TypeConverters(RecipeConverter::class)
class Recipe(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var title: String,
    var description: String
    ) {

    var ingredientList: MutableList<RecipeIngredient> = arrayListOf()
    var stepList: MutableList<Step> = arrayListOf()

    public fun addIngredient(ingredient: RecipeIngredient) = ingredientList.add(ingredient)

    public fun removeIngredient(ingredient: RecipeIngredient) = ingredientList.remove(ingredient)
}