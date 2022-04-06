package dz.akram.bensalem.tp04.components

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import dz.akram.bensalem.tp04.R

/**
 * @author akram bensalem on 06/04/2022.
 */
class DeleteAlertDialog(
    val onConfirm : () -> Unit
) : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity.let {

            val builder = AlertDialog
                .Builder(it, R.style.AlertDialogStyle)
                .setTitle(R.string.delete_dialog_title)
                .setMessage(R.string.delete_alert_dialog_message)
                .setPositiveButton("Ok") { _, _ ->
                    onConfirm()
                }
                .setNegativeButton("Annuler") { dialog, _ ->
                    dialog.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}