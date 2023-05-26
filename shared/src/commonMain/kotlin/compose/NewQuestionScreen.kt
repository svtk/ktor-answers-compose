package compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import viewmodel.NewQuestionViewModel

class NewQuestionScreen : Screen, KoinComponent {
    private val newQuestionViewModel: NewQuestionViewModel by inject()

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        NewQuestionView { title, body, tags ->
            newQuestionViewModel.submitQuestion(
                title,
                body,
                tags
            )
            navigator.push(NewAddedQuestionScreen())
        }
    }
}

@Composable
fun NewQuestionView(
    onQuestionSubmit: (title: String, body: String, tags: String) -> Unit
) {
    Column(modifier = Modifier.padding(10.dp)) {
        var title by rememberSaveable { mutableStateOf("") }
        var body by rememberSaveable { mutableStateOf("") }
        var tags by rememberSaveable { mutableStateOf("") }
        Text(
            text = "Enter the question details",
            style = MaterialTheme.typography.h6
        )
        Item(
            label = "Title",
            value = title,
            onValueChange = { title = it },
        )
        Item(
            label = "Description",
            value = body,
            onValueChange = { body = it },
        )
        Item(
            label = "Tags",
            value = tags,
            onValueChange = { tags = it },
        )
        Button(
            onClick = {
                onQuestionSubmit(title, body, tags)
                title = ""
                body = ""
                tags = ""
            },
            enabled = title.isNotBlank() && body.isNotBlank()// && tags.isNotBlank()
        ) {
            Text("Submit Question")
        }
    }
}

@Composable
private fun Item(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        label = { Text(label) },
    )
}
