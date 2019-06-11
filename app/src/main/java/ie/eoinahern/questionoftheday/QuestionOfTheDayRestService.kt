package ie.eoinahern.questionoftheday

import retrofit2.Call
import retrofit2.http.GET


interface QuestionOfTheDayRestService {

    @GET("/qod.json")
    fun getQuoteOfTheDay(): Call<QuestionOfTheDayResponse>


}