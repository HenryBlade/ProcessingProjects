package com.example.myapplication.recipes.editrecipe.util

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.myapplication.models.RecipeIngredient
import com.example.myapplication.models.Step
import com.example.myapplication.models.typeconverters.RecipeConverter

@Database(entities = [RecipeIngredient::class, Step::class], version = 1, exportSchema = false)
@TypeConverters(RecipeConverter::class)
abstract class RecipeDatabase: RoomDatabase() {

    abstract fun stepsDao(): RecipeStepsDao
    abstract fun ingredientsDao(): RecipeIngredientsDao


    companion object {

        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        fun getDatabase(context: Context): RecipeDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RecipeDatabase::class.java,
                    "recipe_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}