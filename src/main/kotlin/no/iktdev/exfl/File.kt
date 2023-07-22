package no.iktdev.exfl

import java.io.File

fun File.using(root: String, vararg paths: String): File {
    var file = File(root)
    paths.forEach { file = File(file, it) }
    return file
}