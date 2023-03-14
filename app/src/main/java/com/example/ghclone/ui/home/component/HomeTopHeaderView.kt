package com.example.ghclone.ui.home.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ghclone.R
import com.example.ghclone.ui.theme.GHCloneTheme

@Composable
fun HomeTopHeaderView(
    onSearchClicked: () -> Unit,
    onCreateIssueClicked: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = colorResource(id = R.color.white),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 20.dp),
                text = stringResource(id = R.string.home_toolbar_title),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.black),
            )

            Spacer(modifier = Modifier.weight(weight = 1f))

            Row {
                IconButton(onClick = { onSearchClicked() }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                        tint = colorResource(id = R.color.blue),
                    )
                }

                IconButton(
                    modifier = Modifier.padding(end = 5.dp),
                    onClick = { onCreateIssueClicked() },
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.circle_add),
                        contentDescription = null,
                        tint = colorResource(id = R.color.blue),
                    )
                }
            }
        }
    }
}

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
