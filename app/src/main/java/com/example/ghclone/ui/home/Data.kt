package com.example.ghclone.ui.home

import com.example.ghclone.R

val myWorkOptionsList =
    listOf(
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
        WorkOption(
            title = "Discussions",
            icon = R.drawable.ic_discussion,
            workType = WorkType.DISCUSSIONS.type,
            backgroundColor = R.color.purple,
        ),
        WorkOption(
            title = "Repositories",
            icon = R.drawable.ic_repositories,
            workType = WorkType.REPOSITORIES.type,
            backgroundColor = R.color.black_shade,
        ),
        WorkOption(
            title = "Organizations",
            icon = R.drawable.ic_organization,
            workType = WorkType.ORGANIZATIONS.type,
            backgroundColor = R.color.orange,
        ),
        WorkOption(
            title = "Starred",
            icon = R.drawable.ic_starred,
            workType = WorkType.STARRED.type,
            backgroundColor = R.color.yellow,
        ),
    )
