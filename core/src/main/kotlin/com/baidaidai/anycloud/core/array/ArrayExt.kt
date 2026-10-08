package com.baidaidai.anycloud.core.array

fun <T> Array<T>.formatAsMultilineString(): String{
    var formattedText = ""
    for (i in 0..size-1){
        formattedText = formattedText + get(i) + ",\n\n"
    }
    return formattedText
}