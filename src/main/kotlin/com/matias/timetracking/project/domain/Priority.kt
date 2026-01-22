package com.matias.timetracking.project.domain

enum class Priority(val value: Int) {
    LOW(0), MEDIUM(1), HIGH(2);
    companion object {
        fun parse(priorityValue: Int) = Priority.entries.find { it.value == priorityValue }
    }
}