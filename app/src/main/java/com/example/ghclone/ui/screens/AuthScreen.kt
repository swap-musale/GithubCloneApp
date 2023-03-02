package com.example.ghclone.ui.screens

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ghclone.R
import com.example.ghclone.UIState
import com.example.ghclone.ui.theme.GHCloneTheme

@OptIn(ExperimentalAnimationApi::class, ExperimentalTextApi::class)
@Composable
fun AuthScreen(
    authState: UIState<Unit>,
    authenticate: () -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .paint(
                        painter = painterResource(
                            id = if (isSystemInDarkTheme()) R.drawable.github_logo_night
                            else R.drawable.github_logo_day
                        )
                    )
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = authenticate,
                enabled = authState !is UIState.Loading,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.onSurface
                )
            ) {
                AnimatedContent(
                    targetState = authState is UIState.Loading
                ) { isLoading ->
                    if (isLoading) CircularProgressIndicator(
                        modifier = Modifier.size(16.dp),
                        color = MaterialTheme.colorScheme.onSurface,
                        strokeWidth = 2.dp
                    )
                    else Text(
                        text = "SIGN IN WITH GITHUB",
                        color = MaterialTheme.colorScheme.surface,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = buildAnnotatedString {
                    val linkStyle = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline
                    )
                    append("By signing in you accept our ")
                    withStyle(style = linkStyle) {
                        withAnnotation(urlAnnotation = UrlAnnotation("www.github.com")) {
                            append("Terms of use")
                        }
                    }
                    append(" and ")
                    withAnnotation(urlAnnotation = UrlAnnotation("www.github.com")) {
                        withStyle(style = linkStyle) { append("Privacy policy") }
                    }
                    append(".")
                },
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Preview(uiMode = UI_MODE_NIGHT_NO)
@Composable
private fun AuthScreenPreview() {
    GHCloneTheme {
        Surface {
            AuthScreen(authState = UIState.Empty) {}
        }
    }
}
