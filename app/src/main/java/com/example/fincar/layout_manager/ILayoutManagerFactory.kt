package com.example.fincar.layout_manager

import androidx.recyclerview.widget.RecyclerView

interface ILayoutManagerFactory {
    fun create(): RecyclerView.LayoutManager
}