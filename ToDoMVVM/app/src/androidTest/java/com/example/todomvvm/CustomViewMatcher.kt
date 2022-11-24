package com.example.todomvvm

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

fun withDescendantViewAtPosition(
    @IdRes recyclerViewId: Int,
    @IdRes targetViewId: Int,
    position: Int
) = object : TypeSafeMatcher<View>() {
        override fun describeTo(description: Description) {
            description.appendText("指定された位置のリストアイテム内に、指定された ID のビューが存在すること")
        }

        override fun matchesSafely(view: View): Boolean {
            val root = view.rootView
            // 画面内から RecyclerView を見つける
            val recyclerView = root.findViewById<RecyclerView>(recyclerViewId) ?: return false
            // 指定された位置の ViewHolder を取得する
            val viewHolder = recyclerView.findViewHolderForAdapterPosition(position) ?: return false
            // ViewHolder の中から指定された ID のビューを見つける
            val targetView = viewHolder.itemView.findViewById<View>(targetViewId) ?: return false
            // 引数 view が指定されたビューと一致するか検証する
            return view == targetView
        }
    }