package com.example.ghclone.ui.home

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.domain.entities.FavoriteRepository
import com.example.domain.entities.RepositoryNode
import com.example.ghclone.R
import com.example.ghclone.ui.home.component.HomeTopHeaderView
import com.example.ghclone.ui.home.component.MyWorkOptionsView
import com.example.ghclone.ui.home.component.QuickShortcutsView
import com.example.ghclone.ui.theme.GHCloneTheme
import com.example.ghclone.utils.UIState

const val MY_WORK_VIEW_TAG = "my_work"
const val FAVORITE_REPO_VIEW_TAG = "favorite_repo"
const val CIRCULAR_PROGRESS_INDICATOR_TAG = "circular_progress_indicator"

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalFoundationApi::class,
)
@Composable
fun HomeScreen(favoriteRepositoryState: UIState<FavoriteRepository>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colorResource(id = R.color.white)),
    ) {
        LazyColumn {
            stickyHeader {
                HomeTopHeaderView(
                    onSearchClicked = {},
                    onCreateIssueClicked = {},
                )
            }

            item {
                MyWorkOptionsView(myWorkOptionsList = myWorkOptionsList)
            }

            item {
                Divider(
                    thickness = 0.6.dp,
                    color = colorResource(id = R.color.divider_grey),
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth(),
                )
            }

            item {
                FavoriteRepoSection(favoriteRepositoryState = favoriteRepositoryState)
            }

            item {
                QuickShortcutsView()
            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun FavoriteRepoSection(favoriteRepositoryState: UIState<FavoriteRepository>) {
    AnimatedContent(
        modifier = Modifier.fillMaxWidth(),
        targetState = favoriteRepositoryState,
    ) { uiState ->
        when (uiState) {
            is UIState.Empty -> Unit
            is UIState.Loading -> {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 100.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(size = 30.dp)
                            .testTag(tag = CIRCULAR_PROGRESS_INDICATOR_TAG),
                    )
                }
            }
            is UIState.Failure -> Text(text = uiState.exception.localizedMessage.orEmpty())
            is UIState.Success -> {
                val starredRepoList =
                    uiState.data.nodes.filter { it.hasViewerStarred == true }
                FavoriteRepositoryView(starredRepoList = starredRepoList)
            }
        }
    }
}

@Composable
fun FavoriteRepositoryView(starredRepoList: List<RepositoryNode>) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 15.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.padding(start = 20.dp),
                text = stringResource(id = R.string.str_favorite),
                color = colorResource(id = R.color.black),
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
            )

            if (starredRepoList.isNotEmpty()) {
                Spacer(modifier = Modifier.weight(weight = 1f))
                Image(
                    modifier = Modifier.padding(end = 20.dp),
                    painter = painterResource(id = R.drawable.ic_more_horizontal),
                    colorFilter = ColorFilter.tint(color = colorResource(id = R.color.grey)),
                    contentDescription = "ic_more_horizontal",
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .testTag(tag = FAVORITE_REPO_VIEW_TAG),
        ) {
            starredRepoList.forEach { repositoryNode ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    if (LocalInspectionMode.current) {
                        Image(
                            modifier = Modifier
                                .padding(start = 20.dp)
                                .size(size = 35.dp)
                                .clip(shape = CircleShape),
                            painter = painterResource(id = R.drawable.ic_repositories),
                            contentDescription = null,
                        )
                    } else {
                        AsyncImage(
                            modifier = Modifier
                                .padding(start = 20.dp)
                                .size(size = 35.dp)
                                .clip(shape = CircleShape),
                            model = repositoryNode.owner.avatarUrl,
                            contentDescription = null,
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 10.dp),
                    ) {
                        Text(
                            text = repositoryNode.owner.login,
                            color = colorResource(id = R.color.grey),
                        )
                        Text(
                            text = repositoryNode.name,
                            modifier = Modifier.padding(top = 2.dp),
                        )
                    }
                }
            }
        }

        if (starredRepoList.isEmpty()) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 15.dp),
                text = stringResource(id = R.string.add_favorite_repository_display_message),
                color = colorResource(id = R.color.grey),
                textAlign = TextAlign.Center,
            )

            Button(
                colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.white)),
                shape = RoundedCornerShape(size = 6.dp),
                elevation = ButtonDefaults.elevatedButtonElevation(),
                border = BorderStroke(
                    width = 0.5.dp,
                    color = colorResource(id = R.color.divider_grey),
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                onClick = { },
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 4.dp),
                    text = stringResource(id = R.string.str_add_favorite),
                    color = colorResource(id = R.color.blue),
                )
            }
        }

        Divider(
            thickness = 0.6.dp,
            color = colorResource(id = R.color.divider_grey),
            modifier = Modifier
                .padding(top = 20.dp)
                .fillMaxWidth(),
        )
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = 0xFF000000,
)
@Composable
fun FavoriteRepositoryViewPreview() {
    GHCloneTheme {
        FavoriteRepositoryView(starredRepoList = emptyList())
    }
}
