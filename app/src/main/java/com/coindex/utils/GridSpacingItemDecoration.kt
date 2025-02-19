package com.coindex.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(
    private val spanCount: Int,
    private val spacing: Int,
    private val includeEdge: Boolean
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view) // 获取当前项的位置
        val column = position % spanCount // 计算当前项所在的列

        if (includeEdge) {
            // 如果 includeEdge 为 true，则在边缘也添加间距
            outRect.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount
            if (position < spanCount) { // 第一行的顶部间距
                outRect.top = spacing
            }
            outRect.bottom = spacing // 每个项的底部间距
        } else {
            // 如果 includeEdge 为 false，则不为边缘添加间距
            outRect.left = column * spacing / spanCount
            outRect.right = spacing - (column + 1) * spacing / spanCount
            if (position >= spanCount) { // 非第一行的顶部间距
                outRect.top = spacing
            }
        }
    }
}