package com.example.myapplication.inventory.util

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.models.InventoryIngredient


@Database(entities = [InventoryIngredient::class], version = 2, exportSchema = false)
abstract class InventoryDatabase: RoomDatabase() {

    abstract fun inventoryDao(): InventoryDao

    companion object {

        @Volatile
        private var INSTANCE: InventoryDatabase? = null

        fun getDatabase(context: Context): InventoryDatabase {
            val tempInstance =
                INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        InventoryDatabase::class.java,
                        "inventory_database"
                    ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}