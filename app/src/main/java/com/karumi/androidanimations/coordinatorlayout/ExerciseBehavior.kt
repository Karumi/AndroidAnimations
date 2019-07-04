package com.karumi.androidanimations.coordinatorlayout

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.airbnb.lottie.LottieAnimationView
import kotlin.math.max
import kotlin.math.min

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
        val targetIsTranslatedUp = target.translationY < 0
        if (targetIsTranslatedUp) {
            /**
             * No matter what, we consume the scroll event whenever we moved the target view so the view doesn't
             * perform the default scroll implementation.
             */
            consumed[1] = dy

            /**
             * We only move the target view if one of the following is satisfied:
             *   - We are still on limits for the top overscroll, that is, we can fit the child
             *     view below the scroll.
             *   - We are scrolling back to the original position.
             */
            val scrollingDown = dy < 0
            val targetIsOverTheScroll = target.translationY > -childHeight
            if (targetIsOverTheScroll || scrollingDown) {
                handleScroll(child, target, dy)
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
         * to process. In that case we "simulate" the scroll by translating the whole view. When that
         * happens onNestedPreScroll will start doing things because [target.translateY < 0].
         */
        val isOverScrolling = dyUnconsumed > 0
        if (isOverScrolling) {
            handleScroll(child, target, dyUnconsumed)
        }
        updateChildViewAnimationState(child)
    }

    private fun handleScroll(child: View, target: View, dy: Int) {
        updateTranslationY(target, dy)
        updateTranslationY(child, dy)
    }

    private fun updateTranslationY(view: View, dy: Int) {
        val proposedTranslation = view.translationY - dy
        view.translationY = proposedTranslation.clamp(-childHeight.toFloat(), 0f)
    }

    private fun Float.clamp(min: Float, max: Float): Float = min(max, max(this, min))

    private fun updateChildViewAnimationState(child: LottieAnimationView) {
        val lastItemVisible = child.translationY < 0
        if (lastItemVisible && !child.isAnimating) {
            child.playAnimation()
        } else {
            child.pauseAnimation()
        }
    }
}