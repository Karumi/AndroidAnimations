package com.karumi.androidanimations.layouttransition

import android.content.Context
import android.view.View
import android.widget.TextView
import com.karumi.androidanimations.R
import com.karumi.androidanimations.layouttransition.LayoutTransitionFragment.LayoutTransition

interface SimpleLayoutTransition {
    class VH(itemView: View) : com.afollestad.recyclical.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.animationName)
        val button: View = itemView.findViewById(R.id.target)
    }

    class Binder(val getContext: () -> Context) {
        operator fun invoke(receiver: VH, item: LayoutTransition) = receiver.bind(item)

        private fun VH.bind(item: LayoutTransition) {
            name.text = getAnimationName(item)
            animate(item, button)
        }

        private fun getAnimationName(item: LayoutTransition): String = when (item) {
            LayoutTransition.Fade -> R.string.layout_transition_fade
            LayoutTransition.ChangeBounds -> R.string.layout_transition_change_bounds
            LayoutTransition.Auto -> R.string.layout_transition_auto
        }.let { getContext().getString(it) }

        private fun animate(item: LayoutTransition, view: View) {
            when (item) {
                LayoutTransition.Fade -> {
                }
                LayoutTransition.ChangeBounds -> {
                }
                LayoutTransition.Auto -> {
                }
            }
        }
    }
}