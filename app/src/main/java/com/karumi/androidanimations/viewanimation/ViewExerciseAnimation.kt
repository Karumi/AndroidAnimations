package com.karumi.androidanimations.viewanimation

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.afollestad.recyclical.ViewHolder
import com.karumi.androidanimations.R

interface ViewExerciseAnimation {

    class VH(itemView: View) : ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.animationName)
        val button: Button = itemView.findViewById(R.id.button)
    }

    class Binder(val getContext: () -> Context) {
        operator fun invoke(receiver: VH) = receiver.bind()

        private fun VH.bind() {
            name.text = getContext().getString(R.string.view_animation_set)
            button.setOnClickListener {
                TODO(
                    """
                    | Implement your chained animation with AnimationSet according to the slides
                    |
                    | You can use this.itemView to animate the whole card view.
                    | """.trimMargin()
                )
            }
        }
    }
}