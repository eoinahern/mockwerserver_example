package ie.eoinahern.questionoftheday

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Quote(
    val quote: String,
    val author: String
)