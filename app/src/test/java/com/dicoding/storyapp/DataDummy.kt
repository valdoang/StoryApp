package com.dicoding.storyapp

import com.dicoding.storyapp.model.ListStoryItem

object DataDummy {

    fun generateDummyQuoteResponse(): List<ListStoryItem> {
        val data: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val list = ListStoryItem(
                "photo",
                "10-5-2023",
                "valdo",
                "tes",
            "$i",
                -7.315352,
                109.671027
            )
            data.add(list)
        }
        return data
    }
}