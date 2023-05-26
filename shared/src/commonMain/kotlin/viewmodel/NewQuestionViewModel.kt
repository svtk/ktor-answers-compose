package viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import model.Question
import remote.QuestionsRepository

class NewQuestionViewModel(
    private val questionsRepository: QuestionsRepository
): CommonViewModel() {

    private val _newQuestion: MutableStateFlow<Question?> =
        MutableStateFlow(null)
    val newQuestion: StateFlow<Question?> get() = _newQuestion

    fun submitQuestion(
        title: String,
        body: String,
        tags: String
    ) {
        viewModelScope.launch {
            _newQuestion.value = questionsRepository.submitQuestion(title, body, tags)
        }
    }
}