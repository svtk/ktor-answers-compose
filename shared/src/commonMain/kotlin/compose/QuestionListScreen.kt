package compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import model.Question
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import viewmodel.QuestionsViewModel


class QuestionListScreen : Screen, KoinComponent {
    private val questionsViewModel: QuestionsViewModel by inject()

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        QuestionListView(
            questions = questionsViewModel.questions.collectAsState().value,
            submitQuestion = {
                navigator.push(NewQuestionScreen())
            },
            navigateToQuestion = { question ->
                navigator.push(QuestionScreen(question))
            }
        )
    }
}

@Composable
fun QuestionListView(
    questions: List<Question>,
    submitQuestion: () -> Unit,
    navigateToQuestion: (Question) -> Unit
) {
    Column {
        Row(
            modifier = Modifier.padding(10.dp).fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "Top Questions",
                style = MaterialTheme.typography.h5
            )
            Button(
                onClick = submitQuestion
            ) {
                Text("Ask Question")
            }
        }
        LazyColumn {
            items(questions) { question ->
                QuestionCard(question, navigateToQuestion)
            }
        }
    }
}

@Composable
fun QuestionCard(
    question: Question,
    navigateToQuestion: (Question) -> Unit
) {
    Card(Modifier
        .fillMaxWidth()
        .padding(10.dp)
        .clickable { navigateToQuestion(question) }
    ) {
        Column(Modifier.padding(horizontal = 5.dp, vertical = 3.dp)) {
            Text(
                text = question.title.replaceFirstChar(Char::titlecase), // TODO: temporary
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold)
            )
            Row {
                Column(modifier = Modifier.fillMaxWidth(0.3f)) {
                    Text("${question.upVoteCount - question.downVoteCount} votes")
                    Text("${question.answers.size} answers")
                    Text("${question.comments.size} comments")
                }
                val body = if (question.body.length > 150) question.body.substring(0..150) + " ..." else question.body
                Text(body)
            }
            Text(
                text = "${question.owner.displayName} asked on ${question.creationDate.date}",
                style = MaterialTheme.typography.overline,
            )
        }
    }
}
