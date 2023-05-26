package compose

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Tag
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp

@Composable
fun BottomBar(
    navigateToQuestions: () -> Unit,
    navigateToTags: () -> Unit,
    navigateToUsers: () -> Unit,
) {
    val selectedIndex = remember { mutableStateOf(0) }
    BottomNavigation(elevation = 10.dp) {

        BottomNavigationItem(icon = {
            Icon(imageVector = Icons.Default.Public,"Questions")
        },
            label = { Text(text = "Questions") },
            selected = (selectedIndex.value == 0),
            onClick = {
                selectedIndex.value = 0
                navigateToQuestions()
            })

        BottomNavigationItem(icon = {
            Icon(imageVector = Icons.Default.Tag,"Tags")
        },
            label = { Text(text = "Tags") },
            selected = (selectedIndex.value == 1),
            onClick = {
                selectedIndex.value = 1
                navigateToTags()
            })

        BottomNavigationItem(icon = {
            Icon(imageVector = Icons.Default.Person,"Users")
        },
            label = { Text(text = "Users") },
            selected = (selectedIndex.value == 2),
            onClick = {
                selectedIndex.value = 2
                navigateToUsers()
            })
    }
}
