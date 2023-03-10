package com.example.ghclone.ui.home

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.domain.entities.FavoriteRepository
import com.example.domain.entities.RepositoryNode
import com.example.ghclone.R
import com.example.ghclone.utils.UIState

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalAnimationApi::class,
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
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = colorResource(id = R.color.white),
                ) {
                    HomeTopHeaderView(
                        onSearchClicked = {},
                        onCreateIssueClicked = {},
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "My Work",
                        color = colorResource(id = R.color.black),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                    )
                    Spacer(modifier = Modifier.weight(weight = 1f))
                    Image(
                        modifier = Modifier.clickable {},
                        painter = painterResource(id = R.drawable.ic_more_horizontal),
                        colorFilter = ColorFilter.tint(color = colorResource(id = R.color.grey)),
                        contentDescription = null,
                    )
                }
            }

            items(items = myWorkOptionsList) { workOption ->
                MyWorkOptionItem(
                    myWorkOption = workOption,
                    onItemClicked = { workType ->
                        when (workType) {
                            WorkType.ISSUES.type -> {}
                            WorkType.PULL_REQUESTS.type -> {}
                            WorkType.DISCUSSIONS.type -> {}
                            WorkType.REPOSITORIES.type -> {}
                            WorkType.ORGANIZATIONS.type -> {}
                            WorkType.STARRED.type -> {}
                        }
                    },
                )
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
                                CircularProgressIndicator(modifier = Modifier.size(size = 30.dp))
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
            item { QuickShortcutsView() }
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
                text = "Favorites",
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
                    contentDescription = null,
                )
            }
        }

        if (starredRepoList.isNotEmpty()) {
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
        } else {
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
                    text = "ADD FAVORITES",
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

@Composable
fun QuickShortcutsView() {
    val shortcutList = remember {
        mutableListOf<ShortcutOption>().apply {
            add(ShortcutOption(icon = R.drawable.ic_circle_dot, iconColor = R.color.green))
            add(ShortcutOption(icon = R.drawable.ic_pull_requests, iconColor = R.color.blue1))
            add(ShortcutOption(icon = R.drawable.ic_discussion, iconColor = R.color.purple))
            add(ShortcutOption(icon = R.drawable.ic_repositories, iconColor = R.color.black_shade))
            add(ShortcutOption(icon = R.drawable.ic_organization, iconColor = R.color.orange))
            add(ShortcutOption(icon = R.drawable.ic_starred, iconColor = R.color.yellow))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 30.dp),
    ) {
        Text(
            modifier = Modifier.padding(start = 20.dp),
            text = "Shortcuts",
            color = MaterialTheme.colorScheme.onSurface,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 35.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            shortcutList.forEach {
                Box(
                    modifier = Modifier
                        .clip(shape = CircleShape)
                        .background(color = colorResource(id = it.iconColor).copy(alpha = 0.2f)),
                ) {
                    Image(
                        modifier = Modifier
                            .padding(all = 6.dp)
                            .size(size = 18.dp),
                        colorFilter = ColorFilter.tint(color = colorResource(id = it.iconColor)),
                        painter = painterResource(id = it.icon),
                        contentDescription = null,
                    )
                }
            }
        }

        Text(
            textAlign = TextAlign.Center,
            text = "The things you need, one tap away",
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
        )

        Text(
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.grey),
            text = "Fast access your lists if Issues, Pull Requests, or Discussions",
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
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
                .padding(vertical = 10.dp, horizontal = 20.dp),
            onClick = { },
        ) {
            Text(
                modifier = Modifier.padding(vertical = 4.dp),
                text = "GET STARTED",
                color = colorResource(id = R.color.blue),
            )
        }
    }
}

@Composable
fun MyWorkOptionItem(
    myWorkOption: WorkOption,
    onItemClicked: (workType: String) -> Unit,
) {
    Box(
        modifier = Modifier.clickable {
            onItemClicked(myWorkOption.workType)
        },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 15.dp, end = 15.dp, top = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(size = 6.dp))
                    .background(color = colorResource(id = myWorkOption.backgroundColor)),
            ) {
                Image(
                    modifier = Modifier
                        .padding(all = 8.dp)
                        .size(size = 20.dp),
                    colorFilter = ColorFilter.tint(color = colorResource(id = R.color.white)),
                    painter = painterResource(id = myWorkOption.icon),
                    contentDescription = myWorkOption.title,
                )
            }

            Text(
                text = myWorkOption.title,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 12.dp),
            )
        }
    }
}

@Composable
fun HomeTopHeaderView(
    onSearchClicked: () -> Unit,
    onCreateIssueClicked: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.padding(horizontal = 15.dp, vertical = 20.dp),
            text = "Home",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = colorResource(id = R.color.black),
        )

        Spacer(modifier = Modifier.weight(weight = 1f))

        Row {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null,
                tint = colorResource(id = R.color.blue),
                modifier = Modifier
                    .padding(end = 12.dp)
                    .clickable { onSearchClicked() },
            )
            Icon(
                painter = painterResource(id = R.drawable.circle_add),
                contentDescription = null,
                tint = colorResource(id = R.color.blue),
                modifier = Modifier
                    .padding(end = 12.dp)
                    .clickable {
                        onCreateIssueClicked()
                    },
            )
        }
    }
}
