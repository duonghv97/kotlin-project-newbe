package com.example.duong.gdgfinder

import android.databinding.BindingAdapter
import android.support.v7.widget.RecyclerView
import android.view.View
import com.example.duong.gdgfinder.network.GdgChapter
import com.example.duong.gdgfinder.search.GdgListAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<GdgChapter>?) {
    val adapter = recyclerView.adapter as GdgListAdapter
        adapter.submitList(data)
}

@BindingAdapter("showOnlyWhenEmpty")
fun View.showOnlyWhenEmpty(data: List<GdgChapter>?) {
    visibility = when {
        data == null || data.isEmpty() -> View.VISIBLE
        else -> View.GONE
    }
}