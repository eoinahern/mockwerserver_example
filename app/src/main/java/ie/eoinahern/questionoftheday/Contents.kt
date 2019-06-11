package ie.eoinahern.questionoftheday

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class Contents(
    val quotes: List<Quote>
)