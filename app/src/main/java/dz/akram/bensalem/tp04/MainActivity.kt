package dz.akram.bensalem.tp04

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dz.akram.bensalem.tp04.components.CustomTweetDialog
import dz.akram.bensalem.tp04.components.RecyclerViewAdapter
import dz.akram.bensalem.tp04.models.TweetItem
import dz.akram.bensalem.tp04.viewModels.MainScreenViewModel
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {

    private val mViewModel: MainScreenViewModel by viewModels() // add layer of abstraction to provide data to the view

    lateinit var recyclerView: RecyclerView // lateinit is used to avoid null pointer exception

    private val adapter: RecyclerViewAdapter by lazy { RecyclerViewAdapter(
        onClick = {
            val intent = Intent(this, TweetDetailActivity::class.java)
            startActivity(intent)
        },
        onLongClick = {
            mViewModel.deleteTweet(it)
            adapter.dataList = mViewModel.tweetsList.value
        }
    ) } // initialize the adapter only when it is needed and only once to better performance

    private val customTweetDialog by lazy { // initialize the dialog only when it is needed and only once to better performance with custom callback function when user click 'Tweeter'
        CustomTweetDialog(onTweet = {
            mViewModel.addNewTweet(it)  // pass the new tweet to the view model for persistence
            adapter.dataList = mViewModel.tweetsList.value // notify the adapter that the data has changed
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen() // install the splash screen to the launch activity
        setContentView(R.layout.activity_main)

        // setup the recycler view
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // setup the menu
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.tweet_item -> {
                showTweetFragment() // show the tweet dialog
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showTweetFragment() {
        customTweetDialog.show(supportFragmentManager, "custom_tweet_dialog")
    }
}