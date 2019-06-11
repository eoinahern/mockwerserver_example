package ie.eoinahern.questionoftheday

import com.squareup.moshi.JsonClass


@JsonClass(generateAdapter = true)
data class QuestionOfTheDayResponse(
    val contents: Contents
)
