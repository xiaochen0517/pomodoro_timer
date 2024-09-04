package utils

class DateUtil {
    companion object {

        fun getNowSimpleDate(): String {
            val date = java.util.Date()
            val dateFormat = java.text.SimpleDateFormat("yyyy-MM-dd")
            return dateFormat.format(date)
        }

        fun getNowSimpleTime(): String {
            val date = java.util.Date()
            val dateFormat = java.text.SimpleDateFormat("HH:mm:ss")
            return dateFormat.format(date)
        }

        fun getNowSimpleDateTime(): String {
            val date = java.util.Date()
            val dateFormat = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return dateFormat.format(date)
        }
    }
}
