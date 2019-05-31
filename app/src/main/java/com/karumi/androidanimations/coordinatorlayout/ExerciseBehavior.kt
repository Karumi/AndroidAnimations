package com.karumi.androidanimations.coordinatorlayout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.airbnb.lottie.LottieAnimationView

class ExerciseBehavior(
    context: Context,
    attrs: AttributeSet
) : CoordinatorLayout.Behavior<LottieAnimationView>(context, attrs) {

    private var childHeight: Int = 0

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: LottieAnimationView,
        dependency: View
    ): Boolean = true

    override fun onLayoutChild(
        parent: CoordinatorLayout,
        child: LottieAnimationView,
        layoutDirection: Int
    ): Boolean {
        /**
         * Always call parent.onLayoutChild if we want to do things AFTER a regular layout phase.
         * We want to move the view to the bottom of the recycler view so we let the framework layout
         * its views and then move it down.
         */
        parent.onLayoutChild(child, layoutDirection)
        childHeight = child.height
        child.top += childHeight
        child.bottom += childHeight
        return true
    }

    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: LottieAnimationView,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean =
        /**
         * We always want to listen to scroll events.
         */
        true

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: LottieAnimationView,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        if (target.top < 0) {
            /**
             * No matter what, we consume the overscroll whenever we moved the target view.
             */
            consumed[1] = dy

            animateIfVisible(child)

            /**
             * We only move the target view if one of the following is satisfied:
             *   - We are still on limits for the top overscroll, that is, we can fit the child
             *     view below the scroll.
             *   - We are scrolling back to the original position.
             */
            if (target.top > -childHeight || dy < 0) {
                overscroll(child, target, dy)
            }
        }
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: LottieAnimationView,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        /**
         * We are responsible for listening to new over-scrolls, that is, when dyUnconsumed is
         * positive: That means we got to the end of the list and still there is scrolling left
         * to process. In that case we "simulate" the scroll by moving the whole view. When that
         * happens onNestedPreScroll will start doing things because [target.top < 0].
         */
        if (dyUnconsumed > 0) {
            overscroll(child, target, dyUnconsumed)
        }
    }

    private fun overscroll(child: View, target: View, dy: Int) {
        val targetHeight = target.measuredHeight

        /**
         * We are going to calculate min and max positions for the bottom and the top points for
         * both views. We are doing this so that small floating point operations do not carry
         * errors around.
         */
        target.top = (target.top - dy).clamp(-childHeight, 0)
        target.bottom = (target.bottom - dy).clamp(targetHeight - childHeight, targetHeight)
        child.top = (child.top - dy).clamp(targetHeight - childHeight, targetHeight)
        child.bottom = (child.bottom - dy).clamp(targetHeight, targetHeight + childHeight)
    }

    private fun Int.clamp(min: Int, max: Int): Int {
        return Math.min(max, Math.max(this, min))
    }

    private fun animateIfVisible(child: LottieAnimationView) {
        if (!child.isAnimating) {
            child.playAnimation()
        }
    }
}