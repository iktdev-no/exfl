package no.iktdev.exfl.time

class TimeUtil {
    companion object {
        fun toMilli(time: Long): Long {
            return time * 1000L
        }
    }
}