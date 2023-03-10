package com.example.ghclone.ui.home

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.ghclone.R
import com.example.ghclone.ui.theme.GHCloneTheme

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
fun HomeTopHeaderViewPreview() {
    GHCloneTheme {
        HomeTopHeaderView(onSearchClicked = {}, onCreateIssueClicked = {})
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Composable
fun MyWorkOptionItemPreview() {
    MyWorkOptionItem(
        myWorkOption = WorkOption(
            workType = "starred",
            title = "Starred",
            icon = R.drawable.ic_starred,
            backgroundColor = R.color.yellow,
        ),
        onItemClicked = {},
    )
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = 0xFF000000,
)
@Composable
fun QuickShortcutsViewPreview() {
    GHCloneTheme {
        QuickShortcutsView()
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
