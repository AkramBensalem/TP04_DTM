package dz.akram.bensalem.tp04.screens

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dz.akram.bensalem.tp04.R
import dz.akram.bensalem.tp04.models.TweetItem
import dz.akram.bensalem.tp04.utils.toTextWithTime
import java.util.*

class TweetDetailActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private var tweet: TweetItem? = null
    private lateinit var textToSpeech: TextToSpeech // Text to speech engine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweet_detail)

        tweet = intent.getParcelableExtra("tweetObject") // get the tweet object from the intent

        textToSpeech = TextToSpeech(this, this)

        // setup the views
        val mUserNameTextView = findViewById<TextView>(R.id.tv_tweet_detail_user_name)
        val mTweetTextView = findViewById<TextView>(R.id.tv_tweet_detail_text)
        val mImageUser = findViewById<ImageView>(R.id.iv_tweet_detail_avatar)
        val mTweetDateView = findViewById<TextView>(R.id.tv_tweet_detail_date)
        val mPlayButton = findViewById<ImageView>(R.id.iv_tweet_detail_sound)

        tweet?.let { item ->  // if the tweet is not null then set the content of the views
            mUserNameTextView.text = item.userName
            mTweetTextView.text = item.tweetText
            mImageUser.setImageResource(item.profileImage)
            mTweetDateView.text = item.date.toTextWithTime() // convert the date to a string
            mPlayButton.setOnClickListener {
                speak(item.tweetText) // launch the text to speech engine
            }
        }

        textToSpeech.setOnUtteranceProgressListener(object : UtteranceProgressListener() {  // listen to the text to speech engine
            override fun onDone(utteranceId: String) { // when the text is finished speaking update the button image
                mPlayButton.setImageResource(R.drawable.ic_round_play_arrow_24)
            }

            @Deprecated("Deprecated in Java") // Deprecated in Java
            override fun onError(utteranceId: String) {
                mPlayButton.setImageResource(R.drawable.ic_round_play_arrow_24)
                Toast.makeText(this@TweetDetailActivity, "Error", Toast.LENGTH_SHORT).show()
            }

            override fun onStart(utteranceId: String) { // when the text is starting to speak return the button image as default
                mPlayButton.setImageResource(R.drawable.ic_round_surround_sound_24)
            }
        })


    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = textToSpeech.setLanguage(Locale.US) // support English Tweeter
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "This Language is not supported", Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, "Initialization Failed!", Toast.LENGTH_SHORT).show()
        }
    }


    private fun speak(text: String) {
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, "id")
    }

    override fun onDestroy() {
        textToSpeech.apply { // stop the text to speech engine and clean the resources
            stop()
            shutdown()
        }
        super.onDestroy()
    }

    override fun onBackPressed() { // handle the back button press
        super.onBackPressed()
        finish()
    }

    override fun onSupportNavigateUp(): Boolean { // add the back button to the action bar
        onBackPressed()
        return true
    }


}