package com.karumi.androidanimations.viewanimator

import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.afollestad.recyclical.ViewHolder
import com.karumi.androidanimations.R

interface ViewAnimationSet {

    class VH(itemView: View) : ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.animationName)
        val containerView: View = itemView
        val button: Button = itemView.findViewById(R.id.button)
    }

    class Binder(val getContext: () -> Context) {
        operator fun invoke(
            receiver: VH
        ) = receiver.bind()

        private fun VH.bind() {
            name.text = getContext().getString(R.string.view_animation_set)
            button.setOnClickListener {
                TODO("Implement the animation set to animate this.containerView!")
            }
        }
    }
}