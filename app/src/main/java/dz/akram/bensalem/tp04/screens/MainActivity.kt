package dz.akram.bensalem.tp04.screens

import android.app.ActivityOptions
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dz.akram.bensalem.tp04.R
import dz.akram.bensalem.tp04.components.CustomTweetDialog
import dz.akram.bensalem.tp04.components.DeleteAlertDialog
import dz.akram.bensalem.tp04.components.RecyclerViewAdapter
import dz.akram.bensalem.tp04.viewModels.MainScreenViewModel
import android.util.Pair as UtilPair

class MainActivity : AppCompatActivity() {


    lateinit var mImageView: ImageView // for the animation of the user Avatar

    private val mViewModel: MainScreenViewModel by viewModels() // add layer of abstraction to provide data to the view

    private lateinit var mRecyclerView: RecyclerView // `lateinit` is used to avoid null pointer exception

    private val adapter: RecyclerViewAdapter by lazy { // define thz adapter in a lazy way with the `clicks listener callbacks
        RecyclerViewAdapter(
            onItemClickListener = { item ->
                val intent = Intent(this, TweetDetailActivity::class.java)
                intent.putExtra(
                    "tweetObject",
                    item
                ) // TweetItem object must be serialized to be passed to the next activity
                val options = ActivityOptions.makeSceneTransitionAnimation( // For Animation Purpose
                    this,
                    UtilPair.create(
                        findViewById<ImageView>(R.id.iv_tweet_profile),
                        "transition_avatar"
                    ),
                    UtilPair.create(
                        findViewById<TextView>(R.id.tv_tweet_user_name),
                        "transition_user_name"
                    ),
                    UtilPair.create(findViewById<TextView>(R.id.tv_tweet_text), "transition_text")
                )
                startActivity(intent, options.toBundle())
            },
            onItemLongClickListener = { id ->
                showConfirmAlertDialogFragment(id)
                handleUI()
            }
        )
    } // initialize the adapter only when it is needed and only once to better performance

    private val customTweetDialog by lazy { // initialize the dialog only when it is needed and only once to better performance with custom callback function when user click 'Tweeter'
        CustomTweetDialog(
            onTweet = {
                mViewModel.addNewTweet(it)  // pass the new tweet to the view model for persistence
                handleUI()
            }
        )
    }


    /**
     * `onCreate` is the first function called when the activity is created
     * @param savedInstanceState is null for the first time which helps to launch the splashScreen
     *                           and then the activity is recreated when the device is rotated
     *                           and the savedInstanceState is not null
     *                           which means that the SplashScreen is already launched
     *                           The SplashScreen is not launched again
     *
     * @param installSplashScreen is used to launch the SplashScreen before the activity is created
     * this is the new way to launch the SplashScreen and the recommended way from Google
     * for better performance and better user experience
     */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            val splashScreen = installSplashScreen() // install the splash screen to the launch activity
            splashScreen.setOnExitAnimationListener { splashScreenViewProvider ->  // start the animation when the splash screen is launched
                splashScreenViewProvider.iconView
                    .animate()
                    .setDuration(400L)
                    .rotation(360f)
                    .alpha(0f)
                    .withEndAction {
                        splashScreenViewProvider.remove()
                        setContentView(R.layout.activity_main)
                        setUpContentView()
                    }.start()
            }
        } else {
            setTheme(R.style.Theme_TP04) // needs to be set before the content view is set to avoid the crash
            setContentView(R.layout.activity_main)
            setUpContentView()
        }

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

    private fun showConfirmAlertDialogFragment(id: Long) {
        DeleteAlertDialog(
            onConfirm = { // the callback function when user click 'OK'
                mViewModel.deleteTweet(id)
                handleUI()
            }
        ).show(supportFragmentManager, "delete_tweet_dialog")
    }

    private fun handleUI() {
        adapter.setData(newList = mViewModel.tweetsList)
        // handle the UI when the data is changed
        if (mViewModel.tweetsList.isNullOrEmpty()) {
            mImageView.visibility = View.VISIBLE
            mRecyclerView.visibility = View.GONE
        } else {
            mImageView.visibility = View.GONE
            mRecyclerView.visibility = View.VISIBLE
        }
    }

    private fun setUpContentView() {
        mImageView = findViewById(R.id.iv_no_tweets)

        // setup the recycler view
        mRecyclerView = findViewById(R.id.recyclerView)
        mRecyclerView.adapter = adapter
        mRecyclerView.layoutManager = LinearLayoutManager(this)

        handleUI()
    }
}