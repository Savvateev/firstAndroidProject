package ru.netology.nmedia.dto

data class Post
    (
    val id: Long,
    val author: String,
    val content: String,
    val published: String,
    val videoUrl: String ="",
    val likedByMe: Boolean,
    val countOfLikes: Int,
    val countOfShare: Int,
    val countOfView: Int
)
