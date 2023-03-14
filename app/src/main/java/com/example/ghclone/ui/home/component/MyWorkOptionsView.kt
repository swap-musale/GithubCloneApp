package com.example.ghclone.ui.home.component

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ghclone.R
import com.example.ghclone.ui.home.MY_WORK_VIEW_TAG
import com.example.ghclone.ui.home.WorkOption

@Composable
fun MyWorkOptionsView(myWorkOptionsList: List<WorkOption>) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(id = R.string.str_my_work),
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

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .testTag(tag = MY_WORK_VIEW_TAG),
        ) {
            myWorkOptionsList.forEach { workOption ->
                MyWorkOptionItem(
                    myWorkOption = workOption,
                    onItemClicked = {},
                )
            }
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
