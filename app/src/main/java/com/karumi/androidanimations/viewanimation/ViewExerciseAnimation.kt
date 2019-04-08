package com.karumi.androidanimations.viewanimation

import android.content.Context
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.Animation.AnimationListener
import android.view.animation.Animation.RELATIVE_TO_PARENT
import android.view.animation.AnimationSet
import android.view.animation.DecelerateInterpolator
import android.view.animation.RotateAnimation
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.TextView
import com.afollestad.recyclical.ViewHolder
import com.karumi.androidanimations.R

interface ViewExerciseAnimation {

    companion object {
        private const val eachAnimationDuration = 500L
        private const val secondAnimationDelay = 200L
    }

    class VH(itemView: View) : ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.animationName)
        val button: Button = itemView.findViewById(R.id.button)
    }

    class Binder(val getContext: () -> Context) {
        operator fun invoke(receiver: VH) = receiver.bind()

        private fun VH.bind() {
            name.text = getContext().getString(R.string.view_animation_set)
            button.setOnClickListener {
                val toParentsLeft = 0f
                val toParentsRight = 1f
                val toParentsTop = 0f

                val moveOutAnimation = TranslateAnimation(
                    RELATIVE_TO_PARENT, toParentsLeft,
                    RELATIVE_TO_PARENT, toParentsRight,
                    RELATIVE_TO_PARENT, toParentsTop,
                    RELATIVE_TO_PARENT, toParentsTop
                ).apply {
                    duration = eachAnimationDuration
                }

                val totallyVisible = 1f
                val totallyInvisible = 0f
                val alphaOutAnimation = AlphaAnimation(totallyVisible, totallyInvisible).apply {
                    duration = eachAnimationDuration
                }

                val startAngle = 340f
                val endAngle = 360f
                val centeredPivotInX = 0.5f
                val outerPivotInY = 2f
                val rotateInAnimation = RotateAnimation(
                    startAngle, endAngle,
                    RELATIVE_TO_PARENT, centeredPivotInX,
                    RELATIVE_TO_PARENT, outerPivotInY
                ).apply {
                    startOffset = secondAnimationDelay
                    duration = eachAnimationDuration
                }

                val alphaInAnimation = AlphaAnimation(totallyInvisible, totallyVisible).apply {
                    startOffset = secondAnimationDelay
                    duration = eachAnimationDuration
                }

                val exitAnimation = AnimationSet(true).apply {
                    interpolator = AccelerateInterpolator()
                }
                exitAnimation.addAnimation(moveOutAnimation)
                exitAnimation.addAnimation(alphaOutAnimation)

                val enterAnimation = AnimationSet(true).apply {
                    interpolator = DecelerateInterpolator()
                }
                enterAnimation.addAnimation(alphaInAnimation)
                enterAnimation.addAnimation(rotateInAnimation)

                exitAnimation.setAnimationListener(object : AnimationListener {
                    override fun onAnimationRepeat(animation: Animation?) {
                    }

                    override fun onAnimationEnd(animation: Animation?) {
                        itemView.startAnimation(enterAnimation)
                    }

                    override fun onAnimationStart(animation: Animation?) {
                    }

                })
                itemView.startAnimation(exitAnimation)
            }
        }
    }
}