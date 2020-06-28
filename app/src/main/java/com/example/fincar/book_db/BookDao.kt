package com.example.fincar.book_db
import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface BookDao {
    @Insert
    fun insert(book: BookModel)

    @Delete
    fun delete(book: BookModel)

    @Query("SELECT * FROM book_table")
    fun getAllBooks(): LiveData<List<BookModel>>

    @Query("SELECT COUNT(id) FROM book_table WHERE id=:id")
    fun bookCount(id: String): LiveData<Int>
}