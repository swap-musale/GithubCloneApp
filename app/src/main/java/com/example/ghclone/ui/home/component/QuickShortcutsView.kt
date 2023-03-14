package com.example.ghclone.ui.home.component

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ghclone.R
import com.example.ghclone.ui.home.ShortcutOption
import com.example.ghclone.ui.theme.GHCloneTheme

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
            text = stringResource(R.string.things_you_need_one_tap_away),
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
        )

        Text(
            textAlign = TextAlign.Center,
            color = colorResource(id = R.color.grey),
            text = stringResource(R.string.fast_access_your_issues),
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
                text = stringResource(R.string.str_get_started),
                color = colorResource(id = R.color.blue),
            )
        }
    }
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
