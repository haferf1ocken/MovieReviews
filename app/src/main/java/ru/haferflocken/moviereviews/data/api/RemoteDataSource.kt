package ru.haferflocken.moviereviews.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.haferflocken.moviereviews.BuildConfig

object RemoteDataSource {

    private const val BASE_URL = "https://api.nytimes.com/svc/movies/v2/"

    fun <Api> buildApi(
        api: Class<Api>
    ): Api {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(getRetrofitClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(api)
    }



    private fun getRetrofitClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .also { client ->
                client.hostnameVerifier { _, _ -> true }
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(logging)
                }
            }.build()
    }
}