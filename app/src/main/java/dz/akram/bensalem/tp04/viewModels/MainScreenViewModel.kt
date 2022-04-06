package dz.akram.bensalem.tp04.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dz.akram.bensalem.tp04.R
import dz.akram.bensalem.tp04.models.TweetItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

/**
 * @author `Akram Bensalem` on 06/04/2022.
 * This class is responsible for managing the state of the MainScreenViewModel
 * @property tweetsList a MutableList that holds the state of the tweets
 * @property addNewTweet adds a new tweet to the _tweetsList and notifies the observers inside the viewModelScope of the new tweet
 * @property deleteTweet deletes a tweet from the _tweetsList and notifies the observers inside the viewModelScope of the deleted tweet
 */


class MainScreenViewModel : ViewModel() {

    companion object{
        private var tweetId: Long = 0
    }


    private val _tweetsList : MutableList<TweetItem> = mutableListOf()
    val tweetsList: List<TweetItem> = _tweetsList


    fun addNewTweet(tweet: String) { //add layer of protection
        viewModelScope.launch { // launch a new coroutine in viewModelScope to not bloc UI
            if (tweet.isNotEmpty() && tweet.isNotBlank()) { // check if tweet is not empty
                _tweetsList.add( //add new tweet to the list of tweets
                    TweetItem().copy(
                        tweetText = tweet,
                        id = tweetId++,
                        date = Date()
                    )
                )
            }
        }
    }

    fun deleteTweet(id: Long) {
        viewModelScope.launch {
            tweetsList.find { item -> item.id == id }?.let {
                _tweetsList.remove(it)
            }
        }
    }
}