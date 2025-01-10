package eu.tutorials.mywishlistapp

import android.content.Context
import androidx.room.Room
import eu.tutorials.mywishlistapp.data.WishDatabase
import eu.tutorials.mywishlistapp.data.WishRepository

object Graph {
    lateinit var database: WishDatabase

    // You load the data when you actually need it
    val wishRepository by lazy {
        WishRepository(wishDao = database.wishDao())
    }

    // creating an instance of the wish database and assign it
    // where the database is located
    fun provide(context: Context) {
        database = Room.databaseBuilder(context, WishDatabase::class.java, "wish_list.db")
            .build()
    }
}