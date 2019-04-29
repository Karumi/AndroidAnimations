package com.karumi.androidanimations.sharedelements

import android.content.Context
import android.graphics.PointF
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.karumi.androidanimations.R
import com.karumi.androidanimations.common.CircularView


interface SharedBackgroundPathTransition {
    class VH(itemView: View) : com.afollestad.recyclical.ViewHolder(itemView) {
        val row: ConstraintLayout = itemView.findViewById(R.id.row)
        val background: CircularView = itemView.findViewById(R.id.background)
    }

    data class SharedElements(val row: ConstraintLayout, val background: CircularView)

    class Binder(
        val getContext: () -> Context,
        val onClick: (sharedElements: SharedElements) -> Unit
    ) {
        operator fun invoke(receiver: VH, onFinished: () -> Unit) = receiver.bind(onFinished)

        private fun VH.bind(onFinished: () -> Unit) {
            background.viewTreeObserver.addOnGlobalLayoutListener {
                val radius = Math.hypot(
                    background.measuredWidth.toDouble(),
                    background.measuredHeight.toDouble()
                )
                background.radius = radius.toFloat()
                background.center = PointF(
                    background.measuredWidth.toFloat() / 2,
                    background.measuredHeight.toFloat() / 2
                )
            }
            background.setOnClickListener { onClick(SharedElements(row, background)) }
            onFinished()
        }
    }
}