package com.matias.timetracking.project.domain.enums

enum class Priority(val id: Int) {
    INVALID(-1), LOW(0), MEDIUM(1), HIGH(2);
    fun isInvalid() = this == INVALID
    companion object {
        fun parse(priorityId: Int) = entries.find { it.id == priorityId } ?: INVALID
    }
}