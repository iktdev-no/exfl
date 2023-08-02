package no.iktdev.exfl.time

class TimeUtil {
    companion object {
        fun toMilli(time: Long): Long {
            return time * 1000L
        }
    }

    class UnixTime {
        companion object {
            fun now() = System.currentTimeMillis() / 1000L

            fun reduceDays(time: Int, days: Int): Int {
                return time - (days * 86400)
            }

            fun reduceHours(time: Int, hours: Int): Int {
                return time - (hours * 3600)
            }
        }

    }

}