package dz.akram.bensalem.tp04.components

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import dz.akram.bensalem.tp04.R

class CustomTweetDialog(
     val onTweet : (String) -> Unit
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {

            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater;


            builder.setView(inflater.inflate(R.layout.custom_tweet_dialog, null))
                .setPositiveButton("Tweetez") { dialog, _ ->
                   onTweet(
                       (dialog as AlertDialog).findViewById<TextView>(R.id.tweet_text).text.toString()
                   )
                }
                .setNegativeButton("Annuler") { dialog, _ ->
                    dialog.cancel()
                }
            builder.create()
       } ?: throw IllegalStateException("Activity cannot be null")
    }
}