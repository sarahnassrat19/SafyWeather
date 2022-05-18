package com.example.safyweather.utilities

import java.text.SimpleDateFormat
import java.time.Month
import java.util.*

class Converters {

    companion object {

        fun getDateFromInt(day: Int, month: Int,year:Int):String{
            var commingDate: Date = Date(year,month,day)
            var dateFormat: SimpleDateFormat = SimpleDateFormat("dd MMM")
            var formatedDate: String = dateFormat.format(commingDate)

            return formatedDate
        }

        fun getDateFormat(dtInTimeStamp: Int): String {

            var currentDate: Date = Date(dtInTimeStamp.toLong() * 1000)
            var dateFormat: SimpleDateFormat = SimpleDateFormat("EEE MMM d")
            var formatedDate: String = dateFormat.format(currentDate)

            return formatedDate

        }

        fun getDayFormat(dtInTimeStamp: Int): String {

            var currentDate: Date = Date(dtInTimeStamp.toLong() * 1000)
            var dateFormat: SimpleDateFormat = SimpleDateFormat("EEE")
            var formatedDate: String = dateFormat.format(currentDate)

            return formatedDate

        }

        fun getTimeFormat(dtInTimeStamp: Int): String {

            var currentTime: Date = Date(dtInTimeStamp.toLong() * 1000)
            var timeFormat: SimpleDateFormat = SimpleDateFormat("HH:mm")
            var formatedTime: String = timeFormat.format(currentTime)

            return formatedTime

        }
    }

}

/*fun main(){
    //var currentDate: Date = Date(1651871751000)
    /*var con=Converters()
    println(con.getDateFormat(1651871751))
    println(con.getTimeFormat(1651871751))*/
    println(Date(1651993200000))
    println(Date(1651996800000))
    println(Date(1652000400000))
    println(Date(1652004000000))
}*/