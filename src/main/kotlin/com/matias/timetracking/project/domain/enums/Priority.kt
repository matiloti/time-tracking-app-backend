package com.matias.timetracking.project.domain.enums

enum class Priority(val id: Int) {
    INVALID(-1),
    LOW(1),
    MEDIUM(2),
    HIGH(3),
    ;

    fun isInvalid() = this == INVALID
    companion object {
        fun parse(priorityId: Int) = entries.find { it.id == priorityId } ?: INVALID
    }
}
