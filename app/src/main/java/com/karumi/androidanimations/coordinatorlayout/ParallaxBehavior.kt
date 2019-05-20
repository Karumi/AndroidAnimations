package com.karumi.androidanimations.coordinatorlayout

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Matrix
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.Scroller
import androidx.coordinatorlayout.widget.CoordinatorLayout

class ParallaxBehavior(
    context: Context,
    attrs: AttributeSet
) : CoordinatorLayout.Behavior<ImageView>(context, attrs) {

    private val parallaxFactor = 0.2f
    private var isAnimatingFling = false
    private var previousFlingX = 0
    private var previousFlingY = 0
    private val scroller = Scroller(context)

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: ImageView,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        // We stop fling animations on any scrolling stop
        isAnimatingFling = false
        return true
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: ImageView,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        child.imageMatrix = child.imageMatrix
            .translateWithParallax(dxConsumed.toFloat(), dyConsumed.toFloat())
    }

    private fun Matrix.translateWithParallax(
        dx: Float,
        dy: Float
    ): Matrix = Matrix(this)
        .apply { postTranslate(-parallaxFactor * dx, -parallaxFactor * dy) }

    override fun onNestedFling(
        coordinatorLayout: CoordinatorLayout,
        child: ImageView,
        target: View,
        velocityX: Float,
        velocityY: Float,
        consumed: Boolean
    ): Boolean {
        val canScroll = if (Math.abs(velocityX) > 0) {
            target.canScrollHorizontally(velocityX.toInt())
        } else {
            target.canScrollVertically(velocityX.toInt())
        }

        if (!canScroll) {
            return false
        }

        scroller.fling(
            0,
            0,
            velocityX.toInt(),
            velocityY.toInt(),
            0,
            0,
            Int.MIN_VALUE,
            Int.MAX_VALUE
        )

        previousFlingX = 0
        previousFlingY = 0
        isAnimatingFling = true
        ValueAnimator.ofFloat(0f, 1f).apply {
            addUpdateListener { animateImageViewParallax(it, child) }
            duration = scroller.duration.toLong()
            start()
        }
        return true
    }

    private fun animateImageViewParallax(
        animator: ValueAnimator,
        child: ImageView
    ) {
        if (!isAnimatingFling) {
            animator.cancel()
            return
        }

        scroller.computeScrollOffset()
        val currX = scroller.currX
        val currY = scroller.currY
        val xDiff = currX - previousFlingX.toFloat()
        val yDiff = currY - previousFlingY.toFloat()
        previousFlingX = currX
        previousFlingY = currY
        child.imageMatrix = child.imageMatrix.translateWithParallax(xDiff, yDiff)
    }
}