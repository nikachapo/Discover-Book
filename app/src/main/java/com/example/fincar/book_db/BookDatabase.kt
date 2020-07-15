package com.example.fincar.book_db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.fincar.models.book.GoogleBook


@Database(entities = [GoogleBook::class], version = 1, exportSchema = false)
abstract class BookDatabase : RoomDatabase() {
    abstract fun noteDao(): BookDao

    companion object {
        private var bookDatabase: BookDatabase? = null

        @Synchronized
        fun getBookDatabase(application: Application): BookDatabase? {
            if (bookDatabase == null) {
                bookDatabase = Room.databaseBuilder(
                    application,
                    BookDatabase::class.java, "book_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return bookDatabase
        }
    }
}
