package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {
    override fun compareTo(other: MyDate): Int = when {
        year != other.year -> year - other.year
        month != other.month -> month - other.month
        else -> dayOfMonth - other.dayOfMonth
    }
}

operator fun MyDate.rangeTo(other: MyDate): DateRange = DateRange(this, other)
operator fun MyDate.plus(interval: TimeInterval): MyDate = addTimeIntervals(interval, 1)
operator fun MyDate.plus(interval: RepeatedTimeInterval): MyDate = addTimeIntervals(interval.ti, interval.n)

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR
}

operator fun TimeInterval.times(amount: Int): RepeatedTimeInterval = RepeatedTimeInterval(this, amount)

class DateRange(val start: MyDate, val endInclusive: MyDate) : Iterable<MyDate> {
    operator fun contains(date: MyDate): Boolean = start <= date && date <= endInclusive
    override fun iterator(): Iterator<MyDate> = object : Iterator<MyDate> {
        var nextDate: MyDate = start
        override fun next(): MyDate {
            val result = nextDate
            nextDate = nextDate.addTimeIntervals(TimeInterval.DAY, 1)

            return result
        }

        override fun hasNext(): Boolean {
            return nextDate <= endInclusive
        }
    }
}

class RepeatedTimeInterval(val ti: TimeInterval, val n: Int)
