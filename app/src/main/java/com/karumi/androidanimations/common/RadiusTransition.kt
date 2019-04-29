package com.karumi.androidanimations.common

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.ViewGroup
import androidx.transition.Transition
import androidx.transition.TransitionValues

class RadiusTransition : Transition() {
    companion object {
        private const val RADIUS = "RadiusTransition:radius"
        private const val CENTER_X = "RadiusTransition:centerX"
        private const val CENTER_Y = "RadiusTransition:centerY"
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
        val circularView = endValues.view as? CircularView ?: return null

        val startRadius = startValues.values[RADIUS] as Float
        val endRadius = endValues.values[RADIUS] as Float

        val startCenterX = startValues.values[CENTER_X] as Float
        val endCenterX = endValues.values[CENTER_X] as Float

        val startCenterY = startValues.values[CENTER_Y] as Float
        val endCenterY = endValues.values[CENTER_Y] as Float

        val radius = PropertyValuesHolder.ofFloat("radius", startRadius, endRadius)
        val centerX = PropertyValuesHolder.ofFloat("centerX", startCenterX, endCenterX)
        val centerY = PropertyValuesHolder.ofFloat("centerY", startCenterY, endCenterY)

        return ObjectAnimator.ofPropertyValuesHolder(circularView, radius, centerX, centerY)
    }

    private fun captureValues(transitionValues: TransitionValues) {
        val circularView = transitionValues.view as? CircularView ?: return
        transitionValues.values[RADIUS] = circularView.radius
        transitionValues.values[CENTER_X] = circularView.center.x
        transitionValues.values[CENTER_Y] = circularView.center.y
    }
}