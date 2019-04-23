package com.karumi.androidanimations.drawablegraphicsanimation

import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.vectordrawable.graphics.drawable.Animatable2Compat
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.karumi.androidanimations.R
import com.karumi.androidanimations.drawablegraphicsanimation.DrawableGraphicsAnimationFragment.DrawableGraphicsAnimation
import com.karumi.androidanimations.extensions.exhaustive

interface DrawableGraphicsSimpleAnimation {
    class VH(itemView: View) : com.afollestad.recyclical.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.animationName)
        val animationView: AppCompatImageView = itemView.findViewById(R.id.animationView)
    }

    class Binder(val getContext: () -> Context) {
        operator fun invoke(
            receiver: VH,
            item: DrawableGraphicsAnimation
        ) = receiver.bind(item)

        private fun VH.bind(item: DrawableGraphicsAnimation) {
            name.text = getAnimationName(item)
            animate(item, animationView)
        }

        private fun getAnimationName(item: DrawableGraphicsAnimation): String = when (item) {
            DrawableGraphicsAnimation.Keyframe -> R.string.drawable_graphics_animation_by_keyframe
            DrawableGraphicsAnimation.Vector -> R.string.drawable_graphics_animation_with_vector
        }.let { getContext().getString(it) }

        private fun animate(item: DrawableGraphicsAnimation, view: AppCompatImageView) {
            when (item) {
                DrawableGraphicsAnimation.Keyframe -> {
                    view.setBackgroundResource(R.drawable.keyframe_animation)
                    (view.background as AnimationDrawable).start()
                }
                DrawableGraphicsAnimation.Vector -> {
                    val animation = AnimatedVectorDrawableCompat.create(
                        getContext(),
                        R.drawable.vectorized_animation
                    )?.apply {
                        repeatAnimation()
                        start()
                    }
                    view.setImageDrawable(animation)
                }
            }.exhaustive
        }

        private fun AnimatedVectorDrawableCompat.repeatAnimation() {
            registerAnimationCallback(object : Animatable2Compat.AnimationCallback() {
                override fun onAnimationEnd(drawable: Drawable?) {
                    start()
                }
            })
        }
    }
}