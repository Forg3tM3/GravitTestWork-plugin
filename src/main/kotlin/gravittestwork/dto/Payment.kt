package gravittestwork.dto

import kotlin.properties.Delegates

class Payment {
    var id: Int = 0
    lateinit var username: String
    var amount: Float = 0.toFloat()
}