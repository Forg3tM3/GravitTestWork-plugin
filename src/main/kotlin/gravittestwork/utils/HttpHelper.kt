package gravittestwork.utils

import okhttp3.Response
import java.io.IOException

class HttpHelper(val response: Response) {
    val body: String = response.body!!.string()

    inline fun <reified T>  getOrThrow(): T {
        if (!response.isSuccessful)
            throw IOException(response.toString())

        return Requester.gson.fromJson(body, T::class.java)
    }
}