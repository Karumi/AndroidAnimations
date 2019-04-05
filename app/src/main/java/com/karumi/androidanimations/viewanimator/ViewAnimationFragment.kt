package com.karumi.androidanimations.viewanimator

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
import kotlinx.android.synthetic.main.fragment_view_animator.*

class ViewAnimationFragment : BaseFragment() {

    val simpleAnimationBinder = ViewSimpleAnimation.Binder(::requireContext)
    val animationSetBinder = ViewAnimationSet.Binder(::requireContext)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_view_animator, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureAllAnimations()
    }

    private fun configureAllAnimations() {
        val layoutManager = LinearLayoutManager(requireContext())
        val dataSource = dataSourceOf(
            SimpleAnimation.Alpha,
            SimpleAnimation.Rotate,
            SimpleAnimation.Scale,
            SimpleAnimation.Translate,
            AnimationSet
        )

        allAnimations.setup {
            withLayoutManager(layoutManager)
            withDataSource(dataSource)

            withItem<SimpleAnimation>(R.layout.view_simple_view_animation) {
                onBind(ViewSimpleAnimation::VH) { _, item -> simpleAnimationBinder(this, item) }
            }

            withItem<AnimationSet>(R.layout.view_set_view_animation) {
                onBind(ViewAnimationSet::VH) { _, _ -> animationSetBinder(this) }
            }
        }
    }

    enum class SimpleAnimation {
        Alpha, Rotate, Scale, Translate
    }

    object AnimationSet
}