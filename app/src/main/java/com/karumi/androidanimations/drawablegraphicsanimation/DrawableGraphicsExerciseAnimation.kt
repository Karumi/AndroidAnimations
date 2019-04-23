package com.karumi.androidanimations.drawablegraphicsanimation

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import com.karumi.androidanimations.R

interface DrawableGraphicsExerciseAnimation {
    class VH(itemView: View) : com.afollestad.recyclical.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.animationName)
        val keyframeAnimationView: ImageView = itemView.findViewById(R.id.keyframeAnimationView)
        val vectorizedAnimationView: ImageView = itemView.findViewById(R.id.vectorizedAnimationView)
        val lottieAnimationView: ImageView = itemView.findViewById(R.id.lottieAnimationView)
        val seekBar: SeekBar = itemView.findViewById(R.id.seekBar)
    }

    class Binder(val getContext: () -> Context) {
        operator fun invoke(receiver: VH) = receiver.bind()

        private fun VH.bind() {
            name.text =
                R.string.drawable_graphics_animation_exercise.let { getContext().getString(it) }

            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    TODO(
                        """
                        | Render the animation that can be controlled with this SeekBar.
                        | You have the same animation in all formats: keyframe, vectorized and
                        | lottie. Configure all of them.
                        | """.trimMargin()
                    )
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                override fun onStopTrackingTouch(seekBar: SeekBar?) {}

            })
        }
    }
}