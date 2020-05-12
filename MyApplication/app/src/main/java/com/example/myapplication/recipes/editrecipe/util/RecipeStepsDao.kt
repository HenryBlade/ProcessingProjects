package com.example.myapplication.recipes.editrecipe.util

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myapplication.models.Step

@Dao
interface RecipeStepsDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(step: Step)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(step: List<Step>)

    @Update
    suspend fun update(step: Step)

    @Delete
    suspend fun delete(step: Step)

    @Query("DELETE FROM recipe_step_table")
    suspend fun deleteAllSteps()

    @Query("SELECT * FROM recipe_step_table ORDER BY number ASC")
    fun getAllSteps(): LiveData<List<Step>>

}