package com.example.vamzaplikacia.logika.internet

import com.example.vamzaplikacia.logika.knihy.VyhladaniAutori
import com.example.vamzaplikacia.logika.knihy.AutorData
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Interface s metódami pre http klienta.
 */
interface OpenLibraryAPI {
    @GET("authors/{author_id}.json")
    fun getAutorInfo(@Path("author_id") autorId: String): Call<AutorData>

    @GET("/search/authors.json")
    fun hladajAutorov(@Query("q") autorMeno: String): Call<VyhladaniAutori>
}

/**
 *Objekt pre Retrofit Builder
 */
object RetrofitAPI {
    private const val BASE_URL = "https://openlibrary.org/"

    val api: OpenLibraryAPI by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenLibraryAPI::class.java)
    }
}