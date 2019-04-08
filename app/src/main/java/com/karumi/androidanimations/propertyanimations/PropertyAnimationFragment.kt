package com.karumi.androidanimations.propertyanimations

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.recyclical.datasource.dataSourceOf
import com.afollestad.recyclical.setup
import com.afollestad.recyclical.withItem
import com.karumi.androidanimations.R
import com.karumi.androidanimations.base.BaseFragment
import com.karumi.androidanimations.viewanimator.ViewSimpleAnimation
import kotlinx.android.synthetic.main.fragment_property_animation.*
import kotlin.random.Random


class PropertyAnimationFragment : BaseFragment() {

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
            TranslateAnimation
        )

        allAnimations.setup {
            withLayoutManager(layoutManager)
            withDataSource(dataSource)

            withItem<TranslateAnimation>(R.layout.view_simple_property_animation) {
                onBind(ViewSimpleAnimation::VH) { _, _ ->
                    name.text = R.string.property_animation_translation_x.resolve()
                    targetView.setOnClickListener {
                        val rnd = Random.Default
                        val color =
                            Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
                        targetView.setBackgroundColor(color)
                    }
                    val wm =
                        requireContext().getSystemService(Context.WINDOW_SERVICE) as WindowManager
                    val point = Point()
                    wm.defaultDisplay.getSize(point)
                    ValueAnimator.ofFloat(0f, 0.5f * point.x.toFloat()).apply {
                        duration = 2000
                        addUpdateListener {
                            val translationX =
                                it.animatedValue as? Float ?: return@addUpdateListener
                            targetView.translationX = translationX
                        }
                        repeatCount = ValueAnimator.INFINITE
                        repeatMode = ValueAnimator.REVERSE
                    }.start()
                }
            }
        }
    }

    object TranslateAnimation
}