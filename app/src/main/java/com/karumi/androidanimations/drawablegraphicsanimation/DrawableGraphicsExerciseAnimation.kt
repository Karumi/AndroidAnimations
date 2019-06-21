package com.karumi.androidanimations.drawablegraphicsanimation

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.vectordrawable.graphics.drawable.Animatable2Compat.AnimationCallback
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.karumi.androidanimations.R

interface DrawableGraphicsExerciseAnimation {
    class VH(itemView: View) : com.afollestad.recyclical.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.animationName)
        val keyframeAnimationView: ImageView = itemView.findViewById(R.id.keyframeAnimationView)
        val vectorizedAnimationView: ImageView = itemView.findViewById(R.id.vectorizedAnimationView)
        val lottieAnimationView: LottieAnimationView =
            itemView.findViewById(R.id.lottieAnimationView)
        val playButton: Button = itemView.findViewById(R.id.playButton)
        val stopButton: Button = itemView.findViewById(R.id.stopButton)
    }

    class Binder(val getContext: () -> Context) {

        operator fun invoke(receiver: VH) = receiver.bind()
        private var isPlayingVectorizedAnimation = false

        private fun VH.bind() {
            name.text = R.string.drawable_graphics_animation_exercise
                .let { getContext().getString(it) }

            keyframeAnimationView.setBackgroundResource(R.drawable.keyframe_exercise_animation)
            val vectorizedAnimation = configureVectorizedAnimation()
            vectorizedAnimationView.setImageDrawable(vectorizedAnimation)
            configureLottieAnimation()

            playButton.setOnClickListener {
                isPlayingVectorizedAnimation = true
                vectorizedAnimation?.start()
                (keyframeAnimationView.background as AnimationDrawable).start()
                lottieAnimationView.playAnimation()
            }

            stopButton.setOnClickListener {
                vectorizedAnimation?.stop()
                isPlayingVectorizedAnimation = false
                (keyframeAnimationView.background as AnimationDrawable).stop()
                lottieAnimationView.cancelAnimation()
            }
        }

        private fun configureVectorizedAnimation() = AnimatedVectorDrawableCompat.create(
            getContext(),
            R.drawable.vectorized_exercise_animation
        )?.apply {
            registerAnimationCallback(object : AnimationCallback() {
                override fun onAnimationEnd(drawable: Drawable?) {
                    if (isPlayingVectorizedAnimation) {
                        start()
                    }
                }
            })
        }

        private fun VH.configureLottieAnimation() {
            lottieAnimationView.setAnimation(R.raw.material_wave_loading)
            lottieAnimationView.repeatMode = LottieDrawable.RESTART
            lottieAnimationView.repeatCount = LottieDrawable.INFINITE
        }
    }
}