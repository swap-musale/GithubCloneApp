package com.example.githubcloneapp.ui

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.domain.entities.Owner
import com.example.domain.entities.RepositoryNode
import com.example.ghclone.R
import com.example.ghclone.ui.home.FAVORITE_REPO_VIEW_TAG
import com.example.ghclone.ui.home.FavoriteRepositoryView
import com.example.ghclone.ui.home.HomeScreen
import com.example.ghclone.ui.home.MY_WORK_VIEW_TAG
import com.example.ghclone.ui.home.WorkOption
import com.example.ghclone.ui.home.WorkType
import com.example.ghclone.ui.home.component.MyWorkOptionsView
import com.example.ghclone.utils.UIState
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule: ComposeContentTestRule = createComposeRule()

    @Test
    fun homeScreen_isHeaderTitleCorrect() {
        composeTestRule.setContent {
            HomeScreen(favoriteRepositoryState = UIState.Empty)
        }
        composeTestRule.onNode(hasText("Home"))
    }

    @Test
    fun onAppLaunch_isMyWorkSectionVisible() {
        composeTestRule.setContent {
            HomeScreen(favoriteRepositoryState = UIState.Empty)
        }

        composeTestRule.onNodeWithText("My Work").assertExists()
    }

    @Test
    fun onAppLaunch_ifMyWorkListEmpty() {
        composeTestRule.setContent {
            MyWorkOptionsView(myWorkOptionsList = emptyList())
        }

        composeTestRule.onNodeWithText("My Work").assertExists()
        composeTestRule.onNodeWithTag(testTag = MY_WORK_VIEW_TAG)
            .onChildren()
            .assertCountEquals(0)
    }

    @Test
    fun onAppLaunch_myWorkVerifyFirstOption() {
        val myWorkOptionsList = listOf(
            WorkOption(
                title = "Issues",
                icon = R.drawable.ic_circle_dot,
                workType = WorkType.ISSUES.type,
                backgroundColor = R.color.green,
            ),
            WorkOption(
                title = "Pull Requests",
                icon = R.drawable.ic_pull_requests,
                workType = WorkType.PULL_REQUESTS.type,
                backgroundColor = R.color.blue1,
            ),
        )
        composeTestRule.setContent {
            MyWorkOptionsView(myWorkOptionsList = myWorkOptionsList)
        }

        composeTestRule.onNodeWithTag(testTag = MY_WORK_VIEW_TAG)
            .onChildren()
            .onFirst()
            .assert(hasText("Issues"))
    }

    @Test
    fun onAppLaunch_ifFavoriteRepoListAvailable() {
        val starredRepoList = arrayListOf<RepositoryNode>()
        starredRepoList.add(RepositoryNode("", "Android", "", Owner("", "")))
        starredRepoList.add(RepositoryNode("", "iOS", "", Owner("", "")))

        composeTestRule.setContent {
            FavoriteRepositoryView(starredRepoList = starredRepoList)
        }

        composeTestRule.onNodeWithTag(testTag = FAVORITE_REPO_VIEW_TAG)
            .onChildren()
            .assertAny(hasText("Android"))
    }

    @Test
    fun onAppLaunch_ifFavoriteRepoListNotAvailable() {
        composeTestRule.setContent {
            FavoriteRepositoryView(starredRepoList = emptyList())
        }

        composeTestRule.onNodeWithTag(testTag = FAVORITE_REPO_VIEW_TAG)
            .onChildren()
            .assertCountEquals(0)

        composeTestRule.onNodeWithText(text = "ADD FAVORITES").assertExists()
    }

    @Test
    fun onAppLaunch_isShortCutsSectionVisible() {
        composeTestRule.setContent {
            HomeScreen(favoriteRepositoryState = UIState.Empty)
        }

        composeTestRule.onNodeWithText("Shortcuts").assertExists()
    }
}
