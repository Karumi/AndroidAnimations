package com.karumi.androidanimations.propertyanimator

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.karumi.androidanimations.R
import com.karumi.androidanimations.common.ColorEvaluator
import com.karumi.androidanimations.extensions.px

interface PropertyExerciseAnimator {
    class VH(itemView: View) : com.afollestad.recyclical.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.animationName)
        val background: View = itemView.findViewById(R.id.background)
        val switchContainer: View = itemView.findViewById(R.id.switchContainer)
        val switchThumb: View = itemView.findViewById(R.id.switchThumb)
    }

    class Binder(val getContext: () -> Context) {
        operator fun invoke(receiver: VH) = receiver.bind()

        private fun VH.bind() {
            name.text = R.string.property_animation_exercise.let { getContext().getString(it) }
            switchContainer.setOnClickListener {
                val backgroundColorAnimation = getBackgroundColorAnimation()
                val containerStartRotationAnimation = getContainerStartRotationAnimation()
                val containerEndRotationAnimation = getContainerEndRotationAnimation()
                val thumbTranslateAnimation = getThumbTranslateAnimation()
                    .apply { startDelay = 150 }

                AnimatorSet().apply {
                    play(containerStartRotationAnimation)
                        .with(backgroundColorAnimation)
                        .with(thumbTranslateAnimation)

                    play(containerEndRotationAnimation)
                        .after(thumbTranslateAnimation)
                }.start()

                switchContainer.isSelected = !switchContainer.isSelected
            }
        }

        private fun VH.getBackgroundColorAnimation(): ObjectAnimator {
            val (startColor, endColor) = getColorProgressPair(switchContainer.isSelected)
            return ObjectAnimator.ofObject(
                background,
                "backgroundColor",
                ColorEvaluator.instance,
                startColor,
                endColor
            ).apply { duration = 750 }
        }

        private fun VH.getContainerStartRotationAnimation(): ObjectAnimator {
            val (startRotation, endRotation) = getStartRotationPair(switchContainer.isSelected)
            return ObjectAnimator.ofFloat(
                switchContainer,
                View.ROTATION,
                startRotation,
                endRotation
            ).apply { duration = 250 }
        }

        private fun VH.getContainerEndRotationAnimation(): ObjectAnimator {
            val (startRotation, endRotation) = getStartRotationPair(switchContainer.isSelected)
            return ObjectAnimator.ofFloat(
                switchContainer,
                View.ROTATION,
                endRotation,
                startRotation
            ).apply { duration = 250 }
        }

        private fun VH.getThumbTranslateAnimation(): ObjectAnimator {
            val (startPosition, endPosition) = getThumbTranslatePoint(switchContainer.isSelected)
            return ObjectAnimator.ofFloat(
                switchThumb,
                View.X,
                startPosition,
                endPosition
            ).apply { duration = 500 }
        }

        private fun VH.getThumbTranslatePoint(isSelected: Boolean): Pair<Float, Float> {
            val leftPoint = 4.px.toFloat()
            val rightPoint = switchContainer.width - leftPoint - switchThumb.width.toFloat()

            return if (isSelected) {
                rightPoint to leftPoint
            } else {
                leftPoint to rightPoint
            }
        }

        private fun getStartRotationPair(
            isSelected: Boolean
        ): Pair<Float, Float> = if (isSelected) {
            0f to -15f
        } else {
            0f to 15f
        }

        private fun getColorProgressPair(isSelected: Boolean): Pair<Int, Int> = if (isSelected) {
            val startColor = ContextCompat.getColor(getContext(), R.color.bittersweet)
            val endColor = ContextCompat.getColor(getContext(), R.color.java)
            startColor to endColor
        } else {
            val startColor = ContextCompat.getColor(getContext(), R.color.java)
            val endColor = ContextCompat.getColor(getContext(), R.color.bittersweet)
            startColor to endColor
        }
    }
}