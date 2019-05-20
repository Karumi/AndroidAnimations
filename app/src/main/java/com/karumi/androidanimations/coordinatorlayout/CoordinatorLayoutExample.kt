package com.karumi.androidanimations.coordinatorlayout

import android.content.Context
import android.view.View
import android.widget.TextView
import com.karumi.androidanimations.R
import com.karumi.androidanimations.coordinatorlayout.CoordinatorLayoutFragment.Example.Default
import com.karumi.androidanimations.coordinatorlayout.CoordinatorLayoutFragment.Example.Custom


interface CoordinatorLayoutExample {
    class VH(itemView: View) : com.afollestad.recyclical.ViewHolder(itemView) {
        val row: View = itemView.findViewById(R.id.row)
        val exampleName: TextView = itemView.findViewById(R.id.exampleName)
    }

    class Binder(
        val getContext: () -> Context
    ) {
        operator fun invoke(
            receiver: VH,
            item: CoordinatorLayoutFragment.Example,
            onClick: () -> Unit
        ) = receiver.bind(item, onClick)

        private fun VH.bind(item: CoordinatorLayoutFragment.Example, onClick: () -> Unit) {
            exampleName.text = when (item) {
                Default -> R.string.coordinator_layout_default
                Custom -> R.string.coordinator_custom_behavior
            }.let { getContext().getString(it) }
            row.setOnClickListener { onClick() }
        }
    }
}