package com.karumi.androidanimations.layouttransition

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.transition.AutoTransition
import androidx.transition.ChangeBounds
import androidx.transition.Fade
import androidx.transition.Scene
import androidx.transition.Transition
import androidx.transition.TransitionManager
import com.karumi.androidanimations.R
import com.karumi.androidanimations.layouttransition.LayoutTransitionFragment.LayoutTransition

interface SimpleLayoutTransition {
    class VH(itemView: View) : com.afollestad.recyclical.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.transitionName)
        val masterScene: ViewGroup = itemView.findViewById(R.id.masterScene)
    }

    class Binder(val getContext: () -> Context) {
        operator fun invoke(receiver: VH, item: LayoutTransition) = receiver.bind(item)

        private fun VH.bind(item: LayoutTransition) {
            name.text = getAnimationName(item)
            masterScene.findViewById<Button>(R.id.button).setOnClickListener {
                animateToEndState(item, this)
            }
        }

        private fun getAnimationName(item: LayoutTransition): String = when (item) {
            LayoutTransition.Fade -> R.string.layout_transition_fade
            LayoutTransition.ChangeBounds -> R.string.layout_transition_change_bounds
            LayoutTransition.Auto -> R.string.layout_transition_auto
        }.let { getContext().getString(it) }

        private fun animateToEndState(item: LayoutTransition, viewHolder: VH) {
            animateToLayout(
                viewHolder,
                R.layout.view_layout_transition_end_state,
                item.transition
            ) { animateToStartState(item, viewHolder) }
        }

        private fun animateToStartState(item: LayoutTransition, viewHolder: VH) {
            animateToLayout(
                viewHolder,
                R.layout.view_layout_transition_start_state,
                item.transition
            ) { animateToEndState(item, viewHolder) }
        }

        private val LayoutTransition.transition: Transition
            get() = when (this) {
                LayoutTransition.Fade -> Fade()
                LayoutTransition.ChangeBounds -> ChangeBounds()
                LayoutTransition.Auto -> AutoTransition()
            }

        private fun animateToLayout(
            viewHolder: VH,
            @LayoutRes layoutRes: Int,
            transition: Transition,
            onEnterTransition: () -> Unit
        ) {
            val scene = Scene.getSceneForLayout(
                viewHolder.masterScene,
                layoutRes,
                getContext()
            )
            scene.setEnterAction {
                viewHolder.masterScene.findViewById<Button>(R.id.button)
                    .setOnClickListener {
                        onEnterTransition()
                    }
            }

            TransitionManager.go(scene, transition)
        }
    }
}