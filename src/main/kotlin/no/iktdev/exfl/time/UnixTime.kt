package no.iktdev.exfl.time

import no.iktdev.extender.time.UnixTimestamp

class UnixTime {
    companion object {
        fun now() = UnixTimestamp(System.currentTimeMillis() / 1000L)
    }
}