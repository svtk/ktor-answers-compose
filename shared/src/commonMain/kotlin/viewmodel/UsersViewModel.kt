package viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import model.User
import remote.QuestionsRepository

class UsersViewModel(
    private val questionsRepository: QuestionsRepository
): CommonViewModel() {

    private val _users: MutableStateFlow<List<User>> =
        MutableStateFlow(listOf())
    val users: StateFlow<List<User>> get() = _users

    init {
        viewModelScope.launch {
            val data = questionsRepository.getUsers()
            _users.value = data
        }
    }
}