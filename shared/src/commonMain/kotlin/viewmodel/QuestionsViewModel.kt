package viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import model.Question
import remote.QuestionsRepository

class QuestionsViewModel(
    private val questionsRepository: QuestionsRepository
): CommonViewModel() {

    private val _questions: MutableStateFlow<List<Question>> =
        MutableStateFlow(listOf())
    val questions: StateFlow<List<Question>> get() = _questions

    init {
        viewModelScope.launch {
            val data = questionsRepository.getQuestions()
            _questions.value = data
                // TODO support different ways of sorting in UI
                .sortedByDescending { it.lastActivityDate }

        }
    }
}