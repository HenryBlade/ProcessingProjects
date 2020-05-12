package com.example.myapplication.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "recipe_ingredient_table")
class RecipeIngredient(
    @PrimaryKey(autoGenerate = true) override var id: Int,
    override var name: String,
    override var amount: Double,
    override var unit: String
): Ingredient {

}