package dz.akram.bensalem.tp04.utils

import androidx.recyclerview.widget.DiffUtil
import dz.akram.bensalem.tp04.models.TweetItem

/**
 * Created by `Akram Bensalem` on 06/04/2022.
 * This class is used to compare two lists of [TweetItem]s.
 * It is used to update the list of [TweetItem]s in the [TweetListAdapter]
 * when the list of [TweetItem]s is updated.
 * @param oldList the list of [TweetItem]s before the update
 * @param newList the list of [TweetItem]s after the update
 * @return a [DiffUtil.Callback] object
 * This is the faster way and the most efficient to update the recycler view
 */

class DiffUtils(
    private val oldList : List<TweetItem>,
    private val newList : List<TweetItem>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
      return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return  oldList[oldItemPosition].id == newList[newItemPosition].id
                && oldList[oldItemPosition].tweetText == newList[newItemPosition].tweetText
                && oldList[oldItemPosition].date == newList[newItemPosition].date
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }
}
