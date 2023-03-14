package com.example.githubcloneapp.ui

import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ghclone.ui.auth.AuthScreen
import com.example.ghclone.utils.UIState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AuthScreenTest {

    @get:Rule
    val composeTestRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun userClicksOnSignInOption_OpenCCT() {
        composeTestRule.setContent {
            AuthScreen(authState = UIState.Empty, initAuthFlow = {}, navigateToHome = {})
        }

        composeTestRule.onNodeWithText("SIGN IN WITH GITHUB").performClick()
        composeTestRule.onNodeWithText("SIGN IN WITH GITHUB").assertDoesNotExist()
    }
}
