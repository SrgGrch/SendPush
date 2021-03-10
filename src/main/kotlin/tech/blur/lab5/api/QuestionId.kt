package tech.blur.lab5.api

/**
 * Unique question identifier.
 */
data class QuestionId(
    val surveyId: String,
    val sectionId: Long,
    val blockId: Long,
    val questionId: Long
)