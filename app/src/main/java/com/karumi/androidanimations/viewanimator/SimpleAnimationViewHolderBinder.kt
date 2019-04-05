package com.karumi.androidanimations.viewanimator

import android.content.Context
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import android.widget.TextView
import com.karumi.androidanimations.R
import com.karumi.androidanimations.viewanimator.ViewAnimationFragment.SimpleAnimation

interface ViewSimpleAnimation {
    class VH(itemView: View) : com.afollestad.recyclical.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.animationName)
        val targetView: View = itemView.findViewById(R.id.target)
    }

    class Binder(val getContext: () -> Context) {
        operator fun invoke(
            receiver: VH,
            item: SimpleAnimation
        ) = receiver.bind(item)

        private fun VH.bind(
            item: SimpleAnimation
        ) {
            name.text = getAnimationName(item)
            val animation = getAnimation(item).apply {
                duration = 1000
                repeatMode = Animation.REVERSE
                repeatCount = Animation.INFINITE
                interpolator = AccelerateDecelerateInterpolator()
                fillAfter = true
            }
            targetView.startAnimation(animation)
        }

        private fun getAnimationName(item: SimpleAnimation): String = when (item) {
            SimpleAnimation.Alpha -> R.string.view_animation_alpha
            SimpleAnimation.Rotate -> R.string.view_animation_rotate
            SimpleAnimation.Scale -> R.string.view_animation_scale
            SimpleAnimation.Translate -> R.string.view_animation_translate
        }.let { getContext().getString(it) }

        private fun getAnimation(item: SimpleAnimation): Animation {
            return when (item) {
                SimpleAnimation.Alpha -> AlphaAnimation(0f, 1f)
                SimpleAnimation.Rotate -> RotateAnimation(
                    0f,
                    360f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f
                )
                SimpleAnimation.Scale -> ScaleAnimation(
                    1f,
                    2f,
                    1f,
                    0f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f,
                    Animation.RELATIVE_TO_SELF,
                    0.5f
                )
                SimpleAnimation.Translate -> TranslateAnimation(
                    Animation.RELATIVE_TO_SELF,
                    -2f,
                    Animation.RELATIVE_TO_SELF,
                    2f,
                    Animation.RELATIVE_TO_SELF,
                    0f,
                    Animation.RELATIVE_TO_SELF,
                    0f
                )
            }
        }
    }
}