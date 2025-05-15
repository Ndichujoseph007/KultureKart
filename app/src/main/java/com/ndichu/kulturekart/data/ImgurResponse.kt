package com.ndichu.kulturekart.data


data class ImgurResponse(
    val data: ImgurData,
    val success: Boolean,
    val status: Int
)
data class ImgurData(
    val link: String
)