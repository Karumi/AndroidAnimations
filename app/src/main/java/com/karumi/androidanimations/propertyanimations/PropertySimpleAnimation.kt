package com.karumi.androidanimations.propertyanimations

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.Path
import android.graphics.Point
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.karumi.androidanimations.R
import com.karumi.androidanimations.extensions.animatePath
import com.karumi.androidanimations.propertyanimations.PropertyAnimationFragment.PropertyAnimation
import kotlin.random.Random

interface PropertySimpleAnimation {
    class VH(itemView: View) : com.afollestad.recyclical.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.animationName)
        val button: View = itemView.findViewById(R.id.target)
    }

    class Binder(val getContext: () -> Context) {
        operator fun invoke(receiver: VH, item: PropertyAnimation) = receiver.bind(item)

        private fun VH.bind(item: PropertyAnimation) {
            name.text = getAnimationName(item)
            animate(item, button)
        }

        private fun getAnimationName(item: PropertyAnimation): String = when (item) {
            PropertyAnimation.Translate -> R.string.property_animation_translation_x
            PropertyAnimation.Path -> R.string.property_animation_path_interpolator
            PropertyAnimation.AnimatorSet -> R.string.property_animation_path_interpolator
        }.let { getContext().getString(it) }

        private fun animate(item: PropertyAnimation, view: View) {
            view.configureOnClickListener()
            when (item) {
                PropertyAnimation.Translate -> {
                    ValueAnimator.ofFloat(0f, 0.5f * screenSize.x.toFloat()).apply {
                        duration = 2000
                        addUpdateListener {
                            val translationX =
                                it.animatedValue as? Float ?: return@addUpdateListener
                            view.translationX = translationX
                        }
                        repeatCount = ValueAnimator.INFINITE
                        repeatMode = ValueAnimator.REVERSE
                    }.start()
                }
                PropertyAnimation.Path -> {
                    val path = Path().apply {
                        moveTo(50f, 50f)
                        quadTo(50f, 200f, 0.5f * screenSize.x.toFloat(), 200f)
                    }
                    animatePath(view, path).start()
                }
                PropertyAnimation.AnimatorSet -> {
                    val path = Path().apply {
                        moveTo(50f, 50f)
                        quadTo(50f, 200f, 0.5f * screenSize.x.toFloat(), 200f)
                    }

                    val circlePathAnimation = animatePath(view, path).apply {
                        repeatCount = 0
                        repeatMode = ValueAnimator.RESTART
                    }.apply { duration = 2000 }

                    val rotationAnimation = ObjectAnimator.ofFloat(
                        view, View.ROTATION, 0f, 360f
                    ).apply { duration = 2000 }

                    val scaleXAnimation =
                        ObjectAnimator.ofFloat(
                            view, View.SCALE_X, 1f, 1.2f
                        ).apply { duration = 1000 }
                    val scaleYAnimation =
                        ObjectAnimator.ofFloat(
                            view, View.SCALE_Y, 1f, 1.2f
                        ).apply { duration = 1000 }

                    AnimatorSet().apply {
                        play(circlePathAnimation)
                            .with(rotationAnimation)
                        play(circlePathAnimation)
                            .before(scaleXAnimation)
                        play(scaleYAnimation)
                            .with(scaleXAnimation)
                        duration = 1000
                    }.start()
                }
            }
        }

        private val screenSize: Point
            get() {
                val wm = getContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
                val point = Point()
                wm.defaultDisplay.getSize(point)
                return point
            }

        private fun View.configureOnClickListener() {
            setOnClickListener { setBackgroundColor(nextRandomColor()) }
        }

        private fun nextRandomColor(): Int = with(Random.Default) {
            Color.argb(255, nextInt(256), nextInt(256), nextInt(256))
        }
    }
}