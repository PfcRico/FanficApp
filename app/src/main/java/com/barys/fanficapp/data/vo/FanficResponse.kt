package com.barys.fanficapp.data.vo

data class FanficResponse(
    val content: List<Fanfic>,
    val empty: Boolean,
    val first: Boolean,
    val last: Boolean,
    val number: Int,
    val numberOfElements: Int,
    val pageable: Pageable,
    val size: Int,
    val sort: SortX,
    val totalElements: Int,
    val totalPages: Int
)