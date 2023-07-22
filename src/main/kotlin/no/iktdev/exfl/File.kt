package no.iktdev.exfl

import java.io.File

fun File.using(vararg paths: String): File {
    var file = this
    paths.forEach { file = File(file, it) }
    return file
}