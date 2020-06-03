package com.example.fincar.book

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [BookModel::class], version = 1)
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
