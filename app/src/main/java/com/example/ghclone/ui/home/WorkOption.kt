package com.example.ghclone.ui.home

data class WorkOption(
    val workType: String,
    val title: String,
    val icon: Int,
    val backgroundColor: Int,
)

sealed class WorkType(val type: String) {
    object ISSUES : WorkType("issues")
    object PULL_REQUESTS : WorkType("pull_requests")
    object DISCUSSIONS : WorkType("discussions")
    object REPOSITORIES : WorkType("repositories")
    object ORGANIZATIONS : WorkType("organizations")
    object STARRED : WorkType("starred")
}
