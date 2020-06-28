package com.example.fincar.network.books_api

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

object BooksApiRequest {
    
    private const val BOOKS_API_BASE_URL = "https://www.googleapis.com/"
    private const val QUERY_PARAM = "q"
    private const val MAX_RESULTS = "maxResults"
    private const val PRINT_TYPE = "printType"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(BOOKS_API_BASE_URL)
        .build()

    private val service = retrofit.create(
        BooksApiService::class.java)


    fun getBooksRequest(queryString: String, requestCallBacks: RequestCallBacks) {
        val call = service.getBooksRequestWithQuery(queryString)

        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) requestCallBacks.onSuccess(response.body().toString())
                else if (response.code() == 404)
                    requestCallBacks.onError("No books found")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                requestCallBacks.onError(t.toString())
            }

        })
    }

    interface BooksApiService {
        @GET("books/v1/volumes")
        fun getBooksRequestWithQuery(
            @Query(QUERY_PARAM) queryString: String,
            @Query(MAX_RESULTS) maxResults: String = "10",
            @Query(PRINT_TYPE) printType: String = "books"
        ): Call<String>


    }
}