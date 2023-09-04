package com.example.ghclone.ui

import androidx.compose.ui.res.stringResource
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
        var titleText = ""
        composeTestRule.setContent {
            titleText = stringResource(id = R.string.home_toolbar_title)
            HomeScreen(favoriteRepositoryState = UIState.Empty)
        }
        composeTestRule.onNode(hasText(text = titleText))
    }

    @Test
    fun onAppLaunch_isMyWorkSectionVisible() {
        var myWorkText = ""
        composeTestRule.setContent {
            myWorkText = stringResource(id = R.string.str_my_work)
            HomeScreen(favoriteRepositoryState = UIState.Empty)
        }

        composeTestRule.onNodeWithText(text = myWorkText).assertExists()
    }

    @Test
    fun onAppLaunch_ifMyWorkListEmpty() {
        var myWorkText = ""
        composeTestRule.setContent {
            myWorkText = stringResource(id = R.string.str_my_work)
            MyWorkOptionsView(myWorkOptionsList = emptyList())
        }

        composeTestRule.onNodeWithText(text = myWorkText).assertExists()
        composeTestRule.onNodeWithTag(testTag = MY_WORK_VIEW_TAG)
            .onChildren()
            .assertCountEquals(0)
    }

    @Test
    fun onAppLaunch_myWorkVerifyFirstOption() {
        var issuesText = ""
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
            issuesText = stringResource(id = R.string.str_issues)
            MyWorkOptionsView(myWorkOptionsList = myWorkOptionsList)
        }

        composeTestRule.onNodeWithTag(testTag = MY_WORK_VIEW_TAG)
            .onChildren()
            .onFirst()
            .assert(hasText(text = issuesText))
    }

    @Test
    fun favoriteRepoListAvailable_onAppLaunch_listItemsAreVisible() {
        val starredRepoList = arrayListOf<RepositoryNode>()
        starredRepoList.add(RepositoryNode("", "Android", "", Owner("", "")))
        starredRepoList.add(RepositoryNode("", "iOS", "", Owner("", "")))

        composeTestRule.setContent {
            FavoriteRepositoryView(starredRepoList = starredRepoList)
        }

        composeTestRule.onNodeWithTag(testTag = FAVORITE_REPO_VIEW_TAG)
            .onChildren()
            .assertAny(hasText(text = "Android"))
    }

    @Test
    fun favoriteRepoListEmpty_onAppLaunch_listItemsAreNotVisible() {
        var addFavoriteButtonText = ""
        composeTestRule.setContent {
            addFavoriteButtonText = stringResource(id = R.string.str_add_favorite)
            FavoriteRepositoryView(starredRepoList = emptyList())
        }

        composeTestRule.onNodeWithTag(testTag = FAVORITE_REPO_VIEW_TAG)
            .onChildren()
            .assertCountEquals(0)

        composeTestRule.onNodeWithText(text = addFavoriteButtonText).assertExists()
    }

    @Test
    fun onAppLaunch_isShortCutsSectionVisible() {
        var shortcutsText = ""
        composeTestRule.setContent {
            shortcutsText = stringResource(id = R.string.str_shortcuts)
            HomeScreen(favoriteRepositoryState = UIState.Empty)
        }

        composeTestRule.onNodeWithText(text = shortcutsText).assertExists()
    }
}
