package com.codedev.newsapplication.presentation.utils

import java.text.SimpleDateFormat
import java.util.*

fun String.isoToDateConverter(): Date? {
    val dtStart = "2020-07-05T09:27:37Z"
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
    return format.parse(dtStart)
}

fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

fun getCurrentDateTime(): Date {
    return Calendar.getInstance().time
}

fun getCurrentTime(): String {
    val date = getCurrentDateTime()
    return date.toString("yyyy/MM/dd HH:mm:ss")
}