package dz.akram.bensalem.tp04.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dz.akram.bensalem.tp04.R
import dz.akram.bensalem.tp04.models.TweetItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainScreenViewModel : ViewModel() {

    private val _tweetSample by lazy {
        TweetItem(
            id = 0,
            userName = "Akram Bensalem",
            tweetText = "",
            profileImage = R.drawable.profile_image,
        )
    }

    private var tweetId: Long = 1

    private val _tweetsList = MutableStateFlow<List<TweetItem>>(listOf())
    val tweetsList: StateFlow<List<TweetItem>> = _tweetsList.asStateFlow()


    fun addNewTweet(tweet: String) { //add layer of protection
        viewModelScope.launch { // launch a new coroutine in viewModelScope to not bloc UI
            if (tweet.isNotEmpty() && tweet.isNotBlank()) { // check if tweet is not empty
                _tweetsList.value = tweetsList.value.plus( //add new tweet to the list of tweets
                    _tweetSample.copy(
                        tweetText = tweet,
                        id = (tweetId + 1)
                    )
                )
                tweetId++
            }
        }
    }

    fun deleteTweet(id: Long) {
        tweetsList.value.find { item -> item.id == id }?.let {
            _tweetsList.value = tweetsList.value.minus(it)
        }
    }
}