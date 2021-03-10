package tech.blur.lab5.api

data class AnswerModel(
    val questionId: QuestionId,
    val choiceIds: List<Long>,
    val arbitraryValue: String? = null,
    val answeredAutomatically: Boolean
)