package com.example.fincar.book_db
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.fincar.models.book.GoogleBook

@Dao
interface BookDao {
    @Insert
    fun insert(googleBook: GoogleBook)

    @Delete
    fun delete(googleBook: GoogleBook)

    @Update
    fun update(googleBook: GoogleBook)

    @Query("SELECT * FROM book_table")
    fun getAllBooks(): LiveData<List<GoogleBook>>

    @Query("SELECT COUNT(id) FROM book_table WHERE id=:id")
    fun bookCount(id: String): LiveData<Int>

}