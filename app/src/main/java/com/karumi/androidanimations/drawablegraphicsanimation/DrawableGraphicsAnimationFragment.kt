package com.karumi.androidanimations.drawablegraphicsanimation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.recyclical.datasource.dataSourceOf
import com.afollestad.recyclical.setup
import com.afollestad.recyclical.withItem
import com.karumi.androidanimations.R
import com.karumi.androidanimations.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_animated_drawable_graphics.*

class DrawableGraphicsAnimationFragment : BaseFragment() {

    val drawableGraphicsSimpleAnimationBinder =
        DrawableGraphicsSimpleAnimation.Binder(::requireContext)
    val drawableGraphicsExerciseAnimationBinder =
        DrawableGraphicsExerciseAnimation.Binder(::requireContext)
    val drawableGraphicsLottieAnimationBinder =
        DrawableGraphicsLottieAnimation.Binder(::requireContext)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_animated_drawable_graphics, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureAllAnimations()
    }

    private fun configureAllAnimations() {
        val layoutManager = LinearLayoutManager(requireContext())
        val dataSource = dataSourceOf(
            *DrawableGraphicsAnimation.values(),
            Lottie,
            Exercise
        )

        allAnimations.setup {
            withLayoutManager(layoutManager)
            withDataSource(dataSource)

            withItem<DrawableGraphicsAnimation>(R.layout.view_simple_drawable_graphics_animation) {
                onBind(DrawableGraphicsSimpleAnimation::VH) { _, item ->
                    drawableGraphicsSimpleAnimationBinder(this, item)
                }
            }
            withItem<Lottie>(R.layout.view_lottie_drawable_graphics_animation) {
                onBind(DrawableGraphicsLottieAnimation::VH) { _, _ ->
                    drawableGraphicsLottieAnimationBinder(this)
                }
            }
            withItem<Exercise>(R.layout.view_exercise_drawable_graphics_animation) {
                onBind(DrawableGraphicsExerciseAnimation::VH) { _, _ ->
                    drawableGraphicsExerciseAnimationBinder(this)
                }
            }
        }
    }

    enum class DrawableGraphicsAnimation {
        Keyframe, Vector
    }

    object Lottie
    object Exercise
}