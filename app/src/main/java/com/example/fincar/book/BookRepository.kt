package com.example.fincar.book

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.fincar.App
import com.example.fincar.network.BooksApiRequest
import org.json.JSONObject

class BookRepository {
    private val books = MutableLiveData<List<BookModel>>()

    fun getBooks(q: String): MutableLiveData<List<BookModel>> {
        searchBook(q)
        return books
    }


    private fun searchBook(q: String) {
        BooksApiRequest.getBooksRequest(q,object :BooksApiRequest.RequestCallBacks{
            override fun onSuccess(successJson: String) {
                val rootJsonObject = JSONObject(successJson)
                // Get the JSONArray of books items.
                val itemsArray = rootJsonObject.getJSONArray("items")
                val booksList = ArrayList<BookModel>()

                for (i in 0 until itemsArray.length()){
                    val bookJson = itemsArray.getJSONObject(i)
                    val volumeInfo = bookJson.getJSONObject("volumeInfo")
                    val previewUrl = volumeInfo.getString("previewLink")

                    val title = volumeInfo.getString("title")
                    val imageLinksJsonObject = volumeInfo.getJSONObject("imageLinks")
                    val imgUrl = imageLinksJsonObject.getString("smallThumbnail")
                    val authorsList = arrayListOf<String>()

                    if (volumeInfo.has("authors")) {
                        val authorsJsonArray = volumeInfo.getJSONArray("authors")
                        for (authorIndex in 0 until authorsJsonArray.length()) {
                            val authorString = authorsJsonArray.getString(authorIndex)
                            authorsList.add(authorString)
                        }
                    }

                    booksList.add(BookModel(title,authorsList,
                        imgUrl,previewUrl))

                }

                books.value = booksList
            }

            override fun onFailure(failureMessage: String) {
//                Toast.makeText(App.getInstance(),failureMessage,Toast.LENGTH_LONG).show()
            }

        })
    }
}