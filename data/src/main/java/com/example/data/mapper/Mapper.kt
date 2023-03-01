package com.example.data

import com.example.domain.model.Node
import com.example.domain.model.PrimaryLanguage
import com.example.domain.model.Repository
import com.example.domain.model.UserInfo

fun GetUserInfoQuery.Viewer.toUserInfo() =
    UserInfo(
        name = name.orEmpty(),
        email = email,
        location = location.orEmpty(),
        avatarUrl = avatarUrl.toString(),
        url = url.toString(),
        company = company.toString(),
        websiteUrl = websiteUrl.toString(),
        repositories = repositories.toRepository()
    )

fun GetUserInfoQuery.Repositories.toRepository() =
    Repository(
        nodes = nodes?.map {
            it?.toNode() ?: Node(
                "", "", "", false, "", PrimaryLanguage("", "")
            )
        }.orEmpty(),
        totalCount = totalCount
    )

fun GetUserInfoQuery.Node.toNode() =
    Node(
        createdAt = createdAt.toString(),
        description = description.orEmpty(),
        name = name,
        isPrivate = isPrivate,
        url = url.toString(),
        primaryLanguage = primaryLanguage?.toPrimaryLanguage() ?: PrimaryLanguage(
            name = "",
            color = ""
        )
    )

fun GetUserInfoQuery.PrimaryLanguage.toPrimaryLanguage() =
    PrimaryLanguage(
        name = name,
        color = color.orEmpty()
    )

