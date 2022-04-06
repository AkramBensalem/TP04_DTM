package dz.akram.bensalem.tp04.utils

import java.util.*


// Convert Date to String in format "dayName monthName dd HH:mm:ss yyyy timeZone"

/**
 * Created by `Akram Bensalem` on 06/04/2022.
 * Convert Date to String in format "dayName monthName dd HH:mm:ss yyyy timeZone"
 * @param date Date to convert
 * @return String in format "dayName monthName dd HH:mm:ss yyyy timeZone"
 */
fun Date.toTextWithTime(): String {
    val calendar = Calendar.getInstance()

    calendar.time = this
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val dayName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())

    val monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())

    val year = calendar.get(Calendar.YEAR)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)
    val second = calendar.get(Calendar.SECOND)

    // get timeZone name on GMT+1
    val timeZone = calendar.timeZone.getDisplayName(false, TimeZone.SHORT)

    return "$dayName $monthName $day $hour:$minute:$second $year $timeZone"
}