package com.karumi.androidanimations.common

import android.animation.Animator
import android.annotation.TargetApi
import android.graphics.Rect
import android.os.Build
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import androidx.transition.Transition
import androidx.transition.TransitionValues


@TargetApi(Build.VERSION_CODES.LOLLIPOP)
class CircularReveal : Transition() {

    override fun getTransitionProperties(): Array<String>? = PROPS

    override fun captureStartValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        captureValues(transitionValues)
    }

    private fun captureValues(values: TransitionValues) {
        val view = values.view
        val bounds = Rect()
        bounds.left = view.left
        bounds.right = view.right
        bounds.top = view.top
        bounds.bottom = view.bottom

        values.values[BOUNDS] = bounds
    }

    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {
        startValues ?: return null
        endValues ?: return null

        val startRect = startValues.values[BOUNDS] as Rect
        val endRect = endValues.values[BOUNDS] as Rect

        val view = endValues.view

        val circularTransition: Animator
        return if (isReveal(startRect, endRect)) {
            circularTransition = createReveal(view, startRect, endRect)
            circularTransition
        } else {
            layout(startRect, view)
            circularTransition = createConceal(view, startRect, endRect)
            circularTransition
        }
    }

    private fun layout(startRect: Rect, view: View) {
        view.layout(startRect.left, startRect.top, startRect.right, startRect.bottom)
    }

    private fun createReveal(view: View, from: Rect, to: Rect): Animator {
        val centerX = from.centerX()
        val centerY = from.centerY()
        val finalRadius = Math.hypot(to.width().toDouble(), to.height().toDouble()).toFloat()

        return ViewAnimationUtils.createCircularReveal(
            view, centerX - from.left, centerY - from.top,
            from.width() / 2f, finalRadius
        )
    }

    private fun createConceal(view: View, from: Rect, to: Rect): Animator {
        val centerX = to.centerX()
        val centerY = to.centerY()
        val initialRadius = Math.hypot(from.width().toDouble(), from.height().toDouble()).toFloat()

        return ViewAnimationUtils.createCircularReveal(
            view, centerX - to.left, centerY - to.top,
            initialRadius, to.width() / 2f
        )
    }

    private fun isReveal(startRect: Rect, endRect: Rect): Boolean {
        return startRect.width() < endRect.width()
    }

    companion object {
        private const val BOUNDS = "viewBounds"

        private val PROPS = arrayOf(BOUNDS)
    }
}