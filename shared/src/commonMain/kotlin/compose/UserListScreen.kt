package compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import model.User
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import viewmodel.UsersViewModel

class UserListScreen : Screen, KoinComponent {
    private val usersViewModel: UsersViewModel by inject()

    @Composable
    override fun Content() {
        UserListView(
            users = usersViewModel.users.collectAsState().value
        )
    }
}

@Composable
fun UserListView(
    users: List<User>
) {
    Column {
        Text(
            text = "The current users",
            style = MaterialTheme.typography.h5
        )
        LazyColumn {
            item {
                Row {
                    TableCell(text = "Display Name", weight = 0.2f, caption = true)
                    TableCell(text = "Location", weight = 0.2f, caption = true)
                    TableCell(text = "About Me", weight = 0.4f, caption = true)
                    TableCell(text = "Creation Date", weight = 0.2f, caption = true)
                }
                Divider()
            }
            items(users) { user ->
                Row {
                    TableCell(text = user.displayName, weight = 0.2f)
                    TableCell(text = user.location, weight = 0.2f)
                    TableCell(text = user.aboutMe, weight = 0.4f)
                    TableCell(text = user.creationDate, weight = 0.2f)
                }
                Divider()
            }
        }
    }
}

@Composable
fun RowScope.TableCell(
    text: Any?,
    caption: Boolean = false,
    weight: Float = 0.2f
) {
    Text(
        text = text?.toString() ?: "",
        style = if (caption) MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold) else MaterialTheme.typography.body1,
        modifier = Modifier
            .weight(weight)
            .padding(4.dp)
    )
}