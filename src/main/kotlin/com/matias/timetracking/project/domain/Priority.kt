package com.matias.timetracking.project.domain

enum class Priority(val value: Int) {
    INVALID(-1), LOW(0), MEDIUM(1), HIGH(2);
    fun isInvalid() = this == INVALID
    companion object {
        fun parse(priorityValue: Int) = Priority.entries.find { it.value == priorityValue } ?: INVALID
    }
}