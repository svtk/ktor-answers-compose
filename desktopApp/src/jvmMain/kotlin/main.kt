import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import di.initKoin

fun main() = application {
    initKoin()
    Window(
        title = "Kotlin Answers in Compose",
        onCloseRequest = ::exitApplication
    ) {
        MainView()
    }
}