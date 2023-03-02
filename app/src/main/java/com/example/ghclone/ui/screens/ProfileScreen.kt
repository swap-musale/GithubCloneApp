package com.example.ghclone.ui.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.domain.entities.User
import com.example.ghclone.UIState

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ProfileScreen(
    userState: UIState<User>,
    fetchUser: () -> Unit
) {
    LaunchedEffect(Unit) { fetchUser() }

    AnimatedContent(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(),
        targetState = userState
    ) { state ->
        when (state) {
            is UIState.Empty -> Unit
            is UIState.Loading -> CircularProgressIndicator()
            is UIState.Failure -> Text(text = "Error: ${state.exception.message}")
            is UIState.Success -> {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val user = remember { state.data }
                    AsyncImage(
                        modifier = Modifier
                            .size(100.dp)
                            .clip(RoundedCornerShape(16.dp)),
                        model = user.avatarUrl,
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = buildAnnotatedString {
                            if (user.name.isNotBlank()) {
                                append("Welcome to GHClone ")
                                withStyle(SpanStyle(color = MaterialTheme.colorScheme.primary)) {
                                    append(user.name)
                                }
                            }
                            if (user.email.isNotBlank()) append(", your e-mail is: ${user.email}")
                        }
                    )
                }
            }
        }
    }
}