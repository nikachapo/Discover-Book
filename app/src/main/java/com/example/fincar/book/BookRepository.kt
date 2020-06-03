package com.example.fincar.book

import android.app.Application
import android.os.AsyncTask
import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fincar.MainActivity
import com.example.fincar.app.App
import com.example.fincar.network.BooksApiRequest
import org.json.JSONException
import org.json.JSONObject


class BookRepository(application: Application?) {
    private var bookDao: BookDao? = null

    private val books = MutableLiveData<List<BookModel>>()

    fun getBooks(q: String): MutableLiveData<List<BookModel>> {
        searchBook(q)
        return books
    }

    init {
        if (application != null) {
            val database = BookDatabase.getBookDatabase(application)
            bookDao = database?.noteDao()
        }
    }

    fun insert(book: BookModel) {
        AsyncTask.execute {
            bookDao?.insert(book)
        }
    }

    fun delete(book: BookModel) {
        AsyncTask.execute {
            bookDao?.delete(book)
        }
    }

    fun getStarredBooks(): LiveData<List<BookModel>>? {
        return bookDao?.getAllBooks()
    }

    fun getBookCountLiveData(id: String): LiveData<Int>? {
        return bookDao?.bookCount(id)
    }

    private fun searchBook(q: String) {
        BooksApiRequest.getBooksRequest(q, object : BooksApiRequest.RequestCallBacks {
            override fun onSuccess(successJson: String) {

                val rootJsonObject = JSONObject(successJson)
                // Get the JSONArray of books items.
                val itemsArray = rootJsonObject.getJSONArray("items")
                val booksList = ArrayList<BookModel>()

                for (i in 0 until itemsArray.length()) {
                    try {


                        val bookJson = itemsArray.getJSONObject(i)
                        val id = bookJson.getString("id")
                        val volumeInfo = bookJson.getJSONObject("volumeInfo")
                        val previewUrl = volumeInfo.getString("previewLink")

                        val title = volumeInfo.getString("title")
                        val imageLinksJsonObject = volumeInfo.getJSONObject("imageLinks")
                        val imgUrl = imageLinksJsonObject.getString("smallThumbnail")

                        var authors = ""
                        if (volumeInfo.has("authors")) {
                            val authorsJsonArray = volumeInfo.getJSONArray("authors")
                            authors = authorsJsonArray.join(", ")
                        }

                        var categories = ""
                        if (volumeInfo.has("categories")) {
                            val authorsJsonArray = volumeInfo.getJSONArray("categories")
                            categories = authorsJsonArray.join(", ")
                        }

                        val publisher = volumeInfo.getString("publisher")
                        val published = volumeInfo.getString("publishedDate")
                        val description = volumeInfo.getString("description")
                        val pageCount = volumeInfo.getInt("pageCount")
                        val language = volumeInfo.getString("language")

                        val accessInfoJSONObject = bookJson.getJSONObject("accessInfo")
                        val pdfJSONObject = accessInfoJSONObject.getJSONObject("pdf")
                        val isPdfAvailable = pdfJSONObject.getBoolean("isAvailable")
                        var pdfLink = ""
//                        if (isPdfAvailable) pdfLink = pdfJSONObject.getString("acsTokenLink")

                        booksList.add(
                            BookModel(
                                id,
                                title,
                                description,
                                publisher,
                                published,
                                authors,
                                categories,
                                imgUrl,
                                previewUrl,
                                pageCount,
                                language,
                                isPdfAvailable,
                                pdfLink
                            )
                        )

                        books.value = booksList
                    }catch (jsonException:JSONException){
                        onFailure("Book not found")
                    }

                }
            }

            override fun onFailure(failureMessage: String) {
//                failed load
                MainActivity.showToast(App.getInstance().applicationContext, failureMessage)
            }

        })
    }


}

