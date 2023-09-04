package com.example.ghclone.ui

import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.ghclone.R
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
        var buttonText = ""
        composeTestRule.setContent {
            buttonText = stringResource(id = R.string.str_sign_in_with_github)
            AuthScreen(authState = UIState.Empty, initAuthFlow = {}, navigateToHome = {})
        }

        composeTestRule.onNodeWithText(text = buttonText).performClick()
        composeTestRule.onNodeWithText(text = buttonText).assertDoesNotExist()
    }
}
