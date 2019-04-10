package com.karumi.androidanimations.propertyanimator

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Path
import android.graphics.PathMeasure
import android.graphics.PointF
import android.graphics.RectF
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.karumi.androidanimations.R
import com.karumi.androidanimations.common.ColorEvaluator
import com.karumi.androidanimations.extensions.animatePath

interface PropertyExerciseAnimator {
    companion object {
        private const val animationDuration = 500L
    }

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
                val (startColor, endColor) = getColorProgressPair(button.isSelected)
                val (startRotation, endRotation) = getStartRotationPair(button.isSelected)

                val colorAnimation = getBackgroundColorAnimation(startColor, endColor)
                val startBackgroundRotationAnimation =
                    getBackgroundRotation(startRotation, endRotation)
                val endBackgroundRotationAnimation =
                    getBackgroundRotation(endRotation, startRotation)

                /**
                 * Define some coordinates to better calculate positions
                 * ------------------------------------------------------
                 * ┌───────────┐
                 * │ballTopLeft├───────────────────────────────┬────────────────────────────────┐
                 * └─────────┬─┼─┐                                                              │
                 *           │ │█├──────┬───────┐              │                                │
                 *           │ └┬┘       ┌──────┴───┐           ┌────────────────┐              │
                 *           │  │       ││ballCenter│          ││backgroundCenter│              │
                 *           │  │      ┌─┼──────┬───┘         ┌─┼────────────────┘              │
                 *           ├  ┼─  ── │█├  ──  ┼─  ──  ──  ──│█├─  ──  ──  ──  ──  ──  ──  ──  ┤
                 *           │  │      └─┘      │             └─┘                               │
                 *           │  │       │       │              │                                │
                 *           │  │               │                                               │
                 *           │  └───────┴───────┘              │                                │
                 *           │                                                                  │
                 *           ├──────────────┬──────────────────┴────────────────┬───────────────┤
                 *           │backgroundLeft│                                   │backgroundRight│
                 *           └──────────────┘                                   └───────────────┘
                 */
                val ballTopLeftCoordinate = PointF(
                    switchBall.x - background.paddingStart,
                    switchBall.y - background.paddingTop
                )
                val ballCenterCoordinate = PointF(
                    ballTopLeftCoordinate.x + switchBall.width / 2f,
                    ballTopLeftCoordinate.y + switchBall.height / 2f
                )
                val backgroundCenterCoordinate = PointF(
                    switchBackground.x + switchBackground.width / 2f - background.paddingStart,
                    switchBackground.y + switchBackground.height / 2f - background.paddingTop
                )
                val backgroundLeft = switchBackground.x - background.paddingStart
                val backgroundRight = backgroundLeft + switchBackground.width

                val ballMargin =
                    (switchBall.layoutParams as ViewGroup.MarginLayoutParams).leftMargin

                val ballEndCoordinate = PointF(
                    if (button.isSelected) backgroundLeft + ballMargin + switchBall.width / 2f
                    else backgroundRight - ballMargin - switchBall.width / 2f,
                    ballCenterCoordinate.y
                )

                val radius = Math.abs(backgroundCenterCoordinate.x - ballCenterCoordinate.x)
                val ovalTop = backgroundCenterCoordinate.y - radius

                /**
                 * Define all points the ball is going to follow. Keep in mind that when
                 * the button is selected we rename points such that:
                 *   pointA <=> pointD
                 *   pointB <=> pointC
                 * ------------------------------------------------------------------------
                 * ┌───────────┬────────┬────────────┬───────────────────┬────────────┐
                 * │         ┌─┤ pointB │                                │rotationRect│
                 * │     ┌──>│█├────┬───┘            │                   └────────────┤
                 * │     │   └─┘    └────┐                             ┌────────┐     │
                 * │     │               └────┐      │                 │ pointD │     │
                 * │    ┌─┐                   └────┐                   └───────┬┴┐    │
                 * ├  ──│█┼─  ──  ──  ──  ──  ──  ─┴─┼──┐ ──  ──  ──  ──  ──  ─┼█│──  ┤
                 * │    └┬┴───────┐                     └────┐                 └─┘    │
                 * │     │ pointA │                  │       └────┐             ▲     │
                 * │     └────────┘                               └───┐   ┌─┐   │     │
                 * │                                 │           ┌────┴───┤█│───┘     │
                 * │                                             │ pointC ├─┘         │
                 * └─────────────────────────────────┴───────────┴────────┴───────────┘
                 */
                val rotationRect = RectF(
                    Math.min(ballCenterCoordinate.x, ballEndCoordinate.x), ovalTop,
                    Math.max(ballCenterCoordinate.x, ballEndCoordinate.x), ovalTop + 2 * radius
                )

