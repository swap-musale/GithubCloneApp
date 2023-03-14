package com.example.ghclone.ui.auth

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.UrlAnnotation
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withAnnotation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.ghclone.R
import com.example.ghclone.ui.theme.GHCloneTheme
import com.example.ghclone.utils.UIState

@OptIn(
    ExperimentalAnimationApi::class,
    ExperimentalTextApi::class,
)
@Composable
fun AuthScreen(
    authState: UIState<String>,
    initAuthFlow: () -> Unit,
    navigateToHome: () -> Unit,
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = authState) {
        if (authState is UIState.Success) {
            navigateToHome()
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 16.dp)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(space = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Box(
                modifier = Modifier
                    .weight(weight = 1f)
                    .paint(
                        painter = painterResource(
                            id = if (isSystemInDarkTheme()) {
                                R.drawable.github_logo_night
                            } else {
                                R.drawable.github_logo_day
                            },
                        ),
                    ),
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth(),
                onClick = initAuthFlow,
                enabled = authState !is UIState.Loading,
                shape = RoundedCornerShape(size = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onSurface),
            ) {
                AnimatedContent(
                    targetState = authState is UIState.Loading,
                ) { isLoading ->
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(size = 16.dp),
                            color = MaterialTheme.colorScheme.onSurface,
                            strokeWidth = 2.dp,
                        )
                    } else {
                        Text(
                            text = stringResource(R.string.str_sign_in_with_github),
                            color = MaterialTheme.colorScheme.surface,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }
            }
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = buildAnnotatedString {
                    val linkStyle = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        textDecoration = TextDecoration.Underline,
                    )
                    append(stringResource(R.string.by_signing_accept_conditions))
                    withStyle(style = linkStyle) {
                        withAnnotation(urlAnnotation = UrlAnnotation("www.github.com")) {
                            append(context.getString(R.string.str_terms_of_use))
                        }
                    }
                    append(" and ")
                    withAnnotation(urlAnnotation = UrlAnnotation("www.github.com")) {
                        withStyle(style = linkStyle) {
                            append(context.getString(R.string.str_provacy_policy))
                        }
                    }
                    append(".")
                },
                textAlign = TextAlign.Center,
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
            AuthScreen(authState = UIState.Empty, initAuthFlow = {}) {}
        }
    }
}
