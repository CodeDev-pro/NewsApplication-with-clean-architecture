package com.codedev.newsapplication.presentation.utils

import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

fun String.isoToDateConverter(): String {
    val format = DateTimeFormatter.ISO_INSTANT
    val accessor = format.parse(this)
    val instant = Instant.from(accessor)
    val localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate()
    return localDate.toDateFormat()
}

fun LocalDate.toDateFormat(): String {
    val formattedDate = format(DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG))
    val splits = formattedDate.split(" ").toMutableList()
    val date = StringBuilder()
    date.append("${splits[0]} ${splits[1]}, ${splits[2]}")
    return date.toString()
}


fun getCurrentDate(): LocalDate {
    return LocalDate.now()
}


