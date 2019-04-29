package com.karumi.androidanimations.sharedelements

import android.content.Context
import android.graphics.PointF
import android.view.View
import com.karumi.androidanimations.R
import com.karumi.androidanimations.common.CircularView


interface SharedElementsExerciseTransition {
    class VH(itemView: View) : com.afollestad.recyclical.ViewHolder(itemView) {
        val topStartButton: CircularView = itemView.findViewById(R.id.topStartButton)
        val topEndButton: CircularView = itemView.findViewById(R.id.topEndButton)
        val bottomStartButton: CircularView = itemView.findViewById(R.id.bottomStartButton)
        val bottomEndButton: CircularView = itemView.findViewById(R.id.bottomEndButton)
    }

    data class SharedElements(val circularView: CircularView)
    enum class Color {
        BitterSweet, Java, Molten, Gulava
    }

    class Binder(
        val getContext: () -> Context,
        val onClick: (color: Color, sharedElements: SharedElements) -> Unit
    ) {
        operator fun invoke(receiver: VH, onFinished: () -> Unit) = receiver.bind(onFinished)

        private fun VH.bind(onFinished: () -> Unit) {
            arrayOf(
                topStartButton to Color.BitterSweet,
                topEndButton to Color.Java,
                bottomStartButton to Color.Molten,
                bottomEndButton to Color.Gulava
            ).forEach { (view, color) ->
                view.viewTreeObserver.addOnGlobalLayoutListener {
                    view.radius = topStartButton.measuredWidth / 2f
                    view.center = PointF(
                        view.measuredWidth / 2f,
                        view.measuredHeight / 2f
                    )
                }
                view.setOnClickListener { onClick(color, SharedElements(view)) }
            }

            topStartButton.viewTreeObserver.addOnGlobalLayoutListener { onFinished() }
        }
    }
}