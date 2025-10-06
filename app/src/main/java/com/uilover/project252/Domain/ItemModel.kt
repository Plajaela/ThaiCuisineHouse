package com.uilover.project252.Domain

import java.io.Serializable

data class ItemModel(
    var id: Int,
    var categoryId: Int,
    var title: String = "",
    val shortdesc: String = "",
    val longdesc: String = "",
    val imageRes: Int,
) : Serializable
