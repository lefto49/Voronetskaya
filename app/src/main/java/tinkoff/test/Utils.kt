package tinkoff.test

import com.google.gson.Gson
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

class Utils {
    private val api = "https://kinopoiskapiunofficial.tech";
    private val topEndPoint = "/api/v2.2/films/top";
    private val descriptionEndPoint = "/api/v2.2/films/"
    private val searchEndPoint = "/api/v2.1/films/search-by-keyword"
    private val topType = "TOP_100_POPULAR_FILMS";
    private val parameter = "type"
    private val keyHeader = "X-API-KEY"
    private val key = "e30ffed0-76ab-4dd6-b41f-4c9da2b2735b";

    fun getTopFilms(): Array<Film>? {
        val client = OkHttpClient()
        val url =
            HttpUrl.parse(api + topEndPoint)!!.newBuilder().addQueryParameter(parameter, topType)
                .build()
        val request = Request.Builder().url(url).addHeader(keyHeader, key).build()
        val response = client.newCall(request).execute()

        if (response.code() != 200 || response.body() == null) {
            return null
        }

        val resp = Gson().fromJson(response.body()!!.string(), CustomResponse::class.java)
        return resp.films
    }

    fun getFilmById(id: Long): Film? {
        val client = OkHttpClient()
        val url =
            HttpUrl.parse(api + descriptionEndPoint + id.toString())!!.newBuilder().build()
        val request = Request.Builder().url(url).addHeader(keyHeader, key).build()
        val response = client.newCall(request).execute()

        if (response.code() != 200 || response.body() == null) {
            return null
        }

        val film = Gson().fromJson(response.body()!!.string(), Film::class.java)
        if (film != null) {
            film.genreString = film.genres.joinToString()
            film.countryString = film.countries.joinToString()
        }

        return film
    }

    fun getFilmsByKeyword(keyword: String): Array<Film>? {
        val client = OkHttpClient()
        val url =
            HttpUrl.parse(api + searchEndPoint)!!.newBuilder()
                .addQueryParameter("keyword", keyword)
                .build()
        val request = Request.Builder().url(url).addHeader(keyHeader, key).build()
        val response = client.newCall(request).execute()

        if (response.code() != 200 || response.body() == null) {
            return null
        }

        val resp = Gson().fromJson(response.body()!!.string(), CustomResponse::class.java)
        return resp.films
    }
}