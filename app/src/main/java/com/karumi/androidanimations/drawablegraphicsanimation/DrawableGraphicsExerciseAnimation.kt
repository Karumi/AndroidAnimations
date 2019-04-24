package com.karumi.androidanimations.drawablegraphicsanimation

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.karumi.androidanimations.R

interface DrawableGraphicsExerciseAnimation {
    class VH(itemView: View) : com.afollestad.recyclical.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.animationName)
        val keyframeAnimationView: ImageView = itemView.findViewById(R.id.keyframeAnimationView)
        val vectorizedAnimationView: ImageView = itemView.findViewById(R.id.vectorizedAnimationView)
        val lottieAnimationView: ImageView = itemView.findViewById(R.id.lottieAnimationView)
        val playButton: Button = itemView.findViewById(R.id.playButton)
        val stopButton: Button = itemView.findViewById(R.id.stopButton)
    }

    class Binder(val getContext: () -> Context) {
        operator fun invoke(receiver: VH) = receiver.bind()

        private fun VH.bind() {
            name.text = R.string.drawable_graphics_animation_exercise
                .let { getContext().getString(it) }

            TODO(
                """
                | Render the animation that can be controlled with two buttons, one to play
                | and one to stop.
                | You have the same animation in all formats: keyframe, vectorized and
                | lottie. Configure all of them one by one.
                | """.trimMargin()
            )
        }
    }
}