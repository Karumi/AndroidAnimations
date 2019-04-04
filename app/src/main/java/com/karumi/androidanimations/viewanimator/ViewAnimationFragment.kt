package com.karumi.androidanimations.viewanimator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import android.view.animation.ScaleAnimation
import android.view.animation.TranslateAnimation
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.recyclical.ViewHolder
import com.afollestad.recyclical.datasource.dataSourceOf
import com.afollestad.recyclical.setup
import com.afollestad.recyclical.withItem
import com.karumi.androidanimations.R
import com.karumi.androidanimations.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_view_animator.*

class ViewAnimationFragment : BaseFragment() {
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
        val dataSource = dataSourceOf(*AnimationType.values())

        allAnimations.setup {
            withLayoutManager(layoutManager)
            withDataSource(dataSource)

            withItem<AnimationType>(R.layout.view_view_animation) {
                onBind(::AnimationViewHolder) { _, item ->
                    name.text = when (item) {
                        AnimationType.Alpha -> R.string.view_animation_alpha
                        AnimationType.Rotate -> R.string.view_animation_rotate
                        AnimationType.Scale -> R.string.view_animation_scale
                        AnimationType.Translate -> R.string.view_animation_translate
                    }.resolve()

                    val animation = when (item) {
                        AnimationType.Alpha -> AlphaAnimation(0f, 1f)
                        AnimationType.Rotate -> RotateAnimation(
                            0f,
                            360f,
                            Animation.RELATIVE_TO_SELF,
                            0.5f,
                            Animation.RELATIVE_TO_SELF,
                            0.5f
                        )
                        AnimationType.Scale -> ScaleAnimation(1f, 2f, 1f, 0f)
                        AnimationType.Translate -> TranslateAnimation(
                            Animation.RELATIVE_TO_SELF,
                            -2f,
                            Animation.RELATIVE_TO_SELF,
                            2f,
                            Animation.RELATIVE_TO_SELF,
                            0f,
                            Animation.RELATIVE_TO_SELF,
                            0f
                        )
                    }.apply {
                        duration = 1000
                        repeatMode = Animation.REVERSE
                        repeatCount = Animation.INFINITE
                    }
                    targetView.startAnimation(animation)
                }
            }
        }
    }

    private enum class AnimationType {
        Alpha, Rotate, Scale, Translate
    }

    private class AnimationViewHolder(itemView: View) : ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.animationName)
        val targetView: View = itemView.findViewById(R.id.target)
    }
}