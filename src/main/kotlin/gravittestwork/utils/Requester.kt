package gravittestwork.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.util.concurrent.TimeUnit

class Requester {
    companion object {
        val gson: Gson = GsonBuilder().create()
        var client: OkHttpClient = OkHttpClient
            .Builder()
            .connectTimeout(5, TimeUnit.SECONDS)
            .writeTimeout(5, TimeUnit.SECONDS)
            .readTimeout(5, TimeUnit.SECONDS)
            .callTimeout(10, TimeUnit.SECONDS)
            .build()
        val JSON = "application/json; charset=utf-8".toMediaType()
    }

    @Throws(IOException::class)
    fun <T>post(url: String, data: T): HttpHelper {
        val body = gson.toJson(data).toRequestBody(JSON)
        val request = Request.Builder().url(url).post(body).build()

        return client.newCall(request).execute().use { response ->
            HttpHelper(response)
        }
    }

    @Throws(IOException::class)
    fun <T>patch(url: String, data: T): HttpHelper {
        val body = gson.toJson(data).toRequestBody(JSON)
        val request = Request.Builder().url(url).patch(body).build()

        return client.newCall(request).execute().use { response ->
            HttpHelper(response)
        }
    }

    @Throws(IOException::class)
    fun get(url: String): HttpHelper {
        val request = Request.Builder().url(url).build()

        return client.newCall(request).execute().use { response ->
            HttpHelper(response)
        }
    }

    @Throws(IOException::class)
    fun delete(url: String): HttpHelper {
        val request = Request.Builder().url(url).delete().build()

        return client.newCall(request).execute().use { response ->
            HttpHelper(response)
        }
    }
}