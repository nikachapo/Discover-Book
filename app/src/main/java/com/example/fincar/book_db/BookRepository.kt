package com.example.fincar.book_db

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.fincar.app.App
import com.example.fincar.app.Tools.showToast
import com.example.fincar.models.book.GoogleBook
import com.example.fincar.network.books_api.BooksApiRequest
import com.example.fincar.network.books_api.RequestCallBacks
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class BookRepository(application: Application?) {
    private var bookDao: BookDao? = null

    private val books = MutableLiveData<List<GoogleBook>>()

    fun getBooks(q: String): MutableLiveData<List<GoogleBook>> {
        searchBook(q)
        return books
    }

    init {
        if (application != null) {
            val database = BookDatabase.getBookDatabase(application)
            bookDao = database?.noteDao()

        }


    }

    fun insert(googleBook: GoogleBook) {
        CoroutineScope(Dispatchers.IO).launch {
            bookDao?.insert(googleBook)
        }

    }

    fun update(googleBook: GoogleBook) {
        CoroutineScope(Dispatchers.IO).launch {
            bookDao?.update(googleBook)
        }
    }

    fun delete(googleBook: GoogleBook) {
        CoroutineScope(Dispatchers.IO).launch {
            bookDao?.delete(googleBook)
        }
    }

    fun getStarredBooks(): LiveData<List<GoogleBook>>? {
        return bookDao?.getAllBooks()
    }

    fun getBookCountLiveData(id: String): LiveData<Int>? {
        return bookDao?.bookCount(id)
    }

    private fun searchBook(q: String) {
        BooksApiRequest.getBooksRequest(q, object : RequestCallBacks {
            override fun onSuccess(successJson: String) {
                addBooksToList(successJson, this)
            }

            override fun onError(message: String) {
                showToast(App.getInstance().applicationContext, message)
            }

        })
    }

    fun getBooksWithPDF(): LiveData<List<GoogleBook>> {
        return bookDao!!.getBooksWithPDF()
    }

    private fun addBooksToList(
        successJson: String,
        booksApiRequestCallBacks: RequestCallBacks
    ) {
        val rootJsonObject = JSONObject(successJson)
        // Get the JSONArray of books items.
        val booksList = ArrayList<GoogleBook>()
        val totalItems = rootJsonObject.getInt("totalItems")
        if (totalItems != 0) {
            val itemsArray = rootJsonObject.getJSONArray("items")

            for (i in 0 until itemsArray.length()) {
                val googleBook = GoogleBook()

                val bookJson = itemsArray.getJSONObject(i)
                googleBook.id = bookJson.getString("id")
                val volumeInfo = bookJson.getJSONObject("volumeInfo")
                googleBook.previewUrl = volumeInfo.getString("previewLink")
                googleBook.title = volumeInfo.getString("title")
                if (volumeInfo.has("publisher"))
                    googleBook.publisher = volumeInfo.getString("publisher")

                if (volumeInfo.has("publishedDate")) {
                    googleBook.publishDate = volumeInfo.getString("publishedDate")
                }

                if (volumeInfo.has("description")) {
                    googleBook.description = volumeInfo.getString("description")
                }

                val imageLinksJsonObject = volumeInfo.getJSONObject("imageLinks")
                googleBook.photoUrl = imageLinksJsonObject.getString("smallThumbnail")

                if (volumeInfo.has("authors")) {
                    val authorsJsonArray = volumeInfo.getJSONArray("authors")
                    googleBook.authors = authorsJsonArray.join(", ")
                }

                if (volumeInfo.has("categories")) {
                    val authorsJsonArray = volumeInfo.getJSONArray("categories")
                    googleBook.categories = authorsJsonArray.join(", ")
                }

                if (volumeInfo.has("pageCount")) googleBook.pageCount =
                    volumeInfo.getInt("pageCount")
                googleBook.language = volumeInfo.getString("language")
                val accessInfoJSONObject = bookJson.getJSONObject("accessInfo")
                val pdfJSONObject = accessInfoJSONObject.getJSONObject("pdf")
                googleBook.isPdfAvailable = pdfJSONObject.getBoolean("isAvailable")

                if (googleBook.isPdfAvailable) {
                    if (pdfJSONObject.has("downloadLink")) {
                        googleBook.pdfLink = pdfJSONObject.getString("downloadLink")
                    } else if (pdfJSONObject.has("acsTokenLink")) {
                        googleBook.pdfLink = pdfJSONObject.getString("acsTokenLink")
                    }
                }
                booksList.add(googleBook)

                books.value = booksList

            }
        } else {
            booksApiRequestCallBacks.onError("Book not found")
        }
    }


}

