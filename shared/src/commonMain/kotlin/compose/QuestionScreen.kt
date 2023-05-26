package compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import model.Answer
import model.Post
import model.PostComment
import model.Question
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import viewmodel.NewQuestionViewModel

data class QuestionScreen(val question: Question) : Screen {
    @Composable
    override fun Content() {
        QuestionView(question)
    }
}

class NewAddedQuestionScreen : Screen, KoinComponent {
    private val newQuestionViewModel: NewQuestionViewModel by inject()

    @Composable
    override fun Content() {
        NewAddedQuestionView()
    }

    @Composable
    fun NewAddedQuestionView() {
        val newQuestion = newQuestionViewModel.newQuestion.collectAsState().value
        if (newQuestion != null) {
            QuestionView(newQuestion)
        } else {
            Text("Your question is submitted! Please wait for a while to see it")
        }
    }
}

@Composable
fun QuestionView(question: Question) {
    LazyColumn(modifier = Modifier.padding(10.dp)) {
        item {
            Text(
                text = question.title,
                style = MaterialTheme.typography.h5
            )
        }
        item {
            PostView(question)
            Divider()
        }
        item {
            val answersText = if (question.answers.size == 1) "answer" else "answers"
            Text(
                text = "${question.answers.size} $answersText",
                style = MaterialTheme.typography.h5
            )
        }
        items(question.answers) { answer ->
            PostView(answer)
            Divider()
        }
    }
}

@Composable
fun PostView(post: Post) {
    Row {
        Box(modifier = Modifier.width(40.dp)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                val color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f)
                Icon(
                    imageVector = Icons.Filled.ArrowDropUp,
                    contentDescription = "Vote Up",
                    modifier = Modifier.size(40.dp),
                    tint = color,
                )
                Text(
                    text = "${post.upVoteCount - post.downVoteCount}",
                    style = MaterialTheme.typography.subtitle1,
                    color = color,
                )
                Icon(
                    imageVector = Icons.Filled.ArrowDropDown,
                    contentDescription = "Vote Down",
                    modifier = Modifier.size(40.dp),
                    tint = color,
                )
                if (post is Answer && post.accepted) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Accepted",
                        tint = Color.Green // TODO dark green
                    )
                }
            }
        }
        Column {
            Spacer(modifier = Modifier.height(10.dp))
            // TODO temporary cropping answer body
            val body = if (post is Answer) post.body.substring(0..150) else post.body.trim()
            Text(text = body)
            val askedOrAnswered = when (post) {
                is Question -> "asked"
                is Answer -> "answered"
            }
            Text(
                text = "$askedOrAnswered ${post.creationDate} by ${post.owner.displayName}",
                modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                style = MaterialTheme.typography.overline,
                textAlign = TextAlign.End
            )
            Divider()
            for (comment in post.comments) {
                CommentView(
                    modifier = Modifier.padding(start = 10.dp),
                    comment = comment,
                )
                Divider()
            }
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun CommentView(modifier: Modifier, comment: PostComment) {
    Text(
        modifier = modifier.padding(vertical = 2.dp),
        // TODO cropping comment body
        text = comment.body.substring(0..80) + " - " + comment.owner.displayName,
        style = MaterialTheme.typography.caption
    )
}
