package com.karumi.androidanimations.propertyanimator

import android.content.Context
import android.view.View
import android.widget.TextView
import com.karumi.androidanimations.R

interface PropertyExerciseAnimator {
    class VH(itemView: View) : com.afollestad.recyclical.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.animationName)
        val button: View = itemView.findViewById(R.id.button)
        val background: View = itemView.findViewById(R.id.background)
        val switchBall: View = itemView.findViewById(R.id.switchBall)
        val switchBackground: View = itemView.findViewById(R.id.switchBackground)
    }

    class Binder(val getContext: () -> Context) {
        operator fun invoke(receiver: VH) = receiver.bind()

        private fun VH.bind() {
            name.text = R.string.property_animation_exercise.let { getContext().getString(it) }

            button.setOnClickListener {
                TODO(
                    """
                    | Implement your chained animation with AnimatorSet. Try to respect the reference
                    | animation as much as possible: https://dribbble.com/shots/4598116-On-Off-Switch
                    |
                    | You can use this.itemView to animate the whole card view.
                    | """.trimMargin()
                )
            }
        }
    }
}