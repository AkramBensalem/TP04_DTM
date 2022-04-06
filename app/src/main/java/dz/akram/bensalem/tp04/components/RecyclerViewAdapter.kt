package dz.akram.bensalem.tp04.components

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dz.akram.bensalem.tp04.R
import dz.akram.bensalem.tp04.models.TweetItem
import dz.akram.bensalem.tp04.utils.DiffUtils

/**
 * @author akram-bensalem on 2019-10-03
 * @project tp04
 * recycled view adapter for the recycler view
 * @property dataList the list of tweet items
 * @property onItemClickListener the listener for the item click
 * @property onItemLongClickListener the listener for the item long click
 */

class RecyclerViewAdapter(
    val onItemClickListener: (TweetItem) -> Unit,
    val onItemLongClickListener: (Long) -> Unit
) : RecyclerView.Adapter<RecyclerViewAdapter.CustomViewModel>() {


    var dataList: MutableList<TweetItem> = mutableListOf()

    fun setData(newList: List<TweetItem>) {
        val diffUtilCallback = DiffUtils(newList = newList, oldList =  dataList)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback,true)
        dataList.clear()
        dataList.addAll(newList) // set the new list
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewModel {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.tweet_item_layout, parent, false)
        return CustomViewModel(
            itemView = view,
            onClick = onItemClickListener,
            onLongClick = onItemLongClickListener
        )
    }

    override fun onBindViewHolder(holder: CustomViewModel, position: Int) {
        holder.onBindView(dataList[position], position) // bind the view to the data item at the position in the list of tweet items
    }

    override fun getItemCount(): Int {
        return dataList.size // return the size of the list of tweet items
    }


    class CustomViewModel(
        itemView: View,
        val onClick: (TweetItem) -> Unit = {},
        val onLongClick: (Long) -> Unit = {}
    ) : RecyclerView.ViewHolder(itemView) {

        companion object {
            var  lastPosition = 0
        }

        private var profileImage: ImageView? = null
        private var tweetText: TextView? = null
        private var tweetUserName: TextView? = null
        private var layoutItem: CardView? = null



        init {
            // get the views
            profileImage = itemView.findViewById(R.id.iv_tweet_profile)
            tweetText = itemView.findViewById(R.id.tv_tweet_text)
            tweetUserName = itemView.findViewById(R.id.tv_tweet_user_name)
            layoutItem = itemView.findViewById(R.id.card_view_tweet_item)
        }

        // bind the view to the data item
        fun onBindView(item: TweetItem, position: Int) {

            profileImage?.setImageResource(item.profileImage)
            tweetText?.text = item.tweetText
            tweetUserName?.text = item.userName

            layoutItem?.setOnClickListener {
                onClick(item)
            }

            layoutItem?.setOnLongClickListener {
                onLongClick(item.id)
                true
            }

            // animate the item if it is the first time it is being displayed
            setAnimation(itemView, position)
        }


        private fun setAnimation(
            viewToAnimate: View,
            position: Int
        ) {
            if (position > lastPosition) {
                val animation = AnimationUtils.loadAnimation (viewToAnimate.context, android.R.anim.slide_in_left)
                viewToAnimate.startAnimation(animation)
                lastPosition = position // update the last position
            }
        }

    }


}