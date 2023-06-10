package com.example.oneclick_attendance.JavaClasses.kotlin

import com.example.oneclick_attendance.JavaClasses.StudResObj


class CSVConvert {
    public fun <T> csvOf(
        headers: List<String>,
        data: List<T>,
        itemBuilder: (T) -> List<String>
    ) = buildString {
        append(headers.joinToString(","){"\"$it\""})
        append("\n")
        data.forEach { item -> append(itemBuilder(item).joinToString(","){"\"$it\""})
        append("\n")
        }
    }
}

fun main() {
    val a = StudResObj("19L-2346", true);
    val b = StudResObj("19L-3333", false);
    val stds = listOf(a,b)
    val csv = CSVConvert().csvOf(listOf("RollNo","Status"),stds){
        listOf(it.rollNo.toString(), it.isPresent.toString())
    }
    println(csv)
}