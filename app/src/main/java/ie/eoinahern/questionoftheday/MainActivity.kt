package ie.eoinahern.questionoftheday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import okhttp3.OkHttpClient


class MainActivity : AppCompatActivity() {

    private lateinit var restApi: QuestionOfTheDayRestService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        createApi()
        getQuoteBtn.setOnClickListener {
            getQuoteFromApi()
        }
    }

    private fun createApi() {

        val client = OkHttpClient()
        restApi = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(QuestionOfTheDayRestService::class.java)
    }

    fun showLoading() {
        progbar.visibility = View.VISIBLE
    }

    fun hideLoading() {
        progbar.visibility = View.GONE
    }

    private fun setQuoteText(str: String?) {
        quoteText.text = str
    }

    private fun getQuoteFromApi() {
        showLoading()
        quoteText.text = ""
        val call = restApi.getQuoteOfTheDay()

        call.enqueue(object : Callback<QuestionOfTheDayResponse> {
            override fun onFailure(call: Call<QuestionOfTheDayResponse>, t: Throwable) {
                hideLoading()
                setQuoteText("Loading failed!!")
            }

            override fun onResponse(
                call: Call<QuestionOfTheDayResponse>,
                response: Response<QuestionOfTheDayResponse>
            ) {
                hideLoading()
                if (response.isSuccessful) {
                    val quote = response.body()?.contents?.quotes?.get(0)
                    setQuoteText(quote?.quote)
                } else {
                    setQuoteText(response.message())
                }
            }
        })
    }
}
