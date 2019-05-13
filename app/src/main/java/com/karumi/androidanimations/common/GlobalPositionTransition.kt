package com.karumi.androidanimations.common

import android.animation.Animator
import android.animation.ObjectAnimator
import android.graphics.PointF
import android.util.Property
import android.view.View
import android.view.ViewGroup
import androidx.transition.Transition
import androidx.transition.TransitionValues

class GlobalPositionTransition : Transition() {
    companion object {
        private const val POSITION_X = "RadiusTransition:positionX"
        private const val POSITION_Y = "RadiusTransition:positionY"
        private const val PARENT_WINDOW_POSITION_X = "RadiusTransition:parentPositionX"
        private const val PARENT_WINDOW_POSITION_Y = "RadiusTransition:parentPositionY"
    }

    override fun captureStartValues(transitionValues: TransitionValues) =
        captureValues(transitionValues)

    override fun captureEndValues(transitionValues: TransitionValues) =
        captureValues(transitionValues)

    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {
        startValues ?: return null
        endValues ?: return null

        val startX = startValues.values[POSITION_X] as Float
        val startY = startValues.values[POSITION_Y] as Float
        val startParentWindowX = startValues.values[PARENT_WINDOW_POSITION_X] as Int
        val startParentWindowY = startValues.values[PARENT_WINDOW_POSITION_Y] as Int
        val endX = endValues.values[POSITION_X] as Float
        val endY = endValues.values[POSITION_Y] as Float
        val endParentWindowX = endValues.values[PARENT_WINDOW_POSITION_X] as Int
        val endParentWindowY = endValues.values[PARENT_WINDOW_POSITION_Y] as Int

        val startXInEndViewCoordinates = (startX + startParentWindowX) - endParentWindowX
        val startYInEndViewCoordinates = (startY + startParentWindowY) - endParentWindowY

        val path = pathMotion.getPath(
            startXInEndViewCoordinates,
            startYInEndViewCoordinates,
            endX,
            endY
        )

        return ObjectAnimator.ofFloat(
            endValues.view,
            PathProperty(object : Property<View, PointF>(PointF::class.java, "point") {
                override fun set(view: View?, value: PointF?) {
                    view ?: return
                    value ?: return

                    view.x = value.x
                    view.y = value.y
                }

                override fun get(view: View?): PointF? {
                    view ?: return null
                    return PointF(view.x, view.y)
                }
            }, path),
            0f, 1f
        )
    }

    private fun captureValues(transitionValues: TransitionValues) {
        val parent = transitionValues.view.parent as? ViewGroup ?: return

        val locationOfParentInWindow = IntArray(2)
        parent.getLocationInWindow(locationOfParentInWindow)

        transitionValues.values[POSITION_X] = transitionValues.view.x
        transitionValues.values[POSITION_Y] = transitionValues.view.y
        transitionValues.values[PARENT_WINDOW_POSITION_X] = locationOfParentInWindow[0]
        transitionValues.values[PARENT_WINDOW_POSITION_Y] = locationOfParentInWindow[1]
    }
}