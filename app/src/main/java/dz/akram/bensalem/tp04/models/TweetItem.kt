package dz.akram.bensalem.tp04.models

import android.os.Parcelable
import androidx.annotation.DrawableRes
import dz.akram.bensalem.tp04.R
import kotlinx.parcelize.Parcelize
import java.util.*

/**
 * @author Bensalem Akram on 06/04/2022
 * @project tp04
 * @email ak.bensalem@esi-sba.dz
 * A TweetItem represents a tweet.
 * @property id The id of the tweet.
 * @property text The text of the tweet.
 * @property date The date of the tweet.
 * @property user The user who tweeted.
 * @property image The image of the tweet.
 */

@Parcelize
data class TweetItem(
    val id: Long = 0,
    @DrawableRes val profileImage: Int = R.drawable.profile_image,
    val userName: String ="Akram BenSalem",
    val tweetText: String = "",
    val date: Date = Date()
): Parcelable
