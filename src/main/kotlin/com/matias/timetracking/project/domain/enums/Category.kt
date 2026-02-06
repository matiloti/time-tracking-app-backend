package com.matias.timetracking.project.domain.enums

enum class Category(val id: Int) {
    INVALID(-1), SOFTWARE(0), CONTENT_CREATION(1), LEARNING(2);
    fun isInvalid() = this == INVALID
    companion object {
        fun parse(categoryId: Int) = entries.find { it.id == categoryId } ?: INVALID
    }
}