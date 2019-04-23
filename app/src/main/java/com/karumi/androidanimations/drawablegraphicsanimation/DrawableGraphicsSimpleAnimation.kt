package com.karumi.androidanimations.drawablegraphicsanimation

import android.content.Context
import android.view.View
import android.widget.TextView
import com.karumi.androidanimations.R
import com.karumi.androidanimations.drawablegraphicsanimation.DrawableGraphicsAnimationFragment.DrawableGraphicsAnimation

interface DrawableGraphicsSimpleAnimation {
    class VH(itemView: View) : com.afollestad.recyclical.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.animationName)
    }

    class Binder(val getContext: () -> Context) {
        operator fun invoke(
            receiver: VH,
            item: DrawableGraphicsAnimation
        ) = receiver.bind(item)

        private fun VH.bind(item: DrawableGraphicsAnimation) {
            name.text = getAnimationName(item)
            animate(item, name)
        }

        private fun getAnimationName(item: DrawableGraphicsAnimation): String = when (item) {
            DrawableGraphicsAnimation.Keyframe -> R.string.drawable_graphics_animation_by_keyframe
            DrawableGraphicsAnimation.Vector -> R.string.drawable_graphics_animation_with_vector
            DrawableGraphicsAnimation.Lottie -> R.string.drawable_graphics_animation_with_lottie
        }.let { getContext().getString(it) }

        private fun animate(item: DrawableGraphicsAnimation, view: View) {
            when (item) {
                DrawableGraphicsAnimation.Keyframe -> R.string.drawable_graphics_animation_by_keyframe
                DrawableGraphicsAnimation.Vector -> R.string.drawable_graphics_animation_with_vector
                DrawableGraphicsAnimation.Lottie -> R.string.drawable_graphics_animation_with_lottie
            }
        }
    }
}