package com.karumi.androidanimations.layouttransition

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
import com.karumi.androidanimations.propertyanimations.PropertyExerciseAnimation
import kotlinx.android.synthetic.main.fragment_property_animation.*

class LayoutTransitionFragment : BaseFragment() {

    val simpleLayoutTransitionBinder = SimpleLayoutTransition.Binder(::requireContext)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_layout_transition, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureAllAnimations()
    }

    private fun configureAllAnimations() {
        val layoutManager = LinearLayoutManager(requireContext())
        val dataSource = dataSourceOf(
            *LayoutTransition.values(),
            Exercise
        )

        allAnimations.setup {
            withLayoutManager(layoutManager)
            withDataSource(dataSource)

            withItem<LayoutTransition>(R.layout.view_simple_property_animation) {
                onBind(SimpleLayoutTransition::VH) { _, item ->
                    simpleLayoutTransitionBinder(this, item)
                }
            }
            withItem<Exercise>(R.layout.view_exercise_property_animation) {
                onBind(PropertyExerciseAnimation::VH) { _, _ ->

                }
            }
        }
    }

    enum class LayoutTransition {
        Fade, ChangeBounds, Auto
    }

    object Exercise
}