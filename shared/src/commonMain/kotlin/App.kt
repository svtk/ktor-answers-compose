import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import compose.BottomBar
import compose.QuestionListScreen
import compose.UserListScreen

@Composable
fun App() {
    MaterialTheme {
        Navigator(QuestionListScreen()) { navigator ->
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Kotlin Answers in Compose") },
                        backgroundColor = MaterialTheme.colors.background,
                    )
                },
                bottomBar = {
                    BottomBar(
                        navigateToQuestions = {
                            navigator.push(QuestionListScreen())
                        },
                        navigateToTags = { },
                        navigateToUsers = {
                          navigator.push(UserListScreen())
                        },
                    )
                }
            ) {
                CurrentScreen()
            }
        }
    }
}