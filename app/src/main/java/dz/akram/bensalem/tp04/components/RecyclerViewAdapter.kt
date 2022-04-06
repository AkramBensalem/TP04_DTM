package dz.akram.bensalem.tp04.components

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import dz.akram.bensalem.tp04.R
import dz.akram.bensalem.tp04.models.TweetItem


class RecyclerViewAdapter(
   val onClick: (TweetItem) -> Unit,
   val onLongClick: (Long) -> Unit
) : RecyclerView.Adapter<RecyclerViewAdapter.CustomViewModel>() {

    var dataList: List<TweetItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewModel {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.tweet_item_layout, parent, false)
        return CustomViewModel(
            itemView = view,
            onClick = onClick,
            onLongClick = onLongClick
        )
    }

    override fun onBindViewHolder(holder: CustomViewModel, position: Int) {
        holder.onBindView(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }


    class CustomViewModel(
        itemView: View,
        val onClick: (TweetItem) -> Unit = {},
        val onLongClick: (Long) -> Unit = {}
    ) : RecyclerView.ViewHolder(itemView) {
        private var profileImage: ImageView? = null
        private var tweetText: TextView? = null
        private var tweetUserName: TextView? = null
        private var layoutItem: CardView? = null

        init {
            profileImage = itemView.findViewById(R.id.iv_tweet_profile)
            tweetText = itemView.findViewById(R.id.tv_tweet_text)
            tweetUserName = itemView.findViewById(R.id.tv_tweet_user_name)

            layoutItem = itemView.findViewById(R.id.card_view_tweet_item)
        }

        fun onBindView(item: TweetItem) {
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
        }
    }


}