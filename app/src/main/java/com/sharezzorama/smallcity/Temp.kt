/*
import TimeInterval.*

fun main(args: Array<String>) {
    println("Hello, World!")
    */
/*val date1 = MyDate(2019, 1, 1)
    val date2 = MyDate(2019, 1, 31)
    println("date 1: $date1, date 2: $date2")
    val inDate = MyDate(2019, 1, 20)
    println("Check if date $inDate in range: ${checkInRange(inDate, date1, date2)}")
    val outDate = MyDate(2019, 2, 20)
    println("Check if date $outDate in range: ${checkInRange(outDate, date1, date2)}")*//*

    val startDate = MyDate(2019, 1, 1)
    val endDate = MyDate(2019, 1, 10)
    val rangeTo = MyDate(2019, 1, 1).rangeTo(MyDate(2019, 1, 10))
    for (currentDate in startDate.rangeTo(endDate)) {
        println("Current Date = $currentDate")
    }
}


data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int)

enum class TimeInterval { DAY, WEEK, YEAR }

operator fun MyDate.plus(timeInterval: TimeInterval): MyDate {
    timeInterval
}

fun task1(today: MyDate): MyDate {
    return today + YEAR + WEEK
}

fun task2(today: MyDate): MyDate {
    return today + YEAR * 2 + WEEK * 3 + DAY * 5
}

*/
/*



    fun main(args: Array<String>) {
        var open = mu
        val stack = mutableListOf<String>
        val input = "{}"
        input.forEach { char->

        }
    }
}

public class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {

    override fun contains(value: MyDate): Boolean {
        return start <= value && value <= endInclusive
    }

    override fun iterator(): Iterator<MyDate> {
        return object : Iterator<MyDate> {
            var currentDate: MyDate? = null

            override fun hasNext() = if (currentDate == null) true else currentDate!! < endInclusive

            override fun next(): MyDate {
                if (currentDate == null) {
                    currentDate = start
                } else {
                    currentDate = currentDate?.nexDay()
                }
                return currentDate!!
            }

        }
    }
}

operator fun MyDate.rangeTo(other: MyDate) = DateRange(this, other)

fun checkInRange(date: MyDate, first: MyDate, last: MyDate): Boolean {
    return date in DateRange(first, last)
}

fun MyDate.nexDay() = if (dayOfMonth < 31)
    MyDate(year, month, dayOfMonth + 1)
else
    MyDate(year, month + 1, 1)

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int {
        if (year < other.year)
            return -1
        else if (year > other.year)
            return 1
        else if (month < other.month)
            return -1
        else if (month > other.month)
            return 1
        else if (dayOfMonth < other.dayOfMonth)
            return -1
        else if (dayOfMonth > other.dayOfMonth)
            return 1
        else return 0
    }
}
*/
