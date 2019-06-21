package com.karumi.androidanimations.propertyanimator

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
import kotlinx.android.synthetic.main.fragment_property_animator.*


class PropertyAnimatorFragment : BaseFragment() {

    val propertySimpleAnimationBinder = PropertySimpleAnimator.Binder(::requireContext)
    val propertyExerciseAnimationBinder = PropertyExerciseAnimator.Binder(::requireContext)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_view_animation, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureAllAnimations()
    }

    private fun configureAllAnimations() {
        val layoutManager = LinearLayoutManager(requireContext())
        val dataSource = dataSourceOf(
            *PropertyAnimator.values(),
            Exercise
        )

        allAnimations.setup {
            withLayoutManager(layoutManager)
            withDataSource(dataSource)

            withItem<PropertyAnimator>(R.layout.view_simple_property_animator) {
                onBind(PropertySimpleAnimator::VH) { _, item ->
                    propertySimpleAnimationBinder(this, item)
                }
            }
            withItem<Exercise>(R.layout.view_exercise_property_animator) {
                onBind(PropertyExerciseAnimator::VH) { _, _ ->
                    propertyExerciseAnimationBinder(this)
                }
            }
        }
    }

    enum class PropertyAnimator {
        Translate, Path, AnimatorSet
    }

    object Exercise
}