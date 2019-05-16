package com.karumi.androidanimations.coordinatorlayout

import android.content.Context
import android.view.View
import android.widget.TextView
import com.karumi.androidanimations.R


interface CoordinatorLayoutExample {
    class VH(itemView: View) : com.afollestad.recyclical.ViewHolder(itemView) {
        val exampleName: TextView = itemView.findViewById(R.id.exampleName)
    }

    class Binder(
        val getContext: () -> Context
    ) {
        operator fun invoke(
            receiver: VH,
            item: CoordinatorLayoutFragment.Example
        ) = receiver.bind(item)

        private fun VH.bind(item: CoordinatorLayoutFragment.Example) {
            exampleName.text = when (item) {
                CoordinatorLayoutFragment.Example.Anchor -> R.string.coordinator_layout_anchor
            }.let { getContext().getString(it) }
        }
    }
}