                val (aToBStartAngle, aToBSweepAngle) =
                    if (button.isSelected) 0f to -15f
                    else 180f to 15f

                val fromAToBPath = Path().apply {
                    arcTo(rotationRect, aToBStartAngle, aToBSweepAngle)
                }

                val pointB = fromAToBPath.lastPosition

                val (dToCStartAngle, dToCSweepAngle) =
                    if (button.isSelected) 180f to -15f
                    else 0f to 15f
                val fromDToCPath = Path().apply {
                    moveTo(ballEndCoordinate.x, ballEndCoordinate.y)
                    arcTo(rotationRect, dToCStartAngle, dToCSweepAngle)
                }

                val pointC = fromDToCPath.lastPosition
                val (cToDStartAngle, cToDSweepAngle) =
                    if (button.isSelected) 165f to 15f
                    else 15f to -15f
                val fromCToDPath = Path().apply {
                    moveTo(pointC.first, pointC.second)
                    arcTo(rotationRect, cToDStartAngle, cToDSweepAngle)
                }

                val fromBToCPath = Path().apply {
                    moveTo(pointB.first, pointB.second)
                    lineTo(pointC.first, pointC.second)
                }

                val fromAToBBallRotation = fromAToBPath.animation(switchBall)
                val fromBToCBallTranslation = fromBToCPath.animation(switchBall)
                val fromCToDBallRotation = fromCToDPath.animation(switchBall)

                /**
                 * Compose the final animation
                 *
                 * Phase 1:
                 *   - background start rotation
                 *   - ball fromAToB rotation
                 *   - color animation
                 * Phase 2:
                 *   - ball fromBToC translation
                 * Phase 3:
                 *   - background end rotation
                 *   - ball from CToD rotation
                 */
                AnimatorSet().apply {
                    play(startBackgroundRotationAnimation)
                        .with(fromAToBBallRotation)
                        .with(colorAnimation)

                    play(fromBToCBallTranslation)
                        .after(fromAToBBallRotation)

                    play(endBackgroundRotationAnimation)
                        .after(fromBToCBallTranslation)

                    play(fromCToDBallRotation)
                        .with(endBackgroundRotationAnimation)

                    duration = 2 * animationDuration
                }.start()

                button.isSelected = !button.isSelected
            }
        }

        private fun VH.getBackgroundRotation(
            startRotation: Float,
            endRotation: Float
        ) = ObjectAnimator.ofFloat(
            switchBackground,
            View.ROTATION,
            startRotation,
            endRotation
        ).apply { duration = animationDuration }

        private fun VH.getBackgroundColorAnimation(
            startColor: Int,
            endColor: Int
        ): ObjectAnimator? = ObjectAnimator.ofObject(
            background,
            "backgroundColor",
            ColorEvaluator.instance,
            startColor,
            endColor
        ).apply { duration = animationDuration }

        private val Path.lastPosition: Pair<Float, Float>
            get() {
                val lastPosition = FloatArray(2)
                PathMeasure(this, false).apply {
                    getPosTan(length, lastPosition, null)
                }
                return lastPosition[0] to lastPosition[1]
            }

        private fun Path.animation(view: View): ValueAnimator = animatePath(view, this).apply {
            duration = 5000
            repeatCount = 0
            repeatMode = ValueAnimator.RESTART
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