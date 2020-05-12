package com.example.myapplication.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe_step_table")
class Step(
    var description: String,
    @PrimaryKey var number: Int
) {

}