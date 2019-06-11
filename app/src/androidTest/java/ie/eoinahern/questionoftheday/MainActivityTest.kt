package ie.eoinahern.questionoftheday

import android.content.Context
import android.content.Intent
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader


@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var mockWebServer: MockWebServer
    private val context = InstrumentationRegistry.getInstrumentation().context

    @get:Rule
    val mainActivityTestRule = ActivityTestRule<MainActivity>(
        MainActivity::class.java,
        true, false
    )

    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        BASE_URL = mockWebServer.url("/").toString()
    }

    @Test
    fun test() {

        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody(getStringFromFile(context, "qod.json"))
        )

        val intent = Intent()
        mainActivityTestRule.launchActivity(intent)


        onView(withId(R.id.getQuoteBtn)).perform(click())
        onView(withText("I came from a real tough neighborhood. Once a guy pulled a knife on me. I knew he wasn't a professional, the knife had butter on it.")).check(
            matches(isDisplayed())
        )
    }

    /**
     * ideally these would be in a helper class.
     *
     *
     */


    private fun convertStreamToString(sr: InputStream): String {
        val reader = BufferedReader(InputStreamReader(sr))
        val sb = StringBuilder()

        val iterator = reader.lineSequence().iterator()
        while (iterator.hasNext()) {
            sb.append(iterator.next())
        }

        reader.close()
        return sb.toString()
    }

    private fun getStringFromFile(context: Context, filePath: String): String {
        val stream = context.resources.assets.open(filePath)
        val ret = convertStreamToString(stream)
        stream.close()
        return ret
    }
}