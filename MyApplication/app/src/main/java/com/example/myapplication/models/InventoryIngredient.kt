package com.example.myapplication.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "inventory_ingredient_table")
class InventoryIngredient(
    @PrimaryKey(autoGenerate = true) override val id: Int,
    override var name: String,
    override var amount: Double,
    override var unit: String
): Ingredient {

}