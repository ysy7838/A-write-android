package com.example.a_write

data class Post(
    var title: String = "",
    var time: String = "",
    var content: String = "",
    var isSaved: Boolean = false,
